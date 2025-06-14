package com.example.demo.controllers;


import com.example.demo.dto.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173/**")
public class UserController {

    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserServiceImpl userService;

    public UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> enregistrer(@RequestBody User user) {
        try {
            User savedUser = userService.enregistrerUtilisateur(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<User> listerUtilisateurs() {
        return userService.tousLesUtilisateurs();
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        return userService.trouverParEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id) {
        userService.supprimerUtilisateur(id);
        return ResponseEntity.ok("Utilisateur supprimé");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getEmail(), loginRequest.getMotDePasse())
                .map(user -> ResponseEntity.ok("Connexion réussie pour " + user.getPrenom()))
                .orElse(ResponseEntity.status(401).body("Email ou mot de passe incorrect"));
    }

}

