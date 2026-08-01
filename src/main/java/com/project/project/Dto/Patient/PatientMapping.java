package com.project.project.Dto.Patient;

import com.project.project.Entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapping {

    @Mapping(target = "id" ,ignore = true)
    @Mapping(target = "appointments" ,ignore = true)
    @Mapping(target = "reports" ,ignore = true)
    @Mapping(target = "user", ignore = true)
    Patient toEntity(PatientReqDto dto);

    PatientResDto toDto(Patient patient);
    List<PatientResDto> tolistdto(List<Patient> patients);
}