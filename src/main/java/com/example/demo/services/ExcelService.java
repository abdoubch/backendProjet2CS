package com.example.demo.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ExcelService {

    // Méthode principale pour lire la cellule avec ligne et colonne comme entrée
    public String readCellValue(String filePath, int rowNumber, int columnNumber) throws IOException {
        FileInputStream file = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0); // Lire la première feuille du fichier Excel

        // Récupérer la valeur de la cellule (fusionnée ou non)
        String cellValue = getMergedCellValue(sheet, rowNumber, columnNumber);

        workbook.close();
        file.close();

        return cellValue;
    }

    // Méthode pour gérer les cellules fusionnées
    private String getMergedCellValue(Sheet sheet, int rowNumber, int columnNumber) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
            // Vérifier si la cellule demandée est dans une région fusionnée
            if (mergedRegion.isInRange(rowNumber, columnNumber)) {
                // Retourner la valeur de la première cellule de la région fusionnée
                Row row = sheet.getRow(mergedRegion.getFirstRow());
                Cell cell = row.getCell(mergedRegion.getFirstColumn());
                return getCellValue(cell);
            }
        }

        // Si la cellule n'est pas fusionnée, retourner la valeur normale
        Row row = sheet.getRow(rowNumber);
        Cell cell = row.getCell(columnNumber);
        return getCellValue(cell);
    }

    // Méthode utilitaire pour obtenir la valeur d'une cellule
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}

