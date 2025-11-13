package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "type_reponse")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TypeReponse {

    @Id
    @Column(name = "id_type_reponse", length = 250)
    private String idTypeReponse;

    @Column(name = "type", length = 250)
    private String type;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    public TypeReponse() {}

    public TypeReponse(String idTypeReponse, String type) {
        this.idTypeReponse = idTypeReponse;
        this.type = type;
    }

    public String getIdTypeReponse() { return idTypeReponse; }
    public void setIdTypeReponse(String idTypeReponse) { this.idTypeReponse = idTypeReponse; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }

    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }
}