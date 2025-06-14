package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "daily_report", schema = "SYSTEM")
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

    private BigDecimal dailyCost;

    private String depth;

    private String phaseName;

    private String progress;

    private BigDecimal actualday;

    private BigDecimal plannedday;

    // private int timeSpent;

    private int totalDuration;

    @ElementCollection
    @CollectionTable(name = "work_done", joinColumns = @JoinColumn(name = "daily_report_id"))
    @Column(name = "work_done", columnDefinition = "CLOB")
    private List<String> workDone; // Replaced String[] with List<String>

    private boolean issuesReported;

    @ElementCollection
    @CollectionTable(name = "comments_drillwell", joinColumns = @JoinColumn(name = "daily_report_id"))
    @Column(name = "comment_drillwell", columnDefinition = "CLOB")
    private List<String> comments;

    // Default constructor
    public DailyReport() {
    }

    // Constructor with all fields (except id)
    public DailyReport(DrillWell drillingWell, LocalDate reportDate, BigDecimal totalCost, int totalDuration,
            List<String> workDone, boolean issuesReported, List<String> comments) {
        this.drillingWell = drillingWell;
        this.reportDate = reportDate;
        this.totalCost = totalCost;
        this.totalDuration = totalDuration;
        this.workDone = workDone;
        this.issuesReported = issuesReported;
        this.comments = comments;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DrillWell getDrillingWell() {
        return drillingWell;
    }

    public void setDrillingWell(DrillWell drillingWell) {
        this.drillingWell = drillingWell;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(BigDecimal dailyCost) {
        this.dailyCost = dailyCost;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    public BigDecimal  getActualDay() {
        return actualday;
    }

    public void setActualDay(BigDecimal actualday) {
        this.actualday = actualday;
    }

    public BigDecimal getPlannedDay() {
        return plannedday;
    }

    public void setPlannedDay(BigDecimal plannedday) {
        this.plannedday = plannedday;
    }

    public List<String> getWorkDone() {
        return workDone;
    }

    public void setWorkDone(List<String> workDone) {
        this.workDone = workDone;
    }

    public boolean isIssuesReported() {
        return issuesReported;
    }

    public void setIssuesReported(boolean issuesReported) {
        this.issuesReported = issuesReported;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    /*
     * public int getTimeSpent() {
     * return timeSpent;
     * }
     * 
     * public void setTimeSpent(int timeSpent) {
     * this.timeSpent = timeSpent;
     * }
     */
}
