package com.project.project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String gender;

    private int age;

    private String specialization;

    private String qualification;

    private int experience;

    private String hospitalName;

    private String address;

    private String city;

    private String licenseNumber;

    private String availableDays;

    private String availableTime;

    private Double consultationFee;


    @OneToMany(
            mappedBy = "doctor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Appointment> appointments;

}