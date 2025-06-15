package com.example.demo.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "drilling_well", schema = "SYSTEM")
public class DrillWell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate plannedEndDate;

    private LocalDate actualEndDate;

    @Column(nullable = false)
    private String status;

    private BigDecimal totalCost;

    private BigDecimal cumulativeCost1;

    private BigDecimal cumulativeCost2;

    private BigDecimal cumulativeCost3;

    private BigDecimal cumulativeCost4;

    private BigDecimal plannedCost1;

    private BigDecimal plannedCost2;

    private BigDecimal plannedCost3;

    private BigDecimal plannedCost4;

    private String depth;

    private String depth1;

    private String depth2;

    private String depth3;

    private String depth4;

    private String plannedDepth1;

    private String plannedDepth2;

    private String plannedDepth3;

    private String plannedDepth4;

    private BigDecimal actualDay;

    private BigDecimal actualDay1;

    private BigDecimal actualDay2;

    private BigDecimal actualDay3;

    private BigDecimal actualDay4;

    private BigDecimal plannedDay1;

    private BigDecimal plannedDay2;

    private BigDecimal plannedDay3;

    private BigDecimal plannedDay4;

    @Column(nullable = false)
    private String type;

    @Column
    private String wellName; // Nom du puits

    @Column
    private Integer progress; // Pourcentage de progression (0 à 100)

    @Column
    private Integer phaseId; // Pourcentage de progression (0 à 100)

    @Column
    private String state; // État synthétique : "À risque", "En retard", etc.

    @OneToMany(mappedBy = "drillingWell", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Manages serialization and prevents recursion
    private List<Planning> plannings;

    public void addPlanning(Planning planning) {
        this.plannings.add(planning);
        planning.setDrillingWell(this);
    }

    // Constructor
    public DrillWell() {
    }

    public DrillWell(String location, LocalDate startDate, String status, String type) {
        this.location = location;
        this.startDate = startDate;
        this.status = status;
        this.type = type;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(LocalDate plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public LocalDate getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(LocalDate actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlannedDepth1() {
        return plannedDepth1;
    }

    public void setPlannedDepth1(String plannedDepth1) {
        this.plannedDepth1 = plannedDepth1;
    }

    public String getPlannedDepth2() {
        return plannedDepth2;
    }

    public void setPlannedDepth2(String plannedDepth2) {
        this.plannedDepth2 = plannedDepth2;
    }

    public String getPlannedDepth3() {
        return plannedDepth3;
    }

    public void setPlannedDepth3(String plannedDepth3) {
        this.plannedDepth3 = plannedDepth3;
    }

    public String getPlannedDepth4() {
        return plannedDepth4;
    }

    public void setPlannedDepth4(String plannedDepth4) {
        this.plannedDepth4 = plannedDepth4;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getDepth1() {
        return depth1;
    }

    public void setDepth1(String depth1) {
        this.depth1 = depth1;
    }

    public String getDepth2() {
        return depth2;
    }

    public void setDepth2(String depth2) {
        this.depth2 = depth2;
    }

    public String getDepth3() {
        return depth3;
    }

    public void setDepth3(String depth3) {
        this.depth3 = depth3;
    }

    public String getDepth4() {
        return depth4;
    }

    public void setDepth4(String depth4) {
        this.depth4 = depth4;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getCumulativeCost1() {
        return cumulativeCost1;
    }

    public void setCumulativeCost1(BigDecimal cumulativeCost1) {
        this.cumulativeCost1 = cumulativeCost1;
    }

    public BigDecimal getCumulativeCost2() {
        return cumulativeCost2;
    }

    public void setCumulativeCost2(BigDecimal cumulativeCost2) {
        this.cumulativeCost2 = cumulativeCost2;
    }

    public BigDecimal getCumulativeCost3() {
        return cumulativeCost3;
    }

    public void setCumulativeCost3(BigDecimal cumulativeCost3) {
        this.cumulativeCost3 = cumulativeCost3;
    }

    public BigDecimal getCumulativeCost4() {
        return cumulativeCost4;
    }

    public void setCumulativeCost4(BigDecimal cumulativeCost4) {
        this.cumulativeCost4 = cumulativeCost4;
    }

    public BigDecimal getPlannedCost1() {
        return plannedCost1;
    }

    public void setPlannedCost1(BigDecimal plannedCost1) {
        this.plannedCost1 = plannedCost1;
    }

    public BigDecimal getPlannedCost2() {
        return plannedCost2;
    }

    public void setPlannedCost2(BigDecimal plannedCost2) {
        this.plannedCost2 = plannedCost2;
    }

    public BigDecimal getPlannedCost3() {
        return plannedCost3;
    }

    public void setPlannedCost3(BigDecimal plannedCost3) {
        this.plannedCost3 = plannedCost3;
    }

    public BigDecimal getPlannedCost4() {
        return plannedCost4;
    }

    public void setPlannedCost4(BigDecimal plannedCost4) {
        this.plannedCost4 = plannedCost4;
    }

    public BigDecimal getActualDay() {
        return actualDay;
    }

    public void setActualDay(BigDecimal actualDay) {
        this.actualDay = actualDay;
    }

    public BigDecimal getActualDay1() {
        return actualDay1;
    }

    public void setActualDay1(BigDecimal actualDay1) {
        this.actualDay1 = actualDay1;
    }

    public BigDecimal getActualDay2() {
        return actualDay2;
    }

    public void setActualDay2(BigDecimal actualDay2) {
        this.actualDay2 = actualDay2;
    }

    public BigDecimal getActualDay3() {
        return actualDay3;
    }

    public void setActualDay3(BigDecimal actualDay3) {
        this.actualDay3 = actualDay3;
    }

    public BigDecimal getActualDay4() {
        return actualDay4;
    }

    public void setActualDay4(BigDecimal actualDay4) {
        this.actualDay4 = actualDay4;
    }

    public BigDecimal getPlannedDay1() {
        return plannedDay1;
    }

    public void setPlannedDay1(BigDecimal plannedDay1) {
        this.plannedDay1 = plannedDay1;
    }

    public BigDecimal getPlannedDay2() {
        return plannedDay2;
    }

    public void setPlannedDay2(BigDecimal plannedDay2) {
        this.plannedDay2 = plannedDay2;
    }

    public BigDecimal getPlannedDay3() {
        return plannedDay3;
    }

    public void setPlannedDay3(BigDecimal plannedDay3) {
        this.plannedDay3 = plannedDay3;
    }

    public BigDecimal getPlannedDay4() {
        return plannedDay4;
    }

    public void setPlannedDay4(BigDecimal plannedDay4) {
        this.plannedDay4 = plannedDay4;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Planning> getPlannings() {
        return plannings;
    }

    public void setPlannings(List<Planning> plannings) {
        this.plannings = plannings;
    }

    public String getWellName() {
        return wellName;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
