package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_details")
public class ActivityDetail {
    @Id
    @Column(name = "id_activity", length = 10)
    private String idActivity;

    @Column(name = "libelle", length = 255, nullable = false)
    private String libelle;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    public String getIdActivity() { return idActivity; }
    public void setIdActivity(String idActivity) { this.idActivity = idActivity; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }
}