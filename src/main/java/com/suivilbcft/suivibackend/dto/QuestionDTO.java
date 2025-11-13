package com.suivilbcft.suivibackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionDTO {

    private String id;

    @NotNull(message = "axisId est obligatoire")
    @JsonProperty("axisId")
    private String axisId; // id_sous_section

    @NotBlank(message = "Le libellé est obligatoire")
    @JsonProperty("libelle")
    private String libelle;

    @JsonProperty("definition")
    private String definition;

    @NotBlank(message = "Le type de réponse est obligatoire")
    @JsonProperty("type")
    private String type; // ex: "choice_multiple", "boolean", "percentage"

    @JsonProperty("required")
    private Boolean required;

    @JsonProperty("justificationRequired")
    private Boolean justificationRequired;

    @JsonProperty("commentRequired")
    private Boolean commentRequired;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("options")
    private String options; // "opt1, opt2, opt3" — uniquement pour choice_multiple et choice_single

    // === CONSTRUCTEURS ===
    public QuestionDTO() {}

    // === GETTERS & SETTERS ===
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAxisId() { return axisId; }
    public void setAxisId(String axisId) { this.axisId = axisId; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }

    public Boolean getJustificationRequired() { return justificationRequired; }
    public void setJustificationRequired(Boolean justificationRequired) { this.justificationRequired = justificationRequired; }

    public Boolean getCommentRequired() { return commentRequired; }
    public void setCommentRequired(Boolean commentRequired) { this.commentRequired = commentRequired; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getOptions() { return options; }
    public void setOptions(String options) { this.options = options; }

    // === TOSTRING ===
    @Override
    public String toString() {
        return "QuestionDTO{" +
                "id='" + id + '\'' +
                ", axisId='" + axisId + '\'' +
                ", libelle='" + libelle + '\'' +
                ", definition='" + definition + '\'' +
                ", type='" + type + '\'' +
                ", required=" + required +
                ", justificationRequired=" + justificationRequired +
                ", commentRequired=" + commentRequired +
                ", notes='" + notes + '\'' +
                ", options='" + options + '\'' +
                '}';
    }
}