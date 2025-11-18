package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.dto.request.LoginRequest;
import com.suivilbcft.suivibackend.dto.request.RegisterRequest;
import com.suivilbcft.suivibackend.dto.response.AuthResponse;
import com.suivilbcft.suivibackend.model.Institution;
import com.suivilbcft.suivibackend.model.Role;
import com.suivilbcft.suivibackend.model.Utilisateur;
import com.suivilbcft.suivibackend.repository.InstitutionRepository;
import com.suivilbcft.suivibackend.repository.RoleRepository;
import com.suivilbcft.suivibackend.repository.UtilisateurRepository;
import com.suivilbcft.suivibackend.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/**
 * Service d'authentification principal
 * Gère l'inscription et la connexion des utilisateurs
 */
@Service
@Transactional
public class AuthServiceFixed {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final InstitutionRepository institutionRepository;

    public AuthServiceFixed(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
                       InstitutionRepository institutionRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.institutionRepository = institutionRepository;
    }

    public AuthResponse login(LoginRequest request) {
        // Utiliser une requête spécifique qui charge uniquement l'utilisateur sans jointure
        Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(request.getEmail());
        
        if (userOpt.isPresent()) {
            Utilisateur user = userOpt.get();
            if (passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
                String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getLibelle());
                return new AuthResponse(token, user.getRole().getLibelle());
            }
        }
        throw new RuntimeException("Email ou mot de passe invalide");
    }

    public AuthResponse register(RegisterRequest request) {
        // Vérifier si l'utilisateur existe déjà
        if (utilisateurRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        String roleLibelle = mapRoleToLibelle(request.getRole());
        Optional<Role> roleOpt = roleRepository.findByLibelle(roleLibelle);
        
        if (roleOpt.isEmpty()) {
            throw new RuntimeException("Rôle invalide");
        }

        if (request.getMotDePasse().length() < 6) {
            throw new RuntimeException("Le mot de passe doit contenir au moins 6 caractères");
        }

        String nom = request.getFullName();
        String prenom = null;
        String adresse = request.getAdresse();

        if ("superviseur".equals(roleLibelle) || "admin".equals(roleLibelle)) {
            String[] words = request.getFullName().trim().split("\\s+");
            if (words.length < 2) {
                throw new RuntimeException("Nom et prénom requis pour " + roleLibelle);
            }
            nom = words[words.length - 1];
            prenom = String.join(" ", Arrays.copyOfRange(words, 0, words.length - 1));
            adresse = null;
        } else {
            if (adresse == null || adresse.trim().isEmpty()) {
                throw new RuntimeException("Adresse requise pour institution");
            }
            prenom = null;
        }

        String encodedPassword = passwordEncoder.encode(request.getMotDePasse());
        String idUser = UUID.randomUUID().toString();
        Utilisateur user = new Utilisateur(idUser, request.getEmail(), encodedPassword, nom, prenom, roleOpt.get());
        
        // Sauvegarder utilisateur sans charger les relations
        Utilisateur savedUser = utilisateurRepository.saveAndFlush(user);

        // Créer une institution uniquement pour le rôle institution
        if ("institution".equals(roleLibelle)) {
            String idInstitution = UUID.randomUUID().toString();
            Institution institution = new Institution(idInstitution, nom, adresse, savedUser);
            institutionRepository.saveAndFlush(institution);
        }

        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().getLibelle());
        return new AuthResponse(token, roleLibelle);
    }

    private String mapRoleToLibelle(String role) {
        switch (role) {
            case "institution": return "institution";
            case "superviseur": return "superviseur";
            case "admin": return "admin";
            default: throw new RuntimeException("Rôle invalide: " + role);
        }
    }
}