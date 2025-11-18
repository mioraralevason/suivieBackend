package com.suivilbcft.suivibackend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class InstitutionalInfoRequest {

    @NotBlank(message = "La dénomination sociale est obligatoire")
    private String denominationSociale;

    private String nomCommercial;

    private String formeJuridique;

    @NotNull(message = "La date de début des opérations est obligatoire")
    private LocalDate dateDebutOperations;

    @NotBlank(message = "L'adresse du siège social est obligatoire")
    private String adresseSiegeSocial;

    private String adresseActivitePrincipale;

    private String adressesSecondaires;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String numeroTelephone;

    @NotBlank(message = "L'adresse email est obligatoire")
    @Email(message = "L'adresse email doit être valide")
    private String adresseEmail;

    private String listeActivites;

    private String activitePrincipale;

    private String activitesSecondaires;

    // Getters and setters
    public String getDenominationSociale() {
        return denominationSociale;
    }

    public void setDenominationSociale(String denominationSociale) {
        this.denominationSociale = denominationSociale;
    }

    public String getNomCommercial() {
        return nomCommercial;
    }

    public void setNomCommercial(String nomCommercial) {
        this.nomCommercial = nomCommercial;
    }

    public String getFormeJuridique() {
        return formeJuridique;
    }

    public void setFormeJuridique(String formeJuridique) {
        this.formeJuridique = formeJuridique;
    }

    public LocalDate getDateDebutOperations() {
        return dateDebutOperations;
    }

    public void setDateDebutOperations(LocalDate dateDebutOperations) {
        this.dateDebutOperations = dateDebutOperations;
    }

    public String getAdresseSiegeSocial() {
        return adresseSiegeSocial;
    }

    public void setAdresseSiegeSocial(String adresseSiegeSocial) {
        this.adresseSiegeSocial = adresseSiegeSocial;
    }

    public String getAdresseActivitePrincipale() {
        return adresseActivitePrincipale;
    }

    public void setAdresseActivitePrincipale(String adresseActivitePrincipale) {
        this.adresseActivitePrincipale = adresseActivitePrincipale;
    }

    public String getAdressesSecondaires() {
        return adressesSecondaires;
    }

    public void setAdressesSecondaires(String adressesSecondaires) {
        this.adressesSecondaires = adressesSecondaires;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    public String getListeActivites() {
        return listeActivites;
    }

    public void setListeActivites(String listeActivites) {
        this.listeActivites = listeActivites;
    }

    public String getActivitePrincipale() {
        return activitePrincipale;
    }

    public void setActivitePrincipale(String activitePrincipale) {
        this.activitePrincipale = activitePrincipale;
    }

    public String getActivitesSecondaires() {
        return activitesSecondaires;
    }

    public void setActivitesSecondaires(String activitesSecondaires) {
        this.activitesSecondaires = activitesSecondaires;
    }
}