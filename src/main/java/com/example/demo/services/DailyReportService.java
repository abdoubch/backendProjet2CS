package com.example.demo.services;

import com.example.demo.model.DailyReport;
import com.example.demo.model.DrillWell;
import com.example.demo.repository.DailyReportRepository;
import com.example.demo.repository.DrillWellRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Service
public class DailyReportService {

    @Autowired
    private DailyReportRepository dailyReportRepository;

    @Autowired
    private DrillWellRepository drillWellRepository; // Add DrillWellRepository to find DrillWell by ID

    public DailyReport createDailyReport(DailyReport dailyReport) {
        return dailyReportRepository.save(dailyReport);
    }

    public List<DailyReport> getReportsByDrillWellAndPhase(int wellId, int phaseId) {
        return dailyReportRepository.findByDrillingWellIdAndPhaseId(wellId, phaseId);
    }

    public DailyReport createReportForDrillWell(int drillWellId, DailyReport report) {
        DrillWell drillWell = drillWellRepository.findById(drillWellId)
                .orElseThrow(() -> new RuntimeException("DrillWell not found"));

        report.setDrillingWell(drillWell);
        return dailyReportRepository.save(report);
    }

    public List<DailyReport> importFromExcel(MultipartFile file, int drillWellId) throws IOException {
        List<DailyReport> reports = new ArrayList<>();

        // Find the DrillWell by ID
        DrillWell drillWell = drillWellRepository.findById(drillWellId)
                .orElseThrow(() -> new IllegalArgumentException("DrillWell not found for ID: " + drillWellId));

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Assume data is on the first sheet
        DailyReport report = new DailyReport();

        // Set the DrillWell to associate it with the report
        report.setDrillingWell(drillWell);

        // Example for retrieving other fields, skipping this part for brevity

        // WorkDone: Stop on null or blank string
        List<String> workDoneList = new ArrayList<>();
        for (int i = 21; i <= 42; i++) { // Adjust the row indexes to match your data
            Cell workDoneCell = sheet.getRow(i).getCell(8);
            Cell startTimeCell = sheet.getRow(i).getCell(1);
            Cell endTimeCell = sheet.getRow(i).getCell(5);
            if (workDoneCell != null && workDoneCell.getCellType() == CellType.STRING) {
                String description = workDoneCell.getStringCellValue().trim();
                String startTime = "";
                String endTime = "";
                if (startTimeCell != null) {
                    if (startTimeCell.getCellType() == CellType.STRING) {
                        startTime = startTimeCell.getStringCellValue().trim();
                    } else if (startTimeCell.getCellType() == CellType.NUMERIC) {
                        startTime = startTimeCell.getLocalDateTimeCellValue().toLocalTime().toString(); // or use
                                                                                                        // formatter
                    }
                }
                if (endTimeCell != null) {
                    if (endTimeCell.getCellType() == CellType.STRING) {
                        endTime = endTimeCell.getStringCellValue().trim();
                    } else if (endTimeCell.getCellType() == CellType.NUMERIC) {
                        endTime = endTimeCell.getLocalDateTimeCellValue().toLocalTime().toString(); // or use formatter
                    }
                }
                String entry = (startTime + " - " + endTime + ": " + description).trim();
                workDoneList.add(entry);
            }
        }
        report.setWorkDone(workDoneList);

        // Comments: Stop on null or blank string
        List<String> commentsList = new ArrayList<>();
        for (int i = 45; i <= 56; i++) { // Adjust the row indexes to match your data
            Cell commentsCell = sheet.getRow(i).getCell(1);
            if (commentsCell != null && commentsCell.getCellType() == CellType.STRING) {
                commentsList.add(commentsCell.getStringCellValue().trim());
            }
        }
        report.setComments(commentsList);

        Cell bitSizeCell = sheet.getRow(9).getCell(12); // M10
        String phaseName = "Phase inconnue";
        Integer phaseId = 0;

        if (bitSizeCell != null) {
            String cellContent = "";

            if (bitSizeCell.getCellType() == CellType.NUMERIC) {
                cellContent = String.valueOf((int) bitSizeCell.getNumericCellValue());
            } else if (bitSizeCell.getCellType() == CellType.STRING) {
                cellContent = bitSizeCell.getStringCellValue().trim();
            }

            // Normalize: remove special characters (like " or ')
            cellContent = cellContent.replaceAll("[^0-9]", "");

            // Check for digits and assign phase
            if (cellContent.contains("26") || cellContent.contains("20")) {
                phaseName = "26\" x 20\" inches";
                phaseId = 1;
            } else if (cellContent.contains("16") || cellContent.contains("13")) {
                phaseName = "16\" x 13\" inches";
                phaseId = 2;
            } else if (cellContent.contains("12") || cellContent.contains("9")) {
                phaseName = "12\" x 9\" inches";
                phaseId = 3;
            } else if (cellContent.contains("8") || cellContent.contains("7") || cellContent.contains("6")) {
                phaseName = "8\" x 7\" inches";
                phaseId = 4;
            }
        }

        report.setPhaseName(phaseName);
        report.setPhaseId(phaseId);

        // Set other fields as needed (example: totalCost, totalDuration, etc.)
        // Example:
        report.setReportDate(LocalDate.now()); // Example of setting report date
        // report.setTotalCost(BigDecimal.valueOf(sheetCost.getRow(85).getCell(6).getNumericCellValue()));
        // // Example of
        // setting total
        // cost
        report.setTotalDuration(8); // Example of setting total duration

        /* */
        Row row = sheet.getRow(5); // Row 6
        String depth = "";

        if (row != null) {
            // Extract depth from U6 (column 20)

            Cell depthCell = row.getCell(20);
            if (depthCell != null) {
                if (depthCell.getCellType() == CellType.NUMERIC) {
                    report.setDepth(String.valueOf((int) depthCell.getNumericCellValue()));
                } else if (depthCell.getCellType() == CellType.STRING) {
                    String text = depthCell.getStringCellValue().replaceAll("[^\\d]", "");
                    depth = text;
                    if (!text.isEmpty())
                        report.setDepth((text));
                }
            }

            // Extract progress from AW6 (column 48)
            Cell progressValueCell = row.getCell(48);
            Cell timeSpentCell = row.getCell(57);// BF6
            String progressStr = "";
            if (progressValueCell != null && timeSpentCell != null) {
                String progressFeet = "";
                String timeHours = "";
                // Extract progress in ft (AW6)
                if (progressValueCell.getCellType() == CellType.NUMERIC) {
                    progressFeet = String.valueOf((int) progressValueCell.getNumericCellValue());
                } else if (progressValueCell.getCellType() == CellType.STRING) {
                    String digits = progressValueCell.getStringCellValue().replaceAll("[^\\d]", "");
                    if (!digits.isEmpty())
                        progressFeet = digits;
                }

                // Extract time spent in h (BF6)
                if (timeSpentCell.getCellType() == CellType.NUMERIC) {
                    timeHours = String.valueOf((int) timeSpentCell.getNumericCellValue());
                } else if (timeSpentCell.getCellType() == CellType.STRING) {
                    String digits = timeSpentCell.getStringCellValue().replaceAll("[^\\d]", "");
                    if (!digits.isEmpty())
                        timeHours = digits;
                }

                if (!progressFeet.isEmpty() && !timeHours.isEmpty()) {
                    progressStr = progressFeet + " ft in " + timeHours + "h";
                    report.setProgress(progressStr); // ✅ assuming report has a String field "progress"
                }

            }

        }

        // CJ58: row 57, column 86 (daily cost)
        Row row58 = sheet.getRow(57); // row index for Excel row 58
        Cell dailyCostCell = row58 != null ? row58.getCell(87) : null;

        // CJ59: row 58, column 86 (cumulative cost)
        Row row59 = sheet.getRow(58); // row index for Excel row 59
        Cell cumulativeCostCell = row59 != null ? row59.getCell(87) : null;

        BigDecimal dailyCost = BigDecimal.ZERO;
        BigDecimal cumulativeCost = BigDecimal.ZERO;

        if (cumulativeCostCell != null) {
            if (cumulativeCostCell.getCellType() == CellType.NUMERIC) {
                cumulativeCost = BigDecimal.valueOf(cumulativeCostCell.getNumericCellValue());
            } else if (cumulativeCostCell.getCellType() == CellType.STRING) {
                String raw = cumulativeCostCell.getStringCellValue().trim();
                raw = raw.replace(" ", "").replace(",", "."); // Normalize
                try {
                    cumulativeCost = new BigDecimal(raw);
                } catch (NumberFormatException e) {
                    System.err.println("Could not parse cumulative cost: " + raw);
                }
            }
        }

        if (dailyCostCell != null) {
            if (dailyCostCell.getCellType() == CellType.NUMERIC) {
                dailyCost = BigDecimal.valueOf(dailyCostCell.getNumericCellValue());
            } else if (dailyCostCell.getCellType() == CellType.STRING) {
                String raw = dailyCostCell.getStringCellValue().trim();
                raw = raw.replace(" ", "").replace(",", "."); // Normalize to dot format
                try {
                    dailyCost = new BigDecimal(raw);
                } catch (NumberFormatException e) {
                    System.err.println("Could not parse daily cost: " + raw);
                }
            }
        }

        Row row61 = sheet.getRow(60); // row index for Excel row 58
        Cell plannedDayCell = row61.getCell(68);

        Row row62 = sheet.getRow(61); // row index for Excel row 59
        Cell actualDayCell = row62.getCell(68);

        BigDecimal actualday = BigDecimal.ZERO;
        BigDecimal plannedday = BigDecimal.ZERO;

        if (plannedDayCell != null) {
            if (plannedDayCell.getCellType() == CellType.NUMERIC) {
                plannedday = BigDecimal.valueOf(plannedDayCell.getNumericCellValue());
            } else if (plannedDayCell.getCellType() == CellType.STRING) {
                String raw = plannedDayCell.getStringCellValue().trim();
                raw = raw.replace(" ", "").replace(",", "."); // Normalize
                try {
                    plannedday = new BigDecimal(raw);
                } catch (NumberFormatException e) {
                    System.err.println("Could not parse cumulative cost: " + raw);
                }
            }
        }

        if (actualDayCell != null) {
            if (actualDayCell.getCellType() == CellType.NUMERIC) {
                actualday = BigDecimal.valueOf(actualDayCell.getNumericCellValue());
            } else if (actualDayCell.getCellType() == CellType.STRING) {
                String raw = actualDayCell.getStringCellValue().trim();
                raw = raw.replace(" ", "").replace(",", "."); // Normalize to dot format
                try {
                    actualday = new BigDecimal(raw);
                } catch (NumberFormatException e) {
                    System.err.println("Could not parse daily cost: " + raw);
                }
            }
        }

        // Assign to report
        if (cumulativeCost.compareTo(drillWell.getTotalCost()) > 0) {
            drillWell.setTotalCost(cumulativeCost);
        }
        report.setDailyCost(dailyCost); // Assume report has a String field for dailyCost
        report.setActualDay(actualday);
        report.setPlannedDay(plannedday);

        // Add the report to the list
        reports.add(report);
        double depthVal = Double.parseDouble(depth);
        double cumDepthVal = Double.parseDouble(drillWell.getPlannedDepth4());

        double ratio = depthVal / cumDepthVal;
        int progress = (int) Math.floor(Math.min(ratio, 1.0) * 100);

        drillWell.setProgress(progress);

        if (depth != "") {
            drillWell.setDepth(depth);
        }

        drillWell.setActualDay(actualday);
        switch (phaseId) {
            case 0:
                break;
            case 1:
                drillWell.setCumulativeCost1(cumulativeCost);
                drillWell.setActualDay1(actualday);
                drillWell.setPhaseId(phaseId);
                if (depth != "") {
                    drillWell.setDepth1(depth);
                }
                if (cumulativeCost.compareTo(drillWell.getPlannedCost1()) > 0) {
                    drillWell.setStatus("At Risk");
                }
                if (actualday.compareTo(drillWell.getPlannedDay1()) > 0) {
                    drillWell.setStatus("Delayed");
                }

                break;
            case 2:
                drillWell.setCumulativeCost2(cumulativeCost);
                drillWell.setActualDay2(actualday);
                drillWell.setPhaseId(phaseId);

                if (depth != "") {
                    drillWell.setDepth2(depth);
                }
                if (cumulativeCost.compareTo(drillWell.getPlannedCost2()) > 0) {
                    drillWell.setStatus("At risk");
                }
                if (actualday.compareTo(drillWell.getPlannedDay2()) > 0) {
                    drillWell.setStatus("Delayed");
                }
                break;
            case 3:
                drillWell.setCumulativeCost3(cumulativeCost);
                drillWell.setActualDay3(actualday);
                drillWell.setPhaseId(phaseId);

                if (depth != "") {
                    drillWell.setDepth3(depth);
                }
                if (cumulativeCost.compareTo(drillWell.getPlannedCost3()) > 0) {
                    drillWell.setStatus("At risk");
                }
                if (actualday.compareTo(drillWell.getPlannedDay3()) > 0) {
                    drillWell.setStatus("Delayed");
                }
                break;
            case 4:
                drillWell.setCumulativeCost4(cumulativeCost);
                drillWell.setActualDay4(actualday);
                drillWell.setPhaseId(phaseId);
                if (depth != "") {
                    drillWell.setDepth4(depth);
                }
                if (cumulativeCost.compareTo(drillWell.getPlannedCost4()) > 0) {
                    drillWell.setStatus("At risk");
                }
                if (actualday.compareTo(drillWell.getPlannedDay4()) > 0) {
                    drillWell.setStatus("Delayed");
                }
                break;
        }

        if (progress >= 100) {
            drillWell.setStatus("Completed");
        }
        drillWellRepository.save(drillWell);

        // Save all reports to the database
        return dailyReportRepository.saveAll(reports);
    }

    public List<DailyReport> getReportsByDrillWellId(int drillWellId) {
        return dailyReportRepository.findByDrillingWellId(drillWellId);
    }

    public Optional<DailyReport> getReportByDrillWellIdAndReportId(int drillWellId, int reportId) {
        return dailyReportRepository.findByIdAndDrillingWellId(reportId, drillWellId);
    }

    public void deleteAllReportsByDrillWellId(int drillWellId) {
        dailyReportRepository.deleteAllByDrillingWell_Id(drillWellId);
    }
}
