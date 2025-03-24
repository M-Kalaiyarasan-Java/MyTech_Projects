package com.example.hrms.hrms.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hrms.hrms.dto.AttendanceRecordDTO;
import com.example.hrms.hrms.dto.AttendanceReportDTO;
import com.example.hrms.hrms.model.Attendance;
import com.example.hrms.hrms.model.Employee;
import com.example.hrms.hrms.repo.AttendanceRepository;
import com.example.hrms.hrms.repo.EmployeeRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AttendanceTimeReportServiceImpl implements AttendanceTimeReportService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");
    private final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

    @Override
    public List<AttendanceReportDTO> getAttendanceReport(String month, String year) {
        // Convert month and year to date format for database queries
        YearMonth yearMonth = getYearMonth(month, year);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // Get all employees
        List<Employee> employees = employeeRepository.findAll();

        // Get all attendance records for the month
        List<Attendance> attendanceRecords = attendanceRepository.findByDateBetween(startDate, endDate);

        // Group attendance records by employee
        Map<Long, List<Attendance>> attendanceByEmployee = attendanceRecords.stream()
                .collect(Collectors.groupingBy(record -> record.getEmployee().getId()));

        // Create DTOs for each employee
        return employees.stream()
                .map(employee -> createEmployeeAttendanceReport(employee, attendanceByEmployee.get(employee.getId()),
                        month, year))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void syncAttendanceData(String month, String year) {
        // Convert month and year to date format for database queries
        YearMonth yearMonth = getYearMonth(month, year);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        // Get all employees
        List<Employee> employees = employeeRepository.findAll();

        // For each employee, check if all required attendance records exist, if not,
        // create them
        for (Employee employee : employees) {
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                // Skip weekends if your policy does not require attendance on weekends
                if (date.getDayOfWeek().getValue() > 5) {
                    continue;
                }

                // Check if attendance record exists
                boolean recordExists = attendanceRepository.existsByEmployeeAndDate(employee, date);

                if (!recordExists) {
                    // Create a new default attendance record
                    Attendance attendance = new Attendance();
                    attendance.setEmployee(employee);
                    attendance.setDate(date);
                    attendance.setInTime("09:00"); // Default values
                    attendance.setOutTime("17:00"); // Default values
                    attendance.setStatus("Present"); // Default status

                    attendanceRepository.save(attendance);
                }
            }
        }
    }

    @Override
    public Resource generateExcelReport(List<AttendanceReportDTO> dataset, String month, String year) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Attendance Report");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = { "Employee", "Month", "Total Hours", "Worked Hours", "Overtime", "Short Time" };

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            // Create data rows
            int rowNum = 1;
            for (AttendanceReportDTO report : dataset) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(report.getEmployee());
                row.createCell(1).setCellValue(report.getMonth());
                row.createCell(2).setCellValue(report.getTotalHours());
                row.createCell(3).setCellValue(report.getWorkedHours());
                row.createCell(4).setCellValue(report.getOverTime());
                row.createCell(5).setCellValue(report.getShortTime());

                // If there are children records, add them below with indentation
                if (report.getChildren() != null && !report.getChildren().isEmpty()) {
                    for (AttendanceRecordDTO dailyRecord : report.getChildren()) {
                        Row childRow = sheet.createRow(rowNum++);
                        childRow.createCell(0).setCellValue("    " + dailyRecord.getMonth()); // Date with indentation
                        childRow.createCell(1).setCellValue(""); // Empty for month column
                        childRow.createCell(2).setCellValue(dailyRecord.getTotalHours());
                        childRow.createCell(3).setCellValue(dailyRecord.getWorkedHours());
                        childRow.createCell(4).setCellValue(dailyRecord.getOverTime());
                        childRow.createCell(5).setCellValue(dailyRecord.getShortTime());
                    }
                }
            }

            // Write to output stream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            return new ByteArrayResource(outputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }

    private AttendanceReportDTO createEmployeeAttendanceReport(
            Employee employee,
            List<Attendance> attendanceRecords,
            String month,
            String year) {

        AttendanceReportDTO reportDTO = new AttendanceReportDTO();
        reportDTO.setEmployee(employee.getName());
        reportDTO.setMonth(month);

        // Calculate total expected work hours for the month
        double totalHours = calculateTotalHours(month, year);
        reportDTO.setTotalHours(String.format("%.1f", totalHours));

        // Process attendance records
        if (attendanceRecords != null && !attendanceRecords.isEmpty()) {
            double workedHours = 0;
            List<AttendanceRecordDTO> dailyRecords = new ArrayList<>();

            for (Attendance record : attendanceRecords) {
                // Calculate daily hours
                double dailyHours = calculateDailyHours(record.getInTime(), record.getOutTime());
                workedHours += dailyHours;

                // Create daily record
                AttendanceRecordDTO dailyRecord = new AttendanceRecordDTO();
                dailyRecord.setMonth(record.getDate().format(DateTimeFormatter.ofPattern("dd-MMM")));
                dailyRecord.setTotalHours(String.format("%.1f", 8.0)); // Standard daily hours
                dailyRecord.setWorkedHours(String.format("%.1f", dailyHours));

                // Calculate overtime or short time
                if (dailyHours > 8.0) {
                    dailyRecord.setOverTime(String.format("%.1f", dailyHours - 8.0));
                    dailyRecord.setShortTime("0.0");
                } else {
                    dailyRecord.setOverTime("0.0");
                    dailyRecord.setShortTime(String.format("%.1f", 8.0 - dailyHours));
                }

                dailyRecords.add(dailyRecord);
            }

            // Set summary data
            reportDTO.setWorkedHours(String.format("%.1f", workedHours));

            if (workedHours > totalHours) {
                reportDTO.setOverTime(String.format("%.1f", workedHours - totalHours));
                reportDTO.setShortTime("0.0");
            } else {
                reportDTO.setOverTime("0.0");
                reportDTO.setShortTime(String.format("%.1f", totalHours - workedHours));
            }

            // Add daily records as children
            reportDTO.setChildren(dailyRecords);
        } else {
            // No attendance records found
            reportDTO.setWorkedHours("0.0");
            reportDTO.setOverTime("0.0");
            reportDTO.setShortTime(String.format("%.1f", totalHours));
            reportDTO.setChildren(new ArrayList<>());
        }

        return reportDTO;
    }

    private int calculateDailyHours(String inTime, String outTime) {
    if (inTime == null || outTime == null || inTime.isEmpty() || outTime.isEmpty()) {
        return 0;
    }

    try {
        // Create a formatter that can parse time with AM/PM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh a", Locale.ENGLISH);

        // Parse the input time strings
        LocalTime in = LocalTime.parse(inTime.trim(), formatter);
        LocalTime out = LocalTime.parse(outTime.trim(), formatter);

        // Handle cases where out time is before in time (next day)
        if (out.isBefore(in)) {
            out = out.plusHours(24);
        }

        // Calculate the duration in hours
        Duration duration = Duration.between(in, out);
        return (int) duration.toHours();
    } catch (Exception e) {
        // logger.error("Error calculating hours between {} and {}: {}", inTime, outTime, e.getMessage());
        return 0; // Return 0 hours on error
    }
}

    private double calculateTotalHours(String month, String year) {
        YearMonth yearMonth = getYearMonth(month, year);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        int workingDays = 0;
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // Skip weekends
            if (date.getDayOfWeek().getValue() <= 5) {
                workingDays++;
            }
        }

        // Standard 8-hour workday
        return workingDays * 8.0;
    }

    // Replace the problematic getYearMonth method in
    // AttendanceTimeReportServiceImpl.java with this:

    private YearMonth getYearMonth(String monthStr, String yearStr) {
        try {
            int year = Integer.parseInt(yearStr);
            int month;

            // Handle month strings with two digits (e.g., "03")
            if (monthStr.length() == 2 && monthStr.startsWith("0")) {
                month = Integer.parseInt(monthStr.substring(1));
            } else {
                month = Integer.parseInt(monthStr);
            }

            // Ensure month is within valid range (1-12)
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("Invalid month: " + monthStr);
            }

            return YearMonth.of(year, month);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid month or year format: month=" + monthStr + ", year=" + yearStr);
        }
    }
}