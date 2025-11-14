package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "institution_activity_responses")
public class InstitutionActivityResponse {
    @EmbeddedId
    private InstitutionActivityId id = new InstitutionActivityId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idInstitution")
    @JoinColumn(name = "id_institution")
    private Institution institution;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idActivity")
    @JoinColumn(name = "id_activity")
    private ActivityDetail activityDetail;

    @Column(name = "response", columnDefinition = "BOOLEAN")
    private Boolean response;

    @Column(name = "justification", length = 1000)
    private String justification;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public InstitutionActivityResponse() {}

    public InstitutionActivityResponse(Institution institution, ActivityDetail activityDetail, Boolean response, String justification) {
        this.id = new InstitutionActivityId(institution.getIdInstitution(), activityDetail.getIdActivity());
        this.institution = institution;
        this.activityDetail = activityDetail;
        this.response = response;
        this.justification = justification;
    }

    // Getters and setters
    public InstitutionActivityId getId() { return id; }
    public void setId(InstitutionActivityId id) { this.id = id; }

    public Institution getInstitution() { return institution; }
    public void setInstitution(Institution institution) { this.institution = institution; }

    public ActivityDetail getActivityDetail() { return activityDetail; }
    public void setActivityDetail(ActivityDetail activityDetail) { this.activityDetail = activityDetail; }

    public Boolean getResponse() { return response; }
    public void setResponse(Boolean response) { this.response = response; }

    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}