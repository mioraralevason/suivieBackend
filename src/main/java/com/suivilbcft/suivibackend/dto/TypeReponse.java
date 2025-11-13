package com.suivilbcft.suivibackend.dto;

import java.time.LocalDateTime;

public class TypeReponse{

    private String idTypeReponse;
    private String type;
    private LocalDateTime creeLe;
    private LocalDateTime modifieLe;

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