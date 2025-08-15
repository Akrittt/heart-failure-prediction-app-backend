package com.health.care.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;



@Service
public class OtpService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CacheManager cacheManager;

    public String generateOtp(String mail){
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(999999));
        Cache otpCache = cacheManager.getCache("otpCache");
        if(otpCache !=null){
            otpCache.put(mail,otp);
        }
        return otp;

    }

    public void sendOtp(String toEmail, String otp){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP for Healthcare.ai");
            message.setText("Your OTP is: " + otp + "\n\nThis OTP is valid for 5 minutes.");
            mailSender.send(message);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean validateOtp(String email,String otp){
        Cache otpCache = cacheManager.getCache("otpCache");
        if(otpCache != null){
            String cachedOtp = otpCache.get(email, String.class);
            // OTP is correct, invalidate it so it can't be used again
            if(cachedOtp != null && cachedOtp.equals(otp)){
                otpCache.evict(email);
                return true;
            }
        }
        return false;
    }
}
