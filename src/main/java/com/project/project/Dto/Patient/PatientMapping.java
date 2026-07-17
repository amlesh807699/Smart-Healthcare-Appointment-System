package com.project.project.Dto.Patient;

import com.project.project.Entity.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapping {

    Patient toEntity(PatientReqDto dto);

    PatientResDto toDto(Patient patient);
    List<PatientResDto> tolistdto(List<Patient> patients);
}