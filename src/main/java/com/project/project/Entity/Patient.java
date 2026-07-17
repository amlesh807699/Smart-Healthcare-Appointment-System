package com.project.project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String profilepic;
    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String gender;

    private LocalDate dateOfBirth;

    private String bloodGroup;

    private String address;

    private String city;

    private String emergencyContact;

    private String emergencyContactName;

    private String medicalHistory;

    private String allergies;

    private String insuranceNumber;

    @OneToMany( mappedBy = "patient", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @OneToMany(
            mappedBy = "patient",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Report> reports;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;


}