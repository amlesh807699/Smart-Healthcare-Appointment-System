package com.project.project.Dto.Appointment;

import com.project.project.Entity.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    Appointment ToEntity(AppointmentReqDto appointmentReqDto);
    AppointmentResDto ToDto(Appointment appointment);

}
