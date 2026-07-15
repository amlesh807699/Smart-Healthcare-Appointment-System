package com.project.project.Dto.Doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResDto {

    private Long id;

    private String profilepic;

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


    private String availableDays;

    private String availableTime;

    private Double consultationFee;
}
