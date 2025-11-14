package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "institution_epnfd_responses")
public class InstitutionEpnfdResponse {
    @EmbeddedId
    private InstitutionEpnfdId id = new InstitutionEpnfdId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idInstitution")
    @JoinColumn(name = "id_institution")
    private Institution institution;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSector")
    @JoinColumn(name = "id_sector")
    private EpnfdSector epnfdSector;

    @Column(name = "is_applicable", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isApplicable = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public InstitutionEpnfdResponse() {}

    public InstitutionEpnfdResponse(Institution institution, EpnfdSector epnfdSector, Boolean isApplicable) {
        this.id = new InstitutionEpnfdId(institution.getIdInstitution(), epnfdSector.getIdSector());
        this.institution = institution;
        this.epnfdSector = epnfdSector;
        this.isApplicable = isApplicable;
    }

    // Getters and setters
    public InstitutionEpnfdId getId() { return id; }
    public void setId(InstitutionEpnfdId id) { this.id = id; }

    public Institution getInstitution() { return institution; }
    public void setInstitution(Institution institution) { this.institution = institution; }

    public EpnfdSector getEpnfdSector() { return epnfdSector; }
    public void setEpnfdSector(EpnfdSector epnfdSector) { this.epnfdSector = epnfdSector; }

    public Boolean getIsApplicable() { return isApplicable; }
    public void setIsApplicable(Boolean applicable) { isApplicable = applicable; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}