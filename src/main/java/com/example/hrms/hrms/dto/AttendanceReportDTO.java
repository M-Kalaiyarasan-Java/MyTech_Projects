package com.example.hrms.hrms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttendanceReportDTO {
    private String employee;
    private String month;
    
    @JsonProperty("total_hours")
    private String totalHours;
    
    @JsonProperty("worked_hours") 
    private String workedHours;
    
    @JsonProperty("over_time")
    private String overTime;
    
    @JsonProperty("short_time")
    private String shortTime;
    
    private List<AttendanceRecordDTO> children;
}