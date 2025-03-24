package com.example.hrms.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hrms.hrms.dto.AttendanceReportDTO;
import com.example.hrms.hrms.dto.ExcelGenerationRequest;
import com.example.hrms.hrms.service.AttendanceTimeReportService;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class AttendanceTimeReportController {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceTimeReportController.class);

    @Autowired
    private AttendanceTimeReportService attendanceTimeReportService;

    private static final Map<String, String> MONTH_MAP = new HashMap<>();

    static {
        MONTH_MAP.put("january", "01");
        MONTH_MAP.put("february", "02");
        MONTH_MAP.put("march", "03");
        MONTH_MAP.put("april", "04");
        MONTH_MAP.put("may", "05");
        MONTH_MAP.put("june", "06");
        MONTH_MAP.put("july", "07");
        MONTH_MAP.put("august", "08");
        MONTH_MAP.put("september", "09");
        MONTH_MAP.put("october", "10");
        MONTH_MAP.put("november", "11");
        MONTH_MAP.put("december", "12");
    }

    @GetMapping("/hrms_attendance_time_report")
    public ResponseEntity<Map<String, Object>> getAttendanceTimeReport(
            @RequestParam String current_month,
            @RequestParam String year) {

        String monthValue = convertMonth(current_month);

        logger.info("Fetching attendance report for month: {} and year: {}", monthValue, year);

        List<AttendanceReportDTO> data = attendanceTimeReportService.getAttendanceReport(monthValue, year);
        Map<String, Object> response = Map.of("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/hrms_store_attendance_muster")
    public ResponseEntity<Map<String, String>> updateMusterDetails(
            @RequestBody Map<String, String> params) {

        String monthValue = convertMonth(params.get("current_month"));
        String year = params.get("year");

        logger.info("Syncing attendance data for month: {} and year: {}", monthValue, year);

        attendanceTimeReportService.syncAttendanceData(monthValue, year);

        return ResponseEntity.ok(Map.of("message", "Attendance data synchronized successfully"));
    }

    @PostMapping("/generate_excel")
    public ResponseEntity<Resource> generateExcel(@RequestBody ExcelGenerationRequest request) {
        String monthValue = convertMonth(request.getMonth());

        logger.info("Generating Excel report for month: {} and year: {}", monthValue, request.getYear());

        Resource excelResource = attendanceTimeReportService.generateExcelReport(
                request.getDataset(),
                monthValue,
                request.getYear()
        );

        String filename = "Attendance_Report_" + request.getYear() + "_" + monthValue + ".xlsx";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(excelResource);
    }

    private String convertMonth(String month) {
        if (month == null || month.trim().isEmpty()) {
            throw new IllegalArgumentException("Month parameter is required");
        }

        String formattedMonth = month.toLowerCase().trim();
        
        if (MONTH_MAP.containsKey(formattedMonth)) {
            return MONTH_MAP.get(formattedMonth);  // Convert full month name to number with leading zero
        } else if (formattedMonth.matches("^(0?[1-9]|1[0-2])$")) {
            // Ensure two-digit format (with leading zero) for months 1-9
            int monthNum = Integer.parseInt(formattedMonth);
            return String.format("%02d", monthNum);
        } else {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
    }
}