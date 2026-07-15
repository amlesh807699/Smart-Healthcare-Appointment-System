package com.project.project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private LocalDateTime appointmentDateTime;


    private String reason;


    private String symptoms;


    private String status;


    private String notes;


    private LocalDateTime createdAt;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;


    @OneToOne(mappedBy = "appointment")
    private Payment payment;

}