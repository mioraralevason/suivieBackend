package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.dto.response.InstitutionalInfoResponse;
import com.suivilbcft.suivibackend.model.Institution;
import com.suivilbcft.suivibackend.model.Utilisateur;
import com.suivilbcft.suivibackend.repository.InstitutionRepository;
import com.suivilbcft.suivibackend.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class InstitutionalWorkflowService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * Check the current status of an institution's workflow
     */
    public InstitutionalInfoResponse checkInstitutionStatus(String institutionId) {
        if (institutionId == null) {
            return new InstitutionalInfoResponse(true, false, false, null, null,
                "ID de l'institution manquant");
        }

        Optional<Institution> institutionOpt = institutionRepository.findById(institutionId);

        if (!institutionOpt.isPresent()) {
            return new InstitutionalInfoResponse(true, false, false, null, null,
                "Aucune institution trouvée");
        }

        Institution institution = institutionOpt.get();
        boolean needsCompletion = institution.getCompletedInfoAt() == null;
        boolean isCompleted = institution.getCompletedInfoAt() != null;
        boolean isValidated = institution.getValidatedAt() != null;

        String message;
        if (needsCompletion) {
            message = "Compléter les informations de base (I2-I13)";
        } else if (isValidated) {
            message = "Processus complet - institution validée";
        } else {
            message = "Informations de base complétées - en attente de validation";
        }

        return new InstitutionalInfoResponse(needsCompletion, isCompleted, isValidated,
            institution.getCompletedInfoAt(), institution.getValidatedAt(), message);
    }

    /**
     * Check the current status for the authenticated user
     */
    public InstitutionalInfoResponse checkCurrentUserStatus() {
        try {
            Utilisateur utilisateur = getCurrentUser();
            if (utilisateur == null) {
                System.out.println("DEBUG [WorkflowService]: Aucun utilisateur authentifié trouvé dans checkCurrentUserStatus");
                return new InstitutionalInfoResponse(true, false, false, null, null,
                    "Utilisateur non authentifié");
            }

            System.out.println("DEBUG [WorkflowService]: Utilisateur trouvé dans checkCurrentUserStatus: " + utilisateur.getEmail());
            return checkInstitutionStatus(utilisateur.getIdUtilisateur());
        } catch (Exception e) {
            System.err.println("DEBUG [WorkflowService]: Erreur dans checkCurrentUserStatus: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Mark an institution as validated by a supervisor/admin
     */
    public void validateInstitution(String institutionId, String validatorId) {
        Optional<Institution> institutionOpt = institutionRepository.findById(institutionId);
        if (!institutionOpt.isPresent()) {
            throw new RuntimeException("Institution non trouvée: " + institutionId);
        }

        Institution institution = institutionOpt.get();
        
        // Only validate if basic info is already completed
        if (institution.getCompletedInfoAt() == null) {
            throw new RuntimeException("Les informations de base doivent être complétées avant validation");
        }

        institution.setValidatedAt(LocalDateTime.now());
        institutionRepository.save(institution);
    }

    /**
     * Check if user can access normal functionalities based on workflow stage
     */
    public boolean canAccessNormalFeatures(String institutionId) {
        Optional<Institution> institutionOpt = institutionRepository.findById(institutionId);
        if (!institutionOpt.isPresent()) {
            return false;
        }

        Institution institution = institutionOpt.get();
        
        // User can access normal features if basic info is completed
        // (validation is not required for basic access, only for final approval)
        return institution.getCompletedInfoAt() != null;
    }

    /**
     * Check if user is restricted to basic info completion only
     */
    public boolean restrictedToBasicInfo(String institutionId) {
        Optional<Institution> institutionOpt = institutionRepository.findById(institutionId);
        if (!institutionOpt.isPresent()) {
            return true; // If no institution, user should not have access
        }

        Institution institution = institutionOpt.get();
        return institution.getCompletedInfoAt() == null;
    }

    private Utilisateur getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG [WorkflowService]: Authentication object: " + authentication);
        if (authentication != null) {
            System.out.println("DEBUG [WorkflowService]: Authentication name: " + authentication.getName());
            System.out.println("DEBUG [WorkflowService]: Authentication principal: " + authentication.getPrincipal());
            System.out.println("DEBUG [WorkflowService]: Authentication principal class: " + (authentication.getPrincipal() != null ? authentication.getPrincipal().getClass().getName() : "null"));
            System.out.println("DEBUG [WorkflowService]: Authentication authenticated: " + authentication.isAuthenticated());
        }

        if (authentication != null && authentication.getPrincipal() != null) {
            // Handle both String and other principal types (like UserDetails)
            String email;
            if (authentication.getPrincipal() instanceof String) {
                email = (String) authentication.getPrincipal();
                System.out.println("DEBUG [WorkflowService]: Using principal as email: " + email);
            } else {
                // Assuming it's a UserDetails object from Spring Security
                email = authentication.getName(); // This gets the username/email
                System.out.println("DEBUG [WorkflowService]: Using authentication.getName() as email: " + email);
            }

            Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);
            System.out.println("DEBUG [WorkflowService]: Utilisateur trouvé dans la base: " + utilisateurOpt.isPresent());
            return utilisateurOpt.orElse(null);
        }
        System.out.println("DEBUG [WorkflowService]: Authentication null ou principal null");
        return null;
    }
}