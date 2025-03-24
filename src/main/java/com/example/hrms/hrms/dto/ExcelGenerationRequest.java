package com.example.hrms.hrms.dto;

import java.util.List;

import lombok.Data;

@Data
public class ExcelGenerationRequest {
    private List<AttendanceReportDTO> dataset;
    private String month;
    private String year;
}
