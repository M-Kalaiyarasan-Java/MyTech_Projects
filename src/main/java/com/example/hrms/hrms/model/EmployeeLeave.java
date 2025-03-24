package com.example.hrms.hrms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_leave")
public class EmployeeLeave {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "employee_id")
    private Long employeeId;
    
    @Column(name = "ap_emp_name")
    private String apEmpName;
    
    @Column(name = "department_name")
    private String departmentName;
    
    @Column(name = "designation_name")
    private String designationName;
    
    @Column(name = "annual_leave_total")
    private Double annualLeaveTotal;
    
    @Column(name = "annual_leave_availed")
    private Double annualLeaveAvailed;
    
    @Column(name = "annual_leave_balance")
    private Double annualLeaveBalance;
    
    @Column(name = "sick_leave_total")
    private Double sickLeaveTotal;
    
    @Column(name = "sick_leave_availed")
    private Double sickLeaveAvailed;
    
    @Column(name = "sick_leave_balance")
    private Double sickLeaveBalance;
    
    @Column(name = "casual_leave_total")
    private Double casualLeaveTotal;
    
    @Column(name = "casual_leave_availed")
    private Double casualLeaveAvailed;
    
    @Column(name = "casual_leave_balance")
    private Double casualLeaveBalance;
    
    @Column(name = "year")
    private String year;
    
    @Column(name = "month")
    private String month;
    
    @Transient
    private Map<String, LeaveDetail> leaveDetails;
}


