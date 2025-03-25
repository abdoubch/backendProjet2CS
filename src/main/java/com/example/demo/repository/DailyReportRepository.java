package com.example.demo.repository;

import com.example.demo.model.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DailyReportRepository extends JpaRepository<DailyReport, Integer> {
    List<DailyReport> findByDrillingWellId(int drillWellId);
}
