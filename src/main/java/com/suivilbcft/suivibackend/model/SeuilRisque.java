package com.suivilbcft.suivibackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "seuil_risque")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SeuilRisque {

    @Id
    @Column(name = "id_seuil", length = 50)
    private String idSeuil;

    @Column(name = "taux_min")  // Nouveau champ pour min
    private BigDecimal tauxMin;

    @Column(name = "taux_max")  // Nouveau champ pour max
    private BigDecimal tauxMax;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    // Constructeur sans arguments
    public SeuilRisque() {}

    // Constructeur avec intervalles
    public SeuilRisque(String idSeuil, BigDecimal tauxMin, BigDecimal tauxMax, String description) {
        this.idSeuil = idSeuil;
        this.tauxMin = tauxMin;
        this.tauxMax = tauxMax;
        this.description = description;
    }

    
    public String getIdSeuil() { return idSeuil; }
    public void setIdSeuil(String idSeuil) { this.idSeuil = idSeuil; }
    public BigDecimal getTauxMin() { return tauxMin; }
    public void setTauxMin(BigDecimal tauxMin) { this.tauxMin = tauxMin; }
    public BigDecimal getTauxMax() { return tauxMax; }
    public void setTauxMax(BigDecimal tauxMax) { this.tauxMax = tauxMax; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }
    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }
}