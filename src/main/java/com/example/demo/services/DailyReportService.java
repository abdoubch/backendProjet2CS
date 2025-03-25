package com.example.demo.services;

import com.example.demo.model.DailyReport;
import com.example.demo.model.DrillWell;
import com.example.demo.repository.DailyReportRepository;
import com.example.demo.repository.DrillWellRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyReportService {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private DrillWellRepository drillWellRepository; // Add DrillWellRepository to find DrillWell by ID

    public List<DailyReport> importFromExcel(MultipartFile file, int drillWellId) throws IOException {
        List<DailyReport> reports = new ArrayList<>();

        // Find the DrillWell by ID
        DrillWell drillWell = drillWellRepository.findById(drillWellId)
                .orElseThrow(() -> new IllegalArgumentException("DrillWell not found for ID: " + drillWellId));

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);  // Assume data is on the first sheet
        Sheet sheetCost = workbook.getSheetAt(1);
        DailyReport report = new DailyReport();

        // Set the DrillWell to associate it with the report
        report.setDrillingWell(drillWell);

        // Example for retrieving other fields, skipping this part for brevity

        // WorkDone: Stop on null or blank string
        List<String> workDoneList = new ArrayList<>();
        for (int i = 21; i <= 42; i++) {  // Adjust the row indexes to match your data
            Cell workDoneCell = sheet.getRow(i).getCell(8);
            if (workDoneCell != null && workDoneCell.getCellType() == CellType.STRING) {
                workDoneList.add(workDoneCell.getStringCellValue().trim());
            }
        }
        report.setWorkDone(workDoneList);

        // Comments: Stop on null or blank string
        List<String> commentsList = new ArrayList<>();
        for (int i = 45; i <= 56; i++) {  // Adjust the row indexes to match your data
            Cell commentsCell = sheet.getRow(i).getCell(1);
            if (commentsCell != null && commentsCell.getCellType() == CellType.STRING) {
                commentsList.add(commentsCell.getStringCellValue().trim());
            }
        }
        report.setComments(commentsList);

        // Set other fields as needed (example: totalCost, totalDuration, etc.)
        // Example:
        report.setReportDate(LocalDate.now());  // Example of setting report date
        report.setTotalCost(BigDecimal.valueOf(sheetCost.getRow(85).getCell(6).getNumericCellValue()));  // Example of setting total cost
        report.setTotalDuration(8);  // Example of setting total duration

        // Add the report to the list
        reports.add(report);

        // Save all reports to the database
        return dailyReportRepository.saveAll(reports);
    }

    public List<DailyReport> getReportsByDrillWellId(int drillWellId) {
        return dailyReportRepository.findByDrillingWellId(drillWellId);
    }
}
