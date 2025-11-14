package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class InstitutionActivityId implements Serializable {
    @Column(name = "id_institution")
    private String idInstitution;

    @Column(name = "id_activity")
    private String idActivity;

    public InstitutionActivityId() {}

    public InstitutionActivityId(String idInstitution, String idActivity) {
        this.idInstitution = idInstitution;
        this.idActivity = idActivity;
    }

    public String getIdInstitution() { return idInstitution; }
    public void setIdInstitution(String idInstitution) { this.idInstitution = idInstitution; }

    public String getIdActivity() { return idActivity; }
    public void setIdActivity(String idActivity) { this.idActivity = idActivity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstitutionActivityId)) return false;

        InstitutionActivityId that = (InstitutionActivityId) o;

        if (!idInstitution.equals(that.idInstitution)) return false;
        return idActivity.equals(that.idActivity);
    }

    @Override
    public int hashCode() {
        int result = idInstitution.hashCode();
        result = 31 * result + idActivity.hashCode();
        return result;
    }
}