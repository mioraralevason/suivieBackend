package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "institution")
public class Institution {
    @Id
    @Column(name = "id_institution", length = 250)
    private String idInstitution;

    // I2 - Dénomination sociale de l'institution
    @Column(name = "denomination_sociale", length = 255, nullable = false)
    private String denominationSociale;

    // I3 - Nom commercial ou enseigne
    @Column(name = "nom_commercial", length = 255)
    private String nomCommercial;

    // I4 - Forme juridique
    @Column(name = "forme_juridique", length = 100)
    private String formeJuridique;

    // I5 - Date de début des opérations
    @Column(name = "date_debut_operations")
    private LocalDate dateDebutOperations;

    // I6 - Adresse du siège social
    @Column(name = "adresse_siege_social", length = 500)
    private String adresseSiegeSocial;

    // I7 - Adresse de l'activité principale
    @Column(name = "adresse_activite_principale", length = 500)
    private String adresseActivitePrincipale;

    // I8 - Adresses secondaires / succursales
    @Column(name = "adresses_secondaires", length = 1000)
    private String adressesSecondaires;

    // I9 - Numéro téléphone de l'institution
    @Column(name = "numero_telephone", length = 20)
    private String numeroTelephone;

    // I10 - Adresse email de l'institution
    @Column(name = "adresse_email", length = 255)
    private String adresseEmail;

    // I11 - Liste des activités effectuées
    @Column(name = "liste_activites", columnDefinition = "TEXT")
    private String listeActivites;

    // I12 - Activité principale de l'institution
    @Column(name = "activite_principale", length = 255)
    private String activitePrincipale;

    // I13 - Activités secondaires
    @Column(name = "activites_secondaires", columnDefinition = "TEXT")
    private String activitesSecondaires;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    // Validation and completion timestamps
    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @Column(name = "completed_info_at")
    private LocalDateTime completedInfoAt;

    // Link to utilisateur (user)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false, referencedColumnName = "id_utilisateur")
    private Utilisateur utilisateur;

    // Relationships to questionnaire responses (normalized)
    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<InstitutionEpnfdResponse> epnfdResponses = new HashSet<>();

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<InstitutionActivityResponse> activityResponses = new HashSet<>();

    // Constructors
    public Institution() {}

    public Institution(String idInstitution, String denominationSociale, String nomCommercial,
                       String formeJuridique, LocalDate dateDebutOperations, String adresseSiegeSocial,
                       String adresseActivitePrincipale, String adressesSecondaires, String numeroTelephone,
                       String adresseEmail, String listeActivites, String activitePrincipale,
                       String activitesSecondaires, Utilisateur utilisateur) {
        this.idInstitution = idInstitution;
        this.denominationSociale = denominationSociale;
        this.nomCommercial = nomCommercial;
        this.formeJuridique = formeJuridique;
        this.dateDebutOperations = dateDebutOperations;
        this.adresseSiegeSocial = adresseSiegeSocial;
        this.adresseActivitePrincipale = adresseActivitePrincipale;
        this.adressesSecondaires = adressesSecondaires;
        this.numeroTelephone = numeroTelephone;
        this.adresseEmail = adresseEmail;
        this.listeActivites = listeActivites;
        this.activitePrincipale = activitePrincipale;
        this.activitesSecondaires = activitesSecondaires;
        this.utilisateur = utilisateur;
    }

    // Simplified constructor for basic creation (for use by registration)
    public Institution(String idInstitution, String denominationSociale, String adresse, Utilisateur utilisateur) {
        this.idInstitution = idInstitution;
        this.denominationSociale = denominationSociale;
        this.adresseSiegeSocial = adresse;
        this.utilisateur = utilisateur;
    }

    // Constructor with validation and completion timestamps
    public Institution(String idInstitution, String denominationSociale, String nomCommercial,
                       String formeJuridique, LocalDate dateDebutOperations, String adresseSiegeSocial,
                       String adresseActivitePrincipale, String adressesSecondaires, String numeroTelephone,
                       String adresseEmail, String listeActivites, String activitePrincipale,
                       String activitesSecondaires, LocalDateTime validatedAt, LocalDateTime completedInfoAt,
                       Utilisateur utilisateur) {
        this.idInstitution = idInstitution;
        this.denominationSociale = denominationSociale;
        this.nomCommercial = nomCommercial;
        this.formeJuridique = formeJuridique;
        this.dateDebutOperations = dateDebutOperations;
        this.adresseSiegeSocial = adresseSiegeSocial;
        this.adresseActivitePrincipale = adresseActivitePrincipale;
        this.adressesSecondaires = adressesSecondaires;
        this.numeroTelephone = numeroTelephone;
        this.adresseEmail = adresseEmail;
        this.listeActivites = listeActivites;
        this.activitePrincipale = activitePrincipale;
        this.activitesSecondaires = activitesSecondaires;
        this.validatedAt = validatedAt;
        this.completedInfoAt = completedInfoAt;
        this.utilisateur = utilisateur;
    }

    // Getters and setters for all fields
    public String getIdInstitution() { return idInstitution; }
    public void setIdInstitution(String idInstitution) { this.idInstitution = idInstitution; }

    public String getDenominationSociale() { return denominationSociale; }
    public void setDenominationSociale(String denominationSociale) { this.denominationSociale = denominationSociale; }

    public String getNomCommercial() { return nomCommercial; }
    public void setNomCommercial(String nomCommercial) { this.nomCommercial = nomCommercial; }

    public String getFormeJuridique() { return formeJuridique; }
    public void setFormeJuridique(String formeJuridique) { this.formeJuridique = formeJuridique; }

    public LocalDate getDateDebutOperations() { return dateDebutOperations; }
    public void setDateDebutOperations(LocalDate dateDebutOperations) { this.dateDebutOperations = dateDebutOperations; }

    public String getAdresseSiegeSocial() { return adresseSiegeSocial; }
    public void setAdresseSiegeSocial(String adresseSiegeSocial) { this.adresseSiegeSocial = adresseSiegeSocial; }

    public String getAdresseActivitePrincipale() { return adresseActivitePrincipale; }
    public void setAdresseActivitePrincipale(String adresseActivitePrincipale) { this.adresseActivitePrincipale = adresseActivitePrincipale; }

    public String getAdressesSecondaires() { return adressesSecondaires; }
    public void setAdressesSecondaires(String adressesSecondaires) { this.adressesSecondaires = adressesSecondaires; }

    public String getNumeroTelephone() { return numeroTelephone; }
    public void setNumeroTelephone(String numeroTelephone) { this.numeroTelephone = numeroTelephone; }

    public String getAdresseEmail() { return adresseEmail; }
    public void setAdresseEmail(String adresseEmail) { this.adresseEmail = adresseEmail; }

    public String getListeActivites() { return listeActivites; }
    public void setListeActivites(String listeActivites) { this.listeActivites = listeActivites; }

    public String getActivitePrincipale() { return activitePrincipale; }
    public void setActivitePrincipale(String activitePrincipale) { this.activitePrincipale = activitePrincipale; }

    public String getActivitesSecondaires() { return activitesSecondaires; }
    public void setActivitesSecondaires(String activitesSecondaires) { this.activitesSecondaires = activitesSecondaires; }

    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }

    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }

    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

    // Getters and setters for validation and completion timestamps
    public LocalDateTime getValidatedAt() { return validatedAt; }
    public void setValidatedAt(LocalDateTime validatedAt) { this.validatedAt = validatedAt; }

    public LocalDateTime getCompletedInfoAt() { return completedInfoAt; }
    public void setCompletedInfoAt(LocalDateTime completedInfoAt) { this.completedInfoAt = completedInfoAt; }

    // Getters and setters for relationships
    public Set<InstitutionEpnfdResponse> getEpnfdResponses() { return epnfdResponses; }
    public void setEpnfdResponses(Set<InstitutionEpnfdResponse> epnfdResponses) { this.epnfdResponses = epnfdResponses; }

    public Set<InstitutionActivityResponse> getActivityResponses() { return activityResponses; }
    public void setActivityResponses(Set<InstitutionActivityResponse> activityResponses) { this.activityResponses = activityResponses; }
}
