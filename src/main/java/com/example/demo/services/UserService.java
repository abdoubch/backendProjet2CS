package com.example.demo.services;

import com.example.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User enregistrerUtilisateur(User user);
    Optional<User> trouverParEmail(String email);
    List<User> tousLesUtilisateurs();
    void supprimerUtilisateur(Long id);
}

