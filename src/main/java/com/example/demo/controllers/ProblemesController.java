package com.example.demo.controllers;

import com.example.demo.model.Problemes;
import com.example.demo.services.ProblemesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/problemes")
public class ProblemesController {

    @Autowired
    private ProblemesService problemesService;

    @GetMapping
    public List<Problemes> getAllProblemes() {
        return problemesService.getAllProblemes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Problemes> getProblemeById(@PathVariable int id) {
        Optional<Problemes> probleme = problemesService.getProblemeById(id);
        return probleme.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Problemes createProbleme(@RequestBody Problemes probleme) {
        return problemesService.addProbleme(probleme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Problemes> updateProbleme(@PathVariable int id, @RequestBody Problemes updatedProbleme) {
        try {
            return ResponseEntity.ok(problemesService.updateProbleme(id, updatedProbleme));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProbleme(@PathVariable int id) {
        problemesService.deleteProbleme(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rapport/{dailyReportId}")
    public List<Problemes> getProblemesByDailyReportId(@PathVariable int dailyReportId) {
        return problemesService.getProblemesByDailyReportId(dailyReportId);
    }

    @GetMapping("/drillwell/{id}")
    public List<Problemes> getProblemesByDrillWell(@PathVariable("id") int drillWellId) {
        return problemesService.getProblemesByDrillWellId(drillWellId);
    }
    @DeleteMapping("/rapport/{dailyReportId}")
    public ResponseEntity<Void> deleteProblemesByDailyReport(@PathVariable int dailyReportId) {
        problemesService.deleteByDailyReportId(dailyReportId);
        return ResponseEntity.noContent().build();
    }
}