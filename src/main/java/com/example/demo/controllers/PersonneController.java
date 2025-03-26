package com.example.demo.controllers;

import com.example.demo.model.Personne;
<<<<<<< HEAD
import com.example.demo.services.PersonneService;
=======
>>>>>>> 978e2af (planning controller)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    @Autowired
    private com.example.demo.services.PersonneService personneService;

    @PostMapping("/ajouter")
    public Personne ajouterPersonne(@RequestParam String nom, @RequestParam String prenom) {
        return personneService.ajouterPersonne(nom, prenom);
    }
}
