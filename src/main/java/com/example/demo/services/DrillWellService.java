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
                    existingWell.setActualDay(newDrillWell.getActualDay());
                    existingWell.setActualDay1(newDrillWell.getActualDay1());
                    existingWell.setActualDay2(newDrillWell.getActualDay2());
                    existingWell.setActualDay3(newDrillWell.getActualDay3());
                    existingWell.setActualDay4(newDrillWell.getActualDay4());
                    existingWell.setTotalCost(newDrillWell.getTotalCost());
                    existingWell.setCumulativeCost1(newDrillWell.getCumulativeCost1());
                    existingWell.setCumulativeCost2(newDrillWell.getCumulativeCost2());
                    existingWell.setCumulativeCost3(newDrillWell.getCumulativeCost3());
                    existingWell.setCumulativeCost4(newDrillWell.getCumulativeCost4());
                    existingWell.setDepth(newDrillWell.getDepth());
                    existingWell.setDepth1(newDrillWell.getDepth1());
                    existingWell.setDepth2(newDrillWell.getDepth2());
                    existingWell.setDepth3(newDrillWell.getDepth3());
                    existingWell.setDepth4(newDrillWell.getDepth4());
                    existingWell.setPhaseId(newDrillWell.getPhaseId());
                    return drillWellRepository.save(existingWell);
                })
                .orElseGet(() -> {
                    newDrillWell.setId(id);
                    return drillWellRepository.save(newDrillWell);
                });
    }
}
