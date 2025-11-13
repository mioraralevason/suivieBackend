package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.dto.AuthResponse;
import com.suivilbcft.suivibackend.dto.LoginRequest;
import com.suivilbcft.suivibackend.dto.RegisterRequest;
import com.suivilbcft.suivibackend.model.Institution;
import com.suivilbcft.suivibackend.model.Role;
import com.suivilbcft.suivibackend.model.Utilisateur;
import com.suivilbcft.suivibackend.repository.InstitutionRepository;
import com.suivilbcft.suivibackend.repository.RoleRepository;
import com.suivilbcft.suivibackend.repository.UtilisateurRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Optional;
import java.util.UUID;
import java.util.Arrays;

@Service
public class AuthService {
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final InstitutionRepository institutionRepository;
    private final PasswordEncoder passwordEncoder;
    private final Key key;
    private final long jwtExpiration;

    public AuthService(UtilisateurRepository utilisateurRepository, RoleRepository roleRepository, InstitutionRepository institutionRepository, PasswordEncoder passwordEncoder, @Value("${jwt.secret}") String jwtSecret, @Value("${jwt.expiration}") long jwtExpiration) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
        this.institutionRepository = institutionRepository;
        this.passwordEncoder = passwordEncoder;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtExpiration = jwtExpiration;
    }

    public AuthResponse login(LoginRequest request) {
        Optional<Utilisateur> userOpt = utilisateurRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent() && passwordEncoder.matches(request.getMotDePasse(), userOpt.get().getMotDePasse())) {
            Utilisateur user = userOpt.get();
            String token = generateToken(user.getEmail(), user.getRole().getLibelle());
            return new AuthResponse(token, user.getRole().getLibelle());
        }
        throw new RuntimeException("Email ou mot de passe invalide");
    }

    public AuthResponse register(RegisterRequest request) {
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
        utilisateurRepository.save(user);

        // Créer une institution uniquement pour le rôle institution
        if ("institution".equals(roleLibelle)) {
            String idInstitution = UUID.randomUUID().toString();
            Institution institution = new Institution(idInstitution, nom, adresse, null, user);
            institutionRepository.save(institution);
        }

        String token = generateToken(request.getEmail(), roleLibelle);
        return new AuthResponse(token, roleLibelle);
    }

   private String mapRoleToLibelle(String role) {
    switch (role) {
        case "institution":
            return "institution";
        case "superviseur":
            return "superviseur";
        case "admin":
            return "admin";
        default:
            throw new RuntimeException("Rôle invalide: " + role);
    }
}

    private String generateToken(String email, String role) {
    return Jwts.builder()
        .setSubject(email)
        .claim("role", role)  
        .setIssuedAt(new java.util.Date())
        .setExpiration(new java.util.Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
}
}