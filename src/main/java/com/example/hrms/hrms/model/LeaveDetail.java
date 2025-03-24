package com.example.hrms.hrms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
 public class LeaveDetail {
    private Double total;
    private Double availed;
    private Double balance;
}
