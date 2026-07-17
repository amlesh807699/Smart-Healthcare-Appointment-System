package com.project.project.Dto.Appointment;

import com.project.project.Entity.Appointment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    Appointment toEntity(AppointmentReqDto dto);

    AppointmentResDto toDto(Appointment appointment);

    List<AppointmentResDto> toDto(List<Appointment> appointments);

}