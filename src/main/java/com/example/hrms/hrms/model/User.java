package com.example.hrms.hrms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String loginId;

    private Boolean active = true;
    private Integer createdBy;
    private Integer updatedBy;
    private Integer reportingTo;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    private Role role;

    private String passwordHash;

    @Transient
    private String password;

    @Autowired
    private transient PasswordEncoder passwordEncoder;

    @PrePersist
    @PreUpdate
    public void hashPassword() {
        if (this.password != null) {
            this.passwordHash = passwordEncoder.encode(this.password);
        }
    }

    // Getters & Setters
}
