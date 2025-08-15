package com.health.care.Repository;

import com.health.care.Entity.Patient;
import com.health.care.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByUserId(Long userId);
    boolean existsByPatientIdAndUser(String patientId, User user);
    Optional<Patient> findByIdAndUser(Long id, User user);
}
