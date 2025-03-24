package com.example.hrms.hrms.service;

import com.example.hrms.hrms.model.EmployeeLeave;
import com.example.hrms.hrms.model.LeaveDetail;
import com.example.hrms.hrms.repo.EmployeeLeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;

    public List<EmployeeLeave> getEmployeeLeaveData(String month, String year) {
        List<EmployeeLeave> employees;
        
        if (month != null && year != null) {
            employees = employeeLeaveRepository.findByMonthAndYear(month, year);
        } else if (month != null) {
            employees = employeeLeaveRepository.findByMonth(month);
        } else if (year != null) {
            employees = employeeLeaveRepository.findByYear(year);
        } else {
            Calendar calendar = Calendar.getInstance();
            String currentMonth = getMonthFullName(calendar.get(Calendar.MONTH));
            String currentYear = String.valueOf(calendar.get(Calendar.YEAR));
            employees = employeeLeaveRepository.findByMonthAndYear(currentMonth, currentYear);
        }
        
        return employees.stream()
                .map(this::processEmployeeLeave)
                .collect(Collectors.toList());
    }

    private EmployeeLeave processEmployeeLeave(EmployeeLeave employeeLeave) {
        Map<String, LeaveDetail> leaveDetails = new HashMap<>();
        
        if (employeeLeave.getAnnualLeaveTotal() != null) {
            leaveDetails.put("annual_leave", new LeaveDetail(
                employeeLeave.getAnnualLeaveTotal(),
                employeeLeave.getAnnualLeaveAvailed(),
                employeeLeave.getAnnualLeaveBalance()
            ));
        }
        
        if (employeeLeave.getSickLeaveTotal() != null) {
            leaveDetails.put("sick_leave", new LeaveDetail(
                employeeLeave.getSickLeaveTotal(),
                employeeLeave.getSickLeaveAvailed(),
                employeeLeave.getSickLeaveBalance()
            ));
        }
        
        if (employeeLeave.getCasualLeaveTotal() != null) {
            leaveDetails.put("casual_leave", new LeaveDetail(
                employeeLeave.getCasualLeaveTotal(),
                employeeLeave.getCasualLeaveAvailed(),
                employeeLeave.getCasualLeaveBalance()
            ));
        }
        
        employeeLeave.setLeaveDetails(leaveDetails);
        return employeeLeave;
    }

    public List<String> getAllDepartments() {
        return employeeLeaveRepository.findAllDepartments();
    }

    public List<String> getAllDesignations() {
        return employeeLeaveRepository.findAllDesignations();
    }

    public List<Map<String, Object>> getAllEmployees() {
        return employeeLeaveRepository.findAllEmployees();
    }

    public List<String> getAllLeaveTypes() {
        return Arrays.asList("annual", "sick", "casual");
    }

    public Map<String, Object> getEmployeeLeaveDetails(Long employeeId, String year) {
        List<EmployeeLeave> employeeLeaves = (year != null) 
            ? employeeLeaveRepository.findByEmployeeIdAndYear(employeeId, year)
            : employeeLeaveRepository.findByEmployeeId(employeeId);
        
        Map<String, Object> result = new HashMap<>();
        
        if (!employeeLeaves.isEmpty()) {
            EmployeeLeave firstRecord = employeeLeaves.get(0);
            result.put("employeeId", firstRecord.getEmployeeId());
            result.put("employeeName", firstRecord.getApEmpName());
            result.put("department", firstRecord.getDepartmentName());
            result.put("designation", firstRecord.getDesignationName());
            
            Map<String, Map<String, LeaveDetail>> monthlyLeaves = new HashMap<>();
            
            for (EmployeeLeave leave : employeeLeaves) {
                monthlyLeaves.put(leave.getMonth(), processEmployeeLeave(leave).getLeaveDetails());
            }
            
            result.put("monthlyLeaves", monthlyLeaves);
        }
        
        return result;
    }

    private String getMonthFullName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
                               "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
}
