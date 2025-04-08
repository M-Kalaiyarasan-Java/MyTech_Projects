package com.example.hrms.hrms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hrms.hrms.model.EmployeeLeave;
import com.example.hrms.hrms.service.LeaveService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestController
@CrossOrigin("*")
@RequestMapping("/api")

public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/hrms_leave_card_report")
    public ResponseEntity<Map<String, Object>> getLeaveCardReport(
            @RequestParam(value = "curent_month", required = false) String month,
            @RequestParam(value = "year", required = false) String year) {
        
        System.out.println("Fetching data for: Month = " + month + ", Year = " + year); // Debugging
        
        List<EmployeeLeave> employees = leaveService.getEmployeeLeaveData(month, year);
    
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", employees);
        
        return ResponseEntity.ok(response);
    }
    

    @GetMapping("/departments")
    public ResponseEntity<List<String>> getAllDepartments() {
        List<String> departments = leaveService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/designations")
    public ResponseEntity<List<String>> getAllDesignations() {
        List<String> designations = leaveService.getAllDesignations();
        return ResponseEntity.ok(designations);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Map<String, Object>>> getAllEmployees() {
        List<Map<String, Object>> employees = leaveService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/leave-types")
    public ResponseEntity<List<String>> getAllLeaveTypes() {
        List<String> leaveTypes = leaveService.getAllLeaveTypes();
        return ResponseEntity.ok(leaveTypes);
    }

    @GetMapping("/employee/{id}/leave-details")
    public ResponseEntity<Map<String, Object>> getEmployeeLeaveDetails(
            @PathVariable Long id,
            @RequestParam(required = false) String year) {
        
        Map<String, Object> leaveDetails = leaveService.getEmployeeLeaveDetails(id, year);
        return ResponseEntity.ok(leaveDetails);
    }
}