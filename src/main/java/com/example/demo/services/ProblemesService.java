package com.example.demo.services;

import com.example.demo.model.Problemes;
import com.example.demo.repository.ProblemesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemesService {

    @Autowired
    private ProblemesRepository problemesRepository;

    public List<Problemes> getAllProblemes() {
        return problemesRepository.findAll();
    }

    public Optional<Problemes> getProblemeById(int id) {
        return problemesRepository.findById(id);
    }

    public Problemes addProbleme(Problemes probleme) {
        return problemesRepository.save(probleme);
    }

    public Problemes updateProbleme(int id, Problemes updatedProbleme) {
        return problemesRepository.findById(id).map(probleme -> {
            probleme.setName(updatedProbleme.getName());
            probleme.setType(updatedProbleme.getType());
            probleme.setDailyReport(updatedProbleme.getDailyReport());
            probleme.setSolutions(updatedProbleme.getSolutions());
            return problemesRepository.save(probleme);
        }).orElseThrow(() -> new RuntimeException("Probleme not found"));
    }

    public void deleteProbleme(int id) {
        problemesRepository.deleteById(id);
    }
}
