package com.example.hrms.hrms.service;

import org.springframework.core.io.Resource;

import com.example.hrms.hrms.dto.AttendanceReportDTO;

import java.util.List;

public interface AttendanceTimeReportService {
    
    
    /**
     * Retrieves the attendance report for the specified month and year
     * 
     * @param month Month in "MMM" format (e.g. "Jan", "Feb")
     * @param year Year in "YYYY" format (e.g. "2023")
     * @return List of employee attendance reports
     */
    List<AttendanceReportDTO> getAttendanceReport(String month, String year);
    
    /**
     * Synchronizes attendance data for the specified month and year
     * Creates any missing attendance records for all employees
     * 
     * @param month Month in "MMM" format (e.g. "Jan", "Feb")
     * @param year Year in "YYYY" format (e.g. "2023")
     */
    void syncAttendanceData(String month, String year);
    
    /**
     * Generates an Excel report for the provided attendance data
     * 
     * @param dataset List of attendance report DTOs
     * @param month Month in "MMM" format
     * @param year Year in "YYYY" format
     * @return Resource containing the Excel file
     */
    Resource generateExcelReport(List<AttendanceReportDTO> dataset, String month, String year);
}
