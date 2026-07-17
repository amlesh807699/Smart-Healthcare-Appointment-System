package com.project.project.Dto.Report;

import com.project.project.Entity.Report;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {

    Report ToEntity(ReportReqDto reportReqDto);
    ReportResDto ToDto(Report report);
    List<ReportResDto> toDto(List<Report> reports);
}
