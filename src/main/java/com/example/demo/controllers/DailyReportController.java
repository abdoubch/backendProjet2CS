package com.example.demo.controllers;

import com.example.demo.model.DailyReport;
import com.example.demo.services.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/daily-reports")
public class DailyReportController {

    @Autowired
    private DailyReportService dailyReportService;

    // Endpoint to import Daily Reports from Excel file
    @PostMapping("/import")
    public ResponseEntity<?> importDailyReports(@RequestParam("file") MultipartFile file,@RequestParam("drillWellId") int drillWellId) {
        try {
            List<DailyReport> reports = dailyReportService.importFromExcel(file,drillWellId);
            return new ResponseEntity<>(reports, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to import reports: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to get reports by drill well ID
    @GetMapping("/well/{drillWellId}")
    public ResponseEntity<List<DailyReport>> getReportsByDrillWellId(@PathVariable int drillWellId) {
        List<DailyReport> reports = dailyReportService.getReportsByDrillWellId(drillWellId);
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
}
