package com.project.project.Dto.Patient;

import com.project.project.Entity.Appointment;
import com.project.project.Entity.Report;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "Spring")
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

    @OneToMany( mappedBy = "patient", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @OneToMany(
            mappedBy = "patient",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Report> reports;
}
