package com.example.hrms.hrms.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponse {
    private String status;
    private List<EmployeeLeave> data;
}
