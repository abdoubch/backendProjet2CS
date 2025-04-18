package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "problemes",schema = "SYSTEM")
public class Problemes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name; // Name of the problem

    @Column(nullable = false)
    private String type; // Type of problem

    @ManyToOne
    @JoinColumn(name = "daily_report_id", nullable = false)
    private DailyReport dailyReport;

    @ElementCollection
    @CollectionTable(name = "problemes_solutions", joinColumns = @JoinColumn(name = "probleme_id"))
    @Column(name = "solution")
    private List<String> solutions;

    // Constructors
    public Problemes() {}

    public Problemes(String name, String type, DailyReport dailyReport, List<String> solutions) {
        this.name = name;
        this.type = type;
        this.dailyReport = dailyReport;
        this.solutions = solutions;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public DailyReport getDailyReport() { return dailyReport; }
    public void setDailyReport(DailyReport dailyReport) { this.dailyReport = dailyReport; }

    public List<String> getSolutions() { return solutions; }
    public void setSolutions(List<String> solutions) { this.solutions = solutions; }
}
