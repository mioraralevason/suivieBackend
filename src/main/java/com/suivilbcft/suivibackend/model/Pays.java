package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pays")
public class Pays {

    @Id
    @Column(name = "id_pays", updatable = false, nullable = false)
    private String idPays;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_categorie_pays", nullable = false)
    private CategoriePays categoriePays;

   
    public Pays() {}

    public Pays(String libelle, String code, CategoriePays categoriePays) {
        this.libelle = libelle;
        this.code = code;
        this.categoriePays = categoriePays;
    }

    // Getters / Setters
    public String getIdPays() { return idPays; }
    public void setIdPays(String idPays) { this.idPays = idPays; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }

    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }

    public CategoriePays getCategoriePays() { return categoriePays; }
    public void setCategoriePays(CategoriePays categoriePays) { this.categoriePays = categoriePays; }
}
