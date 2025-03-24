package com.example.hrms.hrms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttendanceRecordDTO {
    private String month; // Using month field for date display in daily records
    
    @JsonProperty("total_hours")
    private String totalHours;
    
    @JsonProperty("worked_hours") 
    private String workedHours;
    
    @JsonProperty("over_time")
    private String overTime;
    
    @JsonProperty("short_time")
    private String shortTime;
}
