package com.suivilbcft.suivibackend.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;  // ← Import ajouté pour éviter la boucle infinie
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "question")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question {

    @Id
    @Column(name = "id_question", length = 250)
    private String idQuestion;

    @Column(name = "libelle", length = 255)
    private String libelle;

    @Column(name = "definition", length = 50)
    private String definition;

    @Column(name = "exige_document")
    private Boolean exigeDocument = false;

    @Column(name = "justification_required")
    private Boolean justificationRequired = false;

    @Column(name = "comment_required")
    private Boolean commentRequired = false;

    @Column(name = "notes", length = 500)
    private String notes = "";

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_reponse", nullable = false)
    private TypeReponse typeReponse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sous_section", nullable = false)
    @JsonBackReference  // ← Ajouté : Ignore ce lien en sérialisation JSON pour briser la boucle (Question → SousSection)
    private SousSection sousSection;

    @Column(name = "options", columnDefinition = "TEXT")
    private String options; 

// Getter & Setter
public String getOptions() { return options; }
public void setOptions(String options) { this.options = options; }
    public Question() {}

    public Question(String idQuestion, String libelle, String definition, Boolean exigeDocument, 
                    TypeReponse typeReponse, SousSection sousSection) {
        this.idQuestion = idQuestion;
        this.libelle = libelle;
        this.definition = definition;
        this.exigeDocument = exigeDocument;
        this.typeReponse = typeReponse;
        this.sousSection = sousSection;
    }

    public List<String> getOptionsList() {
    if (options == null || options.trim().isEmpty()) return List.of();
    return Arrays.stream(options.split(","))
                 .map(String::trim)
                 .filter(s -> !s.isEmpty())
                 .toList();
}
    // Getters et Setters
    public String getIdQuestion() { return idQuestion; }
    public void setIdQuestion(String idQuestion) { this.idQuestion = idQuestion; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }

    public Boolean getExigeDocument() { return exigeDocument; }
    public void setExigeDocument(Boolean exigeDocument) { this.exigeDocument = exigeDocument; }

    public Boolean getJustificationRequired() { return justificationRequired; }
    public void setJustificationRequired(Boolean justificationRequired) { this.justificationRequired = justificationRequired; }

    public Boolean getCommentRequired() { return commentRequired; }
    public void setCommentRequired(Boolean commentRequired) { this.commentRequired = commentRequired; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }

    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }

    public TypeReponse getTypeReponse() { return typeReponse; }
    public void setTypeReponse(TypeReponse typeReponse) { this.typeReponse = typeReponse; }

    public SousSection getSousSection() { return sousSection; }
    public void setSousSection(SousSection sousSection) { this.sousSection = sousSection; }
}