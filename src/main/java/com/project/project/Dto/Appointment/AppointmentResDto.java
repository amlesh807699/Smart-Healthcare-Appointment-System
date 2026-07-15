package com.project.project.Dto.Appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResDto {
    private Long id;


    private LocalDateTime appointmentDateTime;


    private String reason;


    private String symptoms;


    private String status;


    private String notes;


    private LocalDateTime createdAt;
}
