package com.example.demo.controllers;

import com.example.demo.model.DrillWell;

import com.example.demo.services.DrillWellService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/drillwells")
public class DrillWellController {

    @Autowired
    private DrillWellService drillWellService;

    @GetMapping
    public List<DrillWell> getAllDrillWells() {
        return drillWellService.getAllDrillWells();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrillWell> getDrillWellById(@PathVariable int id) {
        Optional<DrillWell> drillWell = drillWellService.getDrillWellById(id);
        return drillWell.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DrillWell createDrillWell(@RequestBody DrillWell drillWell) {
        return drillWellService.saveDrillWell(drillWell);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrillWell> updateDrillWell(@PathVariable int id, @RequestBody DrillWell drillWell) {
        DrillWell updatedDrillWell = drillWellService.updateDrillWell(id, drillWell);
        return ResponseEntity.ok(updatedDrillWell);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrillWell(@PathVariable int id) {
        drillWellService.deleteDrillWell(id);
        return ResponseEntity.noContent().build();
    }
    
}
