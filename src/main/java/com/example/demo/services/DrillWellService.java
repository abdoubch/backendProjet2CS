package com.example.demo.services;

import com.example.demo.model.DrillWell;
import com.example.demo.repository.DrillWellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrillWellService {

    @Autowired
    private DrillWellRepository drillWellRepository;

    public List<DrillWell> getAllDrillWells() {
        return drillWellRepository.findAll();
    }

    public Optional<DrillWell> getDrillWellById(int id) {
        return drillWellRepository.findById(id);
    }

    public DrillWell saveDrillWell(DrillWell drillWell) {
        return drillWellRepository.save(drillWell);
    }

    public void deleteDrillWell(int id) {
        drillWellRepository.deleteById(id);
    }

    public DrillWell updateDrillWell(int id, DrillWell newDrillWell) {
        return drillWellRepository.findById(id)
                .map(existingWell -> {
                    existingWell.setLocation(newDrillWell.getLocation());
                    existingWell.setStartDate(newDrillWell.getStartDate());
                    existingWell.setPlannedEndDate(newDrillWell.getPlannedEndDate());
                    existingWell.setActualEndDate(newDrillWell.getActualEndDate());
                    existingWell.setStatus(newDrillWell.getStatus());
                    existingWell.setTotalCost(newDrillWell.getTotalCost());
                    existingWell.setType(newDrillWell.getType());
                    existingWell.setStages(newDrillWell.getStages());
                    return drillWellRepository.save(existingWell);
                })
                .orElseGet(() -> {
                    newDrillWell.setId(id);
                    return drillWellRepository.save(newDrillWell);
                });
    }
}


