package com.suivilbcft.suivibackend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegleScoringDTO {
   
    @Size(max = 50)
    private String idRegle;

    @NotBlank
    @Size(max = 250)
    private String idQuestion;

    @NotBlank
    private String condition;

    private BigDecimal noteRi;
    private BigDecimal noteSc;

    // Getters & Setters
    public String getIdRegle() { return idRegle; }
    public void setIdRegle(String idRegle) { this.idRegle = idRegle; }

    public String getIdQuestion() { return idQuestion; }
    public void setIdQuestion(String idQuestion) { this.idQuestion = idQuestion; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public BigDecimal getNoteRi() { return noteRi; }
    public void setNoteRi(BigDecimal noteRi) { this.noteRi = noteRi; }

    public BigDecimal getNoteSc() { return noteSc; }
    public void setNoteSc(BigDecimal noteSc) { this.noteSc = noteSc; }

}