
package com.example.demo.repository;

import com.example.demo.model.Problemes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProblemesRepository extends JpaRepository<Problemes, Integer> {
    List<Problemes> findByDailyReportId(int dailyReportId);
    @Query("SELECT p FROM Problemes p WHERE p.dailyReport.drillingWell.id = :drillWellId")
    List<Problemes> findByDrillWellId(int drillWellId);
    void deleteByDailyReportId(int dailyReportId);
}
