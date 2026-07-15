package com.project.project.Dto.Doctor;

import com.project.project.Entity.Doctor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapping {

    Doctor toEntity(DoctorReqDto doctorReqDto);

    DoctorResDto toDto(Doctor doctor);

    List<DoctorResDto> DTO_LIST(List<Doctor> doctors);
}