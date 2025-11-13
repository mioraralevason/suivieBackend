package com.suivilbcft.suivibackend.dto;

import java.math.BigDecimal;  // Import ajouté

public class SeuilRisqueRequest {  // Rendu public
    private BigDecimal tauxMin;
    private BigDecimal tauxMax;
    private String description;

    // Constructeur sans arguments (pour Jackson)
    public SeuilRisqueRequest() {}

    // Getters/Setters
    public BigDecimal getTauxMin() { return tauxMin; }
    public void setTauxMin(BigDecimal tauxMin) { this.tauxMin = tauxMin; }
    public BigDecimal getTauxMax() { return tauxMax; }
    public void setTauxMax(BigDecimal tauxMax) { this.tauxMax = tauxMax; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}