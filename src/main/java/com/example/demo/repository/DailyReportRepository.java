package com.example.demo.repository;

import java.util.Optional;
import com.example.demo.model.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Integer> {
    List<DailyReport> findByDrillingWellId(int drillWellId);
    Optional<DailyReport> findByIdAndDrillingWellId(int reportId, int drillWellId);
    List<DailyReport> findByDrillingWellIdAndPhaseId(int drillingWellId, int phaseId);
}
