package com.suivilbcft.suivibackend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suivilbcft.suivibackend.dto.request.InstitutionalInfoRequest;
import com.suivilbcft.suivibackend.dto.response.InstitutionalInfoResponse;
import com.suivilbcft.suivibackend.model.Institution;
import com.suivilbcft.suivibackend.repository.InstitutionRepository;
import com.suivilbcft.suivibackend.service.InstitutionalInfoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/institution")
public class InstitutionalInfoController {

    @Autowired
    private InstitutionalInfoService institutionalInfoService;
    
    @Autowired
    private InstitutionRepository institutionRepository;

    /**
     * ✅ NOUVEAU ENDPOINT : Récupère l'institution de l'utilisateur connecté
     * Cet endpoint résout l'erreur 403 en permettant aux utilisateurs "institution"
     * de récupérer leur propre institution via leur email
     */
    @GetMapping("/by-user-email")
    public ResponseEntity<?> getInstitutionByUserEmail() {
        try {
            // Récupérer l'email de l'utilisateur authentifié
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                System.out.println("❌ Aucun utilisateur authentifié");
                return ResponseEntity.status(401).body("Non authentifié");
            }

            String userEmail = authentication.getName();
            System.out.println("🔍 Recherche institution pour email: " + userEmail);

            // Chercher l'institution associée à cet email via la relation avec Utilisateur
            Optional<Institution> institutionOpt = institutionRepository.findByUtilisateur_Email(userEmail);
            
            if (!institutionOpt.isPresent()) {
                System.out.println("❌ Aucune institution trouvée pour: " + userEmail);
                return ResponseEntity.status(404)
                    .body("Aucune institution associée à l'utilisateur: " + userEmail);
            }

            Institution institution = institutionOpt.get();
            System.out.println("✅ Institution trouvée: " + institution.getDenominationSociale() 
                + " (ID: " + institution.getIdInstitution() + ")");

            return ResponseEntity.ok(institution);

        } catch (Exception e) {
            System.err.println("❌ Erreur serveur: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur serveur: " + e.getMessage());
        }
    }

    @GetMapping("/completion-status")
    public ResponseEntity<InstitutionalInfoResponse> getCompletionStatus() {
        InstitutionalInfoResponse response = institutionalInfoService.getCompletionStatus();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-info")
    public ResponseEntity<InstitutionalInfoResponse> updateInstitutionalInfo(
            @Valid @RequestBody InstitutionalInfoRequest request) {
        InstitutionalInfoResponse response = institutionalInfoService.updateInstitutionalInfo(request);
        return ResponseEntity.ok(response);
    }
}