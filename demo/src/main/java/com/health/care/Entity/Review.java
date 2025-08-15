package com.health.care.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is Required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private  String name;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating;

    @NotNull(message = "Date is required")
    private ZonedDateTime date;

    @NotBlank(message = "Service is required")
    private String service;

    @NotBlank(message = "Review text is required")
    @Size(min = 10, max = 1000, message = "Review must be between 10 and 1000 characters")
    private String review;

    private Boolean verified = true;

    @CreatedDate
    private LocalDateTime createdAt;

    public Review(){}

    public Review(String name, Integer rating, ZonedDateTime date, String service, String review, Boolean verified) {
        this.name = name;
        this.rating = rating;
        this.date = date;
        this.service = service;
        this.review = review;
        this.verified = verified;
    }



}
