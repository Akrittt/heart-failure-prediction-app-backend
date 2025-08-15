package com.health.care.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Firstname is required")
    @Size(max = 40)
    private String firstname;

    @NotBlank(message = "Lastname is required")
    @Size(max = 40)
    private String lastname;

    @NotBlank(message = "HospitalName is required")
    @Size(max = 100)
    private String hospitalName;

    @NotBlank(message = "Email is required")
    @Size(max = 50)
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Patient> patients = new ArrayList<>();

    public User() {}

    public User(String firstname, String lastname, String hospitalName, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.hospitalName = hospitalName;
        this.email = email;
        this.password = password;
    }


    // Adds a patient to this user's list of patients
    public void addPatient(Patient patient) {
        if (patient != null) {
            patients.add(patient);
            patient.setUser(this);
        }
    }


    //Removes a patient from this user's list of patients
    public void removePatient(Patient patient) {
        if (patient != null) {
            patients.remove(patient);
            patient.setUser(null);
        }
    }
}
