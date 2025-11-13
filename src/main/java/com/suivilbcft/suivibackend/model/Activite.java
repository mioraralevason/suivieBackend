package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "activite")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Activite {

    @Id
    @Column(name = "id_activite", length = 250)
    private String idActivite;

    @Column(name = "libelle", length = 250, nullable = false)
    private String libelle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorie", nullable = false)
    private Categorie categorie;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    
    public Activite() {}


    public Activite(String idActivite, String libelle, Categorie categorie) {
        this.idActivite = idActivite;
        this.libelle = libelle;
        this.categorie = categorie;
    }

 
    public String getIdActivite() { return idActivite; }
    public void setIdActivite(String idActivite) { this.idActivite = idActivite; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }
    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }
}