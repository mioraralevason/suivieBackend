package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity

@Table(name = "section")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Section {

    @Id
    @Column(name = "id_section", length = 250)
    private String idSection;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "coefficient")
    private BigDecimal coefficient;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();
    @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // ← Évite la récursion JSON (Section → SousSections, pas l'inverse)
    private List<SousSection> sousSections = new ArrayList<>();
    
    public List<SousSection> getSousSections() { return sousSections; }
    public void setSousSections(List<SousSection> sousSections) { this.sousSections = sousSections; }
    // Constructeur sans arguments
    public Section() {}

    // Constructeur avec champs principaux
    public Section(String idSection, String libelle, BigDecimal coefficient) {
        this.idSection = idSection;
        this.libelle = libelle;
        this.coefficient = coefficient;
    }

    public String getIdSection() { return idSection; }
    public void setIdSection(String idSection) { this.idSection = idSection; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public BigDecimal getCoefficient() { return coefficient; }
    public void setCoefficient(BigDecimal coefficient) { this.coefficient = coefficient; }
    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }
    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }
}