package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "epnfd_sectors")
public class EpnfdSector {
    @Id
    @Column(name = "id_sector", length = 10)
    private String idSector;

    @Column(name = "libelle", length = 255, nullable = false)
    private String libelle;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    public String getIdSector() { return idSector; }
    public void setIdSector(String idSector) { this.idSector = idSector; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }
}