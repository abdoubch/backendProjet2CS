package com.example.demo.controllers;

import com.example.demo.model.Planning;
import com.example.demo.services.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plannings")
public class PlanningController {

    @Autowired
    private PlanningService planningService;

    @GetMapping
    public List<Planning> getAllPlannings() {
        return planningService.getAllPlannings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planning> getPlanningById(@PathVariable int id) {
        Optional<Planning> planning = planningService.getPlanningById(id);
        return planning.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Planning> createPlanning(@RequestBody Planning planning) {
        Planning newPlanning = planningService.createPlanning(planning);
        return ResponseEntity.ok(newPlanning);
    }

    // Endpoint to import plannings from Excel file

    @PostMapping("/import")
    public ResponseEntity<?> importPlannings(@RequestParam("file") MultipartFile file,
            @RequestParam("drillWellId") int drillWellId) {
        try {
            List<Planning> plannings = planningService.importFromExcel(file, drillWellId);
            return new ResponseEntity<>(plannings, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to import planning: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Planning> updatePlanning(@PathVariable int id, @RequestBody Planning planningDetails) {
        Planning updatedPlanning = planningService.updatePlanning(id, planningDetails);
        return updatedPlanning != null ? ResponseEntity.ok(updatedPlanning) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanning(@PathVariable int id) {
        return planningService.deletePlanning(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // get plannings by drill id
    @GetMapping("/well/{drillWellId}")
    public ResponseEntity<List<Planning>> getPlanningsByDrillWell(@PathVariable int drillWellId) {
        List<Planning> plannings = planningService.getPlanningsByDrillWellId(drillWellId);
        return ResponseEntity.ok(plannings);
    }
}