package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "categoriepays")
public class CategoriePays {

    @Id
    @Column(name = "id_categorie_pays", updatable = false, nullable = false)
    private String idCategoriePays;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "cree_le")
    private LocalDateTime creeLe;

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe;


    public CategoriePays() {}

    public CategoriePays(String libelle) {
        this.libelle = libelle;
    }

   
    public String getIdCategoriePays() { return idCategoriePays; }
    public void setIdCategoriePays(String idCategoriePays) { this.idCategoriePays = idCategoriePays; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }

    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }
}
