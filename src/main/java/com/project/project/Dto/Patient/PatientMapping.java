package com.project.project.Dto.Patient;

import com.project.project.Entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface PatientMapping {

    Patient ToEntity(PatientReqDto patientReqDto);
    PatientResDto toDto(Patient patient);
}
