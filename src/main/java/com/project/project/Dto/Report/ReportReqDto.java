package com.project.project.Dto.Report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportReqDto {
    private String reportName;

    private String reportType;

    private String description;

    private String fileUrl;

    private LocalDate reportDate;

    private String status;

    private String doctorName;

    private String labName;
}
