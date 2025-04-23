package com.example.demo.services;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Ajout de PasswordEncoder

    @Override
    public User enregistrerUtilisateur(User user) {
        // Vérification de l'existence de l'email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        // Hachage du mot de passe
        String motDePasseHache = passwordEncoder.encode(user.getMotDePasse());
        user.setMotDePasse(motDePasseHache);  // Stocker le mot de passe haché
        roleRepository.findAll().forEach(role -> System.out.println(role.getNom()));
        // Attribution d'un rôle par défaut
        Role roleUser = roleRepository.findByNom("ROLE_USER".trim().toUpperCase())
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
        user.getRoles().add(roleUser);

        // Sauvegarde de l'utilisateur avec le mot de passe haché
        return userRepository.save(user);
    }

    @Override
    public Optional<User> trouverParEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> tousLesUtilisateurs() {
        return userRepository.findAll();
    }

    @Override
    public void supprimerUtilisateur(Long id) {
        userRepository.deleteById(id);
    }
}
