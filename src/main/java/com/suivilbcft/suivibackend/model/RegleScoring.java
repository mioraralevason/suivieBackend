package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "regle_scoring")
public class RegleScoring {

    @Id
    @Column(name = "id_regle", length = 50)
    private String idRegle;

    @Column(name = "id_question", nullable = false, length = 250)
    private String idQuestion;

    @Column(name = "condition", nullable = false)
    private String condition;

    @Column(name = "note_ri", precision = 2, scale = 1, nullable = true)
    private BigDecimal noteRi;

    @Column(name = "note_sc", precision = 2, scale = 1, nullable = true)
    private BigDecimal noteSc;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    // Relations
    @ManyToOne
    @JoinColumn(name = "id_question", insertable = false, updatable = false)
    private Question question;

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

    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }

    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    @PreUpdate
    public void preUpdate() {
        this.modifieLe = LocalDateTime.now();
    }
}