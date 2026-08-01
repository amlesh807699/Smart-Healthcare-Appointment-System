package com.project.project.Dto.Doctor;

import com.project.project.Entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapping {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profilepic", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    Doctor toEntity(DoctorReqDto dto);


    DoctorResDto toDto(Doctor doctor);


    List<DoctorResDto> toDtoList(List<Doctor> doctors);
}