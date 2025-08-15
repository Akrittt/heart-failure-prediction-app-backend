package com.health.care.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Table(name = "patients")
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "patient_id", unique = true, nullable = false)
    private String patientId;

    // Personal Information
    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotBlank
    @Column(name = "gender")
    private String gender;

    @NotBlank
    @Column(name = "blood_type")
    private String bloodType;

    // Contact Information
    @NotBlank
    @Column(name = "phone")
    private String phone;

    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "address", length = 500)
    private String address;

    @NotBlank
    @Column(name = "emergency_contact")
    private String emergencyContact;

    // Medical Information
    @NotBlank
    @Column(name = "primary_doctor")
    private String primaryDoctor;

    @NotBlank
    @Column(name = "insurance")
    private String insurance;

    @ElementCollection
    @CollectionTable(name = "patient_allergies", joinColumns = @JoinColumn(name = "patient_id_fk"))
    private List<String> allergies;

    // Health Metrics
    @NotBlank
    private String height;

    @NotBlank
    private String weight;

    
    private String bmi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    // Constructors
    public Patient() {
    }

    public Patient(String patientId, String fullName, LocalDate dateOfBirth, String gender, String bloodType,
                   String phone, String email, String address, String emergencyContact,
                   String primaryDoctor, String insurance, List<String> allergies,
                   String height, String weight, String bmi) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.emergencyContact = emergencyContact;
        this.primaryDoctor = primaryDoctor;
        this.insurance = insurance;
        this.allergies = allergies;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
    }

}
