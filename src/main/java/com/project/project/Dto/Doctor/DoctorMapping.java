package com.project.project.Dto.Doctor;

import com.project.project.Entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapping {

    Doctor toEntity(DoctorReqDto doctorReqDto);

    DoctorResDto toDto(Doctor doctor);

}