package com.project.project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reportName;

    private String reportType;

    private String description;

    private String fileUrl;

    private LocalDate reportDate;

    private String status;

    private String doctorName;

    private String labName;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

}