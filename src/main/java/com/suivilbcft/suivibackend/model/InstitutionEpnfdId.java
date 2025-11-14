package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class InstitutionEpnfdId implements Serializable {
    @Column(name = "id_institution")
    private String idInstitution;

    @Column(name = "id_sector")
    private String idSector;

    public InstitutionEpnfdId() {}

    public InstitutionEpnfdId(String idInstitution, String idSector) {
        this.idInstitution = idInstitution;
        this.idSector = idSector;
    }

    public String getIdInstitution() { return idInstitution; }
    public void setIdInstitution(String idInstitution) { this.idInstitution = idInstitution; }

    public String getIdSector() { return idSector; }
    public void setIdSector(String idSector) { this.idSector = idSector; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstitutionEpnfdId)) return false;

        InstitutionEpnfdId that = (InstitutionEpnfdId) o;

        if (!idInstitution.equals(that.idInstitution)) return false;
        return idSector.equals(that.idSector);
    }

    @Override
    public int hashCode() {
        int result = idInstitution.hashCode();
        result = 31 * result + idSector.hashCode();
        return result;
    }
}