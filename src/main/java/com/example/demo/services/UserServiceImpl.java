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
    private PasswordEncoder passwordEncoder;

    @Override
    public User enregistrerUtilisateur(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        String motDePasseHache = passwordEncoder.encode(user.getMotDePasse());
        user.setMotDePasse(motDePasseHache);

//        Role roleUser = roleRepository.findByNom("ROLE_USER".trim().toUpperCase())
//                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
//        user.getRoles().add(roleUser);

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

    // 🔐 Méthode login simple
    public Optional<User> login(String email, String motDePasse) {
        Optional<User> utilisateur = userRepository.findByEmail(email);
        if (utilisateur.isPresent() && passwordEncoder.matches(motDePasse, utilisateur.get().getMotDePasse())) {
            return utilisateur;
        }
        return Optional.empty();
    }
}