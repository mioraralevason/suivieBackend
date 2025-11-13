package com.suivilbcft.suivibackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;  // ← Import ajouté
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;  // ← Pour JSON

@Entity
@Table(name = "sous_section")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SousSection {

    @Id
    @Column(name = "id_sous_section", length = 50)
    private String idSousSection;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_section", nullable = false)
    @JsonBackReference  // ← Évite la récursion JSON (pas de retour vers Section)
    private Section section;

    // ← Nouvelle relation : OneToMany vers Questions
    @OneToMany(mappedBy = "sousSection", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public SousSection() {}

    public SousSection(String idSousSection, String libelle, Section section) {
        this.idSousSection = idSousSection;
        this.libelle = libelle;
        this.section = section;
    }

    // Getters/Setters existants...
    public String getIdSousSection() { return idSousSection; }
    public void setIdSousSection(String idSousSection) { this.idSousSection = idSousSection; }
    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }
    public Section getSection() { return section; }
    public void setSection(Section section) { this.section = section; }
    public LocalDateTime getCreeLe() { return creeLe; }
    public void setCreeLe(LocalDateTime creeLe) { this.creeLe = creeLe; }
    public LocalDateTime getModifieLe() { return modifieLe; }
    public void setModifieLe(LocalDateTime modifieLe) { this.modifieLe = modifieLe; }

    // ← Nouveaux getters/setters pour questions
    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}