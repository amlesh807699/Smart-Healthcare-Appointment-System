package com.project.project.Dto.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResDto {

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

}