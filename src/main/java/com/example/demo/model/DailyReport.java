package com.example.demo.model;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "daily_report")
public class DailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "drilling_well_id", nullable = false)
    private DrillWell drillingWell;

    @Column(nullable = false)
    private LocalDate reportDate;

    private BigDecimal totalCost;

    private int totalDuration;

    @Column(columnDefinition = "TEXT")
    private String workDone;

    private boolean issuesReported;

    @Column(columnDefinition = "TEXT")
    private String comments;

    // Getters, setters, constructors
}
