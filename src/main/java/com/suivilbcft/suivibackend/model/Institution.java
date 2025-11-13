package com.suivilbcft.suivibackend.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "institution")
public class Institution {
    @Id
    @Column(name = "id_institution", length = 250)
    private String idInstitution;

    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    @Column(name = "adresse", length = 50)
    private String adresse;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", nullable = false)
    private Utilisateur utilisateur;

    public Institution() {}

    public Institution(String idInstitution, String nom, String adresse, String description, Utilisateur utilisateur) {
        this.idInstitution = idInstitution;
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.utilisateur = utilisateur;
    }

    public String getIdInstitution() { return idInstitution; }
    public void setIdInstitution(String idInstitution) { this.idInstitution = idInstitution; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }
    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }
}
