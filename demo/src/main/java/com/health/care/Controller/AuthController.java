package com.health.care.Controller;

import com.health.care.DTOs.*;
import com.health.care.Entity.User;
import com.health.care.Repository.UserRepository;
import com.health.care.Security.JwtTokenProvider;
import com.health.care.Service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.lang.reflect.Method;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.health.care.Entity.User;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("http://localhost:5173/")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private OtpService otpService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/signin")
    public ResponseEntity<ResponseDTO> authenticateUser(@RequestBody SignInRequest signInRequest) {
        try {
            String email = signInRequest.getEmail().toLowerCase().trim();
            logger.info("Attempting to authenticate user with email: {}", email);
            
            // Check if user exists before attempting authentication
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isEmpty()) {
                logger.warn("Login failed: No user found with email: {}", email);
                return ResponseEntity.status(401)
                        .body(ResponseDTO.error("Invalid email or password"));
            }
            
            logger.debug("User found in database. Attempting authentication...");
            
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            signInRequest.getPassword()
                    )
            );

            logger.debug("Authentication successful. Generating JWT token...");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            
            User user = userOptional.get();
            logger.info("User '{}' successfully authenticated. Firstname: {}", email, user.getFirstname());
            
            return ResponseEntity.ok(
                    ResponseDTO.success("Login successful", 
                        new JwtAuthenticationResponse(jwt, user.getFirstname()))
            );
        } catch (org.springframework.security.core.AuthenticationException e) {
            logger.warn("Authentication failed: Invalid credentials for email: {}", signInRequest.getEmail());
            return ResponseEntity.status(401)
                    .body(ResponseDTO.error("Invalid email or password"));
        } catch (Exception e) {
            logger.error("Error during authentication for email: " + signInRequest.getEmail(), e);
            return ResponseEntity.status(500)
                    .body(ResponseDTO.error("An error occurred during authentication"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> SendRegistrationOtp(@RequestBody SignUpRequest signUpRequest) {

            if (userRepository.existsByEmail(signUpRequest.getEmail().toLowerCase())) {
                return ResponseEntity.badRequest()
                        .body(ResponseDTO.error("Email is already in use"));
            }

            String otp = otpService.generateOtp(signUpRequest.getEmail());
            otpService.sendOtp(signUpRequest.getEmail(),otp);

            return ResponseEntity.ok("OTP sent successfully to your email ");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtpAndRegister(@RequestBody VerifyOtpRequest verifyOtpRequest){
        String email = verifyOtpRequest.getEmail().toLowerCase().trim();
        if (!otpService.validateOtp(email, verifyOtpRequest.getOtp())){
            return ResponseEntity.badRequest().body("Error: Invalid or expired OTP. ");
        }

        User user = new User(
                verifyOtpRequest.getFirstname(),
                verifyOtpRequest.getLastname(),
                verifyOtpRequest.getHospitalName(),
                email,
                passwordEncoder.encode(verifyOtpRequest.getPassword())
        );

        // Save the user to the database
        userRepository.save(user);

        return ResponseEntity.ok("User registered Successfully!");

    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
        return ResponseEntity.ok(userDetails);
    }




}
