package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "stages")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "drilling_well_id", nullable = false)
    private DrillWell drillingWell;

    @Column(nullable = false)
    private String name;

    private LocalDate plannedStartDate;

    private LocalDate plannedEndDate;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;

    private BigDecimal plannedCost;

    private BigDecimal actualCost;

    @Column(columnDefinition = "TEXT")
    private String description;


    // Getters, setters, constructors
}
