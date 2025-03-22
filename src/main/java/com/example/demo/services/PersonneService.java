package com.example.demo.services;

import com.example.demo.model.Personne;
import com.example.demo.repository.PersonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonneService {
    @Autowired
    private PersonneRepository personneRepository;

    public Personne ajouterPersonne(String nom, String prenom) {
        Personne personne = new Personne(nom, prenom);
        return personneRepository.save(personne);
    }
}
