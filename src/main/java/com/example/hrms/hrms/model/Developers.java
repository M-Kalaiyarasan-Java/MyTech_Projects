package com.example.hrms.hrms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "developers")
public class Developers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String emp_first_name;
    private String emp_middle_name;
    private String emp_last_name;

    private String gender;
    private String marital_status;

    private String dob;

    @Email
    @Column(unique = true)
    private String email;

    private String mobile_no;
    private String designation;

    private Integer department_name; // assuming it's an ID
    private Integer reporting_to; // assuming it's an ID

    private Boolean active = true;
    private Integer createdBy;
    private Integer updatedBy;

    private String doj_office;

    private String aadhaar_no;
    private String pan_no;

    private String bank_name;
    private String bank_branch_name;
    private String bank_ifsc_code;
    private String bank_account_no;

    private String communication_address;
    private String comm_add_city;
    private String comm_add_pincode;
    private String comm_add_state;
    private String comm_add_country;

    private String permenant_address;
    private String permenant_add_city;
    private String permenant_add_pincode;
    private String permenant_add_state;
    private String permenant_add_country;

    private String password;
    private String confirm_password;

    private String resign_reson;
    private String resign_date;

    private String emp_full_name;

    // Getters & Setters
}
