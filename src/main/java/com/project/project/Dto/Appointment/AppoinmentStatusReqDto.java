package com.project.project.Dto.Appointment;

import com.project.project.Entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppoinmentStatusReqDto {
    private AppointmentStatus status;

}
