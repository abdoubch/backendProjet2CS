package com.example.demo.controllers;

import com.example.demo.services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/read-excel-cell")
    public Map<String, String> readExcelCell(@RequestParam String filePath,
                                             @RequestParam int row,
                                             @RequestParam int column) {
        Map<String, String> response = new HashMap<>();
        try {
            String cellValue = excelService.readCellValue(filePath, row, column);
            response.put("value", cellValue);  // Return the value in a JSON object
        } catch (IOException e) {
            response.put("error", "Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        return response;  // Spring will automatically convert this Map to JSON
    }
}


