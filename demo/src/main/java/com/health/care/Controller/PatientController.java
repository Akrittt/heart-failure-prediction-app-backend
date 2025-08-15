package com.health.care.Controller;

import com.health.care.DTOs.ResponseDTO;
import com.health.care.Entity.Patient;
import com.health.care.Entity.User;
import com.health.care.Security.CurrentUser;
import com.health.care.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin("http://localhost:5173/")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllPatients(@CurrentUser User currentUser) {
        List<Patient> patients = patientService.getPatientsByUser(currentUser);
        return ResponseEntity.ok(
            ResponseDTO.success("Patients retrieved successfully", patients)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPatientById(
            @PathVariable Long id,
            @CurrentUser User currentUser) {
        Patient patient = patientService.getPatientByIdAndUser(id, currentUser);
        return ResponseEntity.ok(
            ResponseDTO.success("Patient retrieved successfully", patient)
        );
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> createPatient(
            @Valid @RequestBody Patient patient,
            @CurrentUser User currentUser) {
        Patient createdPatient = patientService.createPatient(patient, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseDTO.success("Patient created successfully", createdPatient)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody Patient patientDetails,
            @CurrentUser User currentUser) {
        Patient updatedPatient = patientService.updatePatient(id, patientDetails, currentUser);
        return ResponseEntity.ok(
            ResponseDTO.success("Patient updated successfully", updatedPatient)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deletePatient(
            @PathVariable Long id,
            @CurrentUser User currentUser) {
        patientService.deletePatient(id, currentUser);
        return ResponseEntity.ok(
            ResponseDTO.success("Patient deleted successfully", null)
        );
    }
}
