package com.example.hrms.hrms.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

/**
 * Entity class representing the Config Table for API configuration management.
 */
@Entity
@Table(name = "config_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @Column(nullable = false)
    private String erp;

    @Column(nullable = false)
    private String name;

    @Column(name = "table_name", nullable = false)
    private String table_name;

    // Client & Company Details
    @Column(name = "client_id", nullable = false)
    private Integer client_id;

    @Column(name = "entity_id", nullable = false)
    private Integer entity_id;

    @Column(name = "company_name", nullable = false)
    private String company_name;

    @Column(name = "company_id", nullable = false)
    private String company_id;

    // API Authentication
    @Column(name = "access_token", nullable = false, columnDefinition = "TEXT")
    private String access_token;

    @Column(name = "refresh_token", nullable = false, columnDefinition = "TEXT")
    private String refresh_token;

    @Column(name = "access_token_expires_at", nullable = false)
    private LocalDateTime access_token_expires_at;

    @Column(name = "refresh_token_expires_at", nullable = false)
    private LocalDateTime refresh_token_expires_at;

    // Schedule & Action
    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private LocalDateTime time;

    // Execution Status
    @Column(name = "started_at")
    private LocalDateTime started_at;

    @Column(name = "completed_at")
    private LocalDateTime completed_at;

    // Error Logs
    @ElementCollection
    @CollectionTable(name = "error_logs", joinColumns = @JoinColumn(name = "config_id"))
    @Column(name = "log", columnDefinition = "TEXT")
    private List<String> error_logs = new ArrayList<>();

    // Timestamps for record-keeping
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "active")

    private Boolean active = true;
}
