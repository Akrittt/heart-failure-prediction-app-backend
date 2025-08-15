package com.health.care.Service;

import com.health.care.Entity.Patient;
import com.health.care.Entity.User;
import com.health.care.Exception.ResourceNotFoundException;
import com.health.care.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional(readOnly = true)
    public List<Patient> getPatientsByUser(User user) {
        return patientRepository.findByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    public Patient getPatientByIdAndUser(Long id, User user) {
        return patientRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    @Transactional
    public Patient createPatient(Patient patient, User user) {
        if (patientRepository.existsByPatientIdAndUser(patient.getPatientId(), user)) {
            throw new IllegalArgumentException("Patient with ID " + patient.getPatientId() + " already exists");
        }
        
        // Set the user for the patient
        patient.setUser(user);
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient updatePatient(Long id, Patient patientDetails, User user) {
        Patient patient = getPatientByIdAndUser(id, user);
        
        // Update patient details
        patient.setFullName(patientDetails.getFullName());
        patient.setDateOfBirth(patientDetails.getDateOfBirth());
        patient.setGender(patientDetails.getGender());
        patient.setBloodType(patientDetails.getBloodType());
        patient.setPhone(patientDetails.getPhone());
        patient.setEmail(patientDetails.getEmail());
        patient.setAddress(patientDetails.getAddress());
        patient.setEmergencyContact(patientDetails.getEmergencyContact());
        patient.setPrimaryDoctor(patientDetails.getPrimaryDoctor());
        patient.setInsurance(patientDetails.getInsurance());
        patient.setAllergies(patientDetails.getAllergies());
        patient.setHeight(patientDetails.getHeight());
        patient.setWeight(patientDetails.getWeight());
        patient.setBmi(patientDetails.getBmi());
        
        return patientRepository.save(patient);
    }

    @Transactional
    public void deletePatient(Long id, User user) {
        Patient patient = getPatientByIdAndUser(id, user);
        patientRepository.delete(patient);
    }
}
