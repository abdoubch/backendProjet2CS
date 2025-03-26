package com.example.demo.services;

import com.example.demo.model.Planning;
import com.example.demo.repository.PlanningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

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

    public boolean deletePlanning(int id) {
        if (planningRepository.existsById(id)) {
            planningRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
