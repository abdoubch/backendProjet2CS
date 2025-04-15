package com.example.demo.services;

import com.example.demo.model.DrillWell;
import com.example.demo.model.Planning;
import com.example.demo.repository.PlanningRepository;
import com.example.demo.repository.DrillWellRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanningService {

    @Autowired
    private PlanningRepository planningRepository;
    @Autowired
    private DrillWellRepository drillWellRepository;

    public List<Planning> getAllPlannings() {
        return planningRepository.findAll();
    }

    public Optional<Planning> getPlanningById(int id) {
        return planningRepository.findById(id);
    }

    public Planning createPlanning(Planning planning) {
        return planningRepository.save(planning);
    }

    public Planning updatePlanning(int id, Planning planningDetails) {
        return planningRepository.findById(id).map(planning -> {
            planning.setName(planningDetails.getName());
            planning.setPlannedStartDate(planningDetails.getPlannedStartDate());
            planning.setPlannedEndDate(planningDetails.getPlannedEndDate());
            planning.setActualStartDate(planningDetails.getActualStartDate());
            planning.setActualEndDate(planningDetails.getActualEndDate());
            planning.setPlannedCost(planningDetails.getPlannedCost());
            planning.setActualCost(planningDetails.getActualCost());
            planning.setDescription(planningDetails.getDescription());
            return planningRepository.save(planning);
        }).orElse(null);
    }

    public List<Planning> importFromExcel(MultipartFile file, int drillWellId) throws IOException {
        List<Planning> plannings = new ArrayList<>();

        DrillWell drillWell = drillWellRepository.findById(drillWellId)
                .orElseThrow(() -> new IllegalArgumentException("DrillWell not found for ID: " + drillWellId));

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Supposons que le planning est sur la première feuille

        // ⚠️ Remplace ces lignes par la logique de parsing propre à ton fichier
        // Planning.xlsx
        String planningName = "";

        // cell name O58 58-1=57 O --> 15-1=14
        Cell planningNameCell = sheet.getRow(57).getCell(14);
        if (planningNameCell != null && planningNameCell.getCellType() == CellType.STRING) {
            planningName = planningNameCell.getStringCellValue().trim();
        }

        Planning planning = new Planning();

        planning.setDrillingWell(drillWell); // Associer au forage

        // Exemple d'extraction de données (ajuste selon ton Excel)

        planning.setName(planningName);
        planning.setPlannedStartDate(LocalDate.now());
        planning.setPlannedEndDate(LocalDate.now());
        planning.setActualStartDate(LocalDate.now());
        planning.setActualEndDate(LocalDate.now());

        planning.setPlannedCost(BigDecimal.valueOf(12345.67));
        planning.setActualCost(BigDecimal.valueOf(12345.67));

        planning.setDescription("testrrrrr melyarrr");
        // Ajouter à la liste
        plannings.add(planning);

        return planningRepository.saveAll(plannings);
    }

    public boolean deletePlanning(int id) {
        if (planningRepository.existsById(id)) {
            planningRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Planning> getPlanningsByDrillWellId(int drillWellId) {
        return planningRepository.findByDrillingWellId(drillWellId);
    }

}