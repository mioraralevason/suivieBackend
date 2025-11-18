package com.suivilbcft.suivibackend.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suivilbcft.suivibackend.dto.request.InstitutionalInfoRequest;
import com.suivilbcft.suivibackend.dto.response.InstitutionalInfoResponse;
import com.suivilbcft.suivibackend.model.Institution;
import com.suivilbcft.suivibackend.repository.InstitutionRepository;
import com.suivilbcft.suivibackend.repository.UtilisateurRepository;

@Service
@Transactional
public class InstitutionalInfoService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public InstitutionalInfoResponse getCompletionStatus() {
        try {
            String email = getCurrentUserEmail();
            if (email == null) {
                System.out.println("DEBUG: Aucun utilisateur authentifié trouvé");
                return new InstitutionalInfoResponse(true, false, false, null, null,
                    "Utilisateur non authentifié");
            }

            System.out.println("DEBUG: Email de l'utilisateur authentifié: " + email);

            // ✅ CORRECTION : Utiliser findByUtilisateur_Email au lieu de findById
            Optional<Institution> institutionOpt = institutionRepository.findByUtilisateur_Email(email);
            
            if (institutionOpt.isPresent()) {
                Institution institution = institutionOpt.get();
                System.out.println("DEBUG: Institution trouvée: " + institution.getDenominationSociale());
                System.out.println("DEBUG: completed_info_at: " + institution.getCompletedInfoAt());
                
                boolean needsCompletion = institution.getCompletedInfoAt() == null;
                boolean isCompleted = institution.getCompletedInfoAt() != null;
                boolean isValidated = institution.getValidatedAt() != null;

                return new InstitutionalInfoResponse(needsCompletion, isCompleted, isValidated,
                    institution.getCompletedInfoAt(), institution.getValidatedAt(),
                    needsCompletion ? "Compléter les informations de base" : "Informations complétées");
            } else {
                System.out.println("DEBUG: Aucune institution trouvée pour l'email: " + email);
                return new InstitutionalInfoResponse(true, false, false, null, null,
                    "Aucune institution trouvée pour cet utilisateur");
            }
        } catch (Exception e) {
            System.err.println("DEBUG: Erreur dans getCompletionStatus: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public InstitutionalInfoResponse updateInstitutionalInfo(InstitutionalInfoRequest request) {
        try {
            System.out.println("DEBUG: Début de updateInstitutionalInfo");
            
            String email = getCurrentUserEmail();
            if (email == null) {
                System.out.println("DEBUG: Aucun utilisateur authentifié pour updateInstitutionalInfo");
                throw new RuntimeException("Utilisateur non authentifié");
            }

            System.out.println("DEBUG: Email de l'utilisateur pour update: " + email);

            // ✅ CORRECTION : Utiliser findByUtilisateur_Email au lieu de findById
            Optional<Institution> institutionOpt = institutionRepository.findByUtilisateur_Email(email);
            
            if (!institutionOpt.isPresent()) {
                System.out.println("DEBUG: Aucune institution trouvée pour l'email: " + email);
                throw new RuntimeException("Aucune institution trouvée pour cet utilisateur");
            }

            Institution institution = institutionOpt.get();
            System.out.println("DEBUG: Institution trouvée pour update: " + institution.getIdInstitution());

            // Validation des champs obligatoires
            validateMandatoryFields(request);

            // Mettre à jour les informations de base
            institution.setDenominationSociale(request.getDenominationSociale());
            institution.setNomCommercial(request.getNomCommercial());
            institution.setFormeJuridique(request.getFormeJuridique());
            institution.setDateDebutOperations(request.getDateDebutOperations());
            institution.setAdresseSiegeSocial(request.getAdresseSiegeSocial());
            institution.setAdresseActivitePrincipale(request.getAdresseActivitePrincipale());
            institution.setAdressesSecondaires(request.getAdressesSecondaires());
            institution.setNumeroTelephone(request.getNumeroTelephone());
            institution.setAdresseEmail(request.getAdresseEmail());
            institution.setListeActivites(request.getListeActivites());
            institution.setActivitePrincipale(request.getActivitePrincipale());
            institution.setActivitesSecondaires(request.getActivitesSecondaires());

            // ✅ TRÈS IMPORTANT : Mettre à jour completed_info_at
            institution.setCompletedInfoAt(LocalDateTime.now());
            institution.setModifieLe(LocalDateTime.now());
            
            System.out.println("DEBUG: Date de complétion mise à jour: " + institution.getCompletedInfoAt());

            institutionRepository.save(institution);
            System.out.println("DEBUG: Institution sauvegardée avec succès");

            return new InstitutionalInfoResponse(false, true, institution.getValidatedAt() != null,
                institution.getCompletedInfoAt(), institution.getValidatedAt(),
                "Informations institutionnelles mises à jour avec succès");
        } catch (Exception e) {
            System.err.println("DEBUG: Erreur dans updateInstitutionalInfo: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void validateMandatoryFields(InstitutionalInfoRequest request) {
        if (request.getDenominationSociale() == null || request.getDenominationSociale().trim().isEmpty()) {
            throw new RuntimeException("La dénomination sociale est obligatoire");
        }
        if (request.getDateDebutOperations() == null) {
            throw new RuntimeException("La date de début des opérations est obligatoire");
        }
        if (request.getAdresseSiegeSocial() == null || request.getAdresseSiegeSocial().trim().isEmpty()) {
            throw new RuntimeException("L'adresse du siège social est obligatoire");
        }
        if (request.getNumeroTelephone() == null || request.getNumeroTelephone().trim().isEmpty()) {
            throw new RuntimeException("Le numéro de téléphone est obligatoire");
        }
        if (request.getAdresseEmail() == null || request.getAdresseEmail().trim().isEmpty()) {
            throw new RuntimeException("L'adresse email est obligatoire");
        }
    }

    // ✅ Méthode simplifiée pour obtenir l'email de l'utilisateur authentifié
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Authentication: " + authentication);
        
        if (authentication != null && authentication.isAuthenticated()) {
            String email;
            if (authentication.getPrincipal() instanceof String) {
                email = (String) authentication.getPrincipal();
            } else {
                email = authentication.getName();
            }
            System.out.println("DEBUG: Email extrait: " + email);
            return email;
        }
        
        System.out.println("DEBUG: Aucune authentification valide");
        return null;
    }

    public Institution getInstitutionByEmail(String email) {
        System.out.println("DEBUG: Recherche institution pour email: " + email);
        
        Optional<Institution> institutionOpt = institutionRepository.findByUtilisateur_Email(email);
        
        if (!institutionOpt.isPresent()) {
            System.out.println("DEBUG: Aucune institution trouvée pour: " + email);
            throw new RuntimeException("Aucune institution associée à l'utilisateur: " + email);
        }
        
        Institution institution = institutionOpt.get();
        System.out.println("DEBUG: Institution trouvée - " + institution.getDenominationSociale());
        
        return institution;
    }
}