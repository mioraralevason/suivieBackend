package com.suivilbcft.suivibackend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suivilbcft.suivibackend.dto.QuestionDTO;
import com.suivilbcft.suivibackend.model.Question;
import com.suivilbcft.suivibackend.model.SousSection;
import com.suivilbcft.suivibackend.model.TypeReponse;
import com.suivilbcft.suivibackend.repository.QuestionRepository;
import com.suivilbcft.suivibackend.repository.SousSectionRepository;
import com.suivilbcft.suivibackend.repository.TypeReponseRepository;

@Service
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TypeReponseRepository typeReponseRepository;
    private final SousSectionRepository sousSectionRepository;

    public QuestionService(
            QuestionRepository questionRepository,
            TypeReponseRepository typeReponseRepository,
            SousSectionRepository sousSectionRepository) {
        this.questionRepository = questionRepository;
        this.typeReponseRepository = typeReponseRepository;
        this.sousSectionRepository = sousSectionRepository;
    }

    /**
     * Crée une nouvelle question
     */
    public Question createQuestion(QuestionDTO dto) {
        // === VALIDATIONS OBLIGATOIRES ===
        if (dto.getLibelle() == null || dto.getLibelle().trim().isEmpty())
            throw new RuntimeException("Le libellé est requis");

        if (dto.getAxisId() == null || dto.getAxisId().trim().isEmpty())
            throw new RuntimeException("L'ID de la sous-section (axisId) est requis");

        if (dto.getType() == null || dto.getType().trim().isEmpty())
            throw new RuntimeException("Le type de réponse est requis");

        // === UNICITÉ DU LIBELLÉ DANS LA SOUS-SECTION ===
        if (questionRepository.findBySousSectionIdSousSectionAndLibelleIgnoreCase(
                dto.getAxisId(), dto.getLibelle()).isPresent()) {
            throw new RuntimeException("Question avec libellé '" + dto.getLibelle() + "' existe déjà dans cette sous-section");
        }

        // === VALIDATION CONDITIONNELLE : OPTIONS POUR CHOIX ===
        if (List.of("choice_multiple", "choice_single").contains(dto.getType())) {
            if (dto.getOptions() == null || dto.getOptions().trim().isEmpty()) {
                throw new RuntimeException("Les options sont obligatoires pour le type '" + dto.getType() + "'");
            }
        }

        // === TYPE RÉPONSE ===
        TypeReponse typeReponse = typeReponseRepository.findByTypeIgnoreCase(dto.getType())
                .orElseThrow(() -> new RuntimeException("Type de réponse inconnu: " + dto.getType()));

        // === SOUS-SECTION ===
        SousSection sousSection = sousSectionRepository.findById(dto.getAxisId())
                .orElseThrow(() -> new RuntimeException("Sous-section non trouvée: " + dto.getAxisId()));

        // === CRÉATION DE LA QUESTION ===
        Question question = new Question(
                UUID.randomUUID().toString(),
                dto.getLibelle(),
                dto.getDefinition() != null ? dto.getDefinition() : "",
                dto.getRequired() != null && dto.getRequired(),
                typeReponse,
                sousSection
        );

        // Champs optionnels
        question.setJustificationRequired(dto.getJustificationRequired() != null && dto.getJustificationRequired());
        question.setCommentRequired(dto.getCommentRequired() != null && dto.getCommentRequired());
        question.setNotes(dto.getNotes() != null ? dto.getNotes() : "");
        question.setOptions(dto.getOptions() != null && !dto.getOptions().trim().isEmpty() ? dto.getOptions().trim() : null);

        // Timestamps
        question.setCreeLe(LocalDateTime.now());
        question.setModifieLe(LocalDateTime.now());

        return questionRepository.save(question);
    }

    /**
     * Met à jour une question existante
     */
    public Question updateQuestion(String idQuestion, QuestionDTO dto) {
        Question question = questionRepository.findById(idQuestion)
                .orElseThrow(() -> new RuntimeException("Question non trouvée: " + idQuestion));

        // === LIBELLÉ (avec unicité) ===
        if (dto.getLibelle() != null && !dto.getLibelle().trim().isEmpty()
                && !dto.getLibelle().trim().equalsIgnoreCase(question.getLibelle())) {

            boolean exists = questionRepository.findBySousSectionIdSousSectionAndLibelleIgnoreCase(
                    question.getSousSection().getIdSousSection(), dto.getLibelle()).isPresent();

            if (exists) {
                throw new RuntimeException("Libellé déjà utilisé dans cette sous-section");
            }
            question.setLibelle(dto.getLibelle().trim());
        }

        // === AUTRES CHAMPS ===
        if (dto.getDefinition() != null) {
            question.setDefinition(dto.getDefinition());
        }

        if (dto.getRequired() != null) {
            question.setExigeDocument(dto.getRequired());
        }

        if (dto.getType() != null && !dto.getType().trim().isEmpty()) {
            TypeReponse typeReponse = typeReponseRepository.findByTypeIgnoreCase(dto.getType())
                    .orElseThrow(() -> new RuntimeException("Type inconnu: " + dto.getType()));
            question.setTypeReponse(typeReponse);
        }

        if (dto.getAxisId() != null && !dto.getAxisId().equals(question.getSousSection().getIdSousSection())) {
            SousSection sousSection = sousSectionRepository.findById(dto.getAxisId())
                    .orElseThrow(() -> new RuntimeException("Sous-section non trouvée: " + dto.getAxisId()));
            question.setSousSection(sousSection);
        }

        if (dto.getJustificationRequired() != null) {
            question.setJustificationRequired(dto.getJustificationRequired());
        }

        if (dto.getCommentRequired() != null) {
            question.setCommentRequired(dto.getCommentRequired());
        }

        if (dto.getNotes() != null) {
            question.setNotes(dto.getNotes());
        }

        // === OPTIONS (uniquement pour choice_multiple / choice_single) ===
        if (dto.getOptions() != null) {
            if (dto.getOptions().trim().isEmpty()) {
                question.setOptions(null);
            } else {
                question.setOptions(dto.getOptions().trim());
            }
        }

        // === VALIDATION POST-UPDATE : options obligatoires si type choix ===
        if (List.of("choice_multiple", "choice_single").contains(question.getTypeReponse().getType())) {
            if (question.getOptions() == null || question.getOptions().trim().isEmpty()) {
                throw new RuntimeException("Les options sont obligatoires pour le type '" + question.getTypeReponse().getType() + "'");
            }
        }

        question.setModifieLe(LocalDateTime.now());
        return questionRepository.save(question);
    }

    // === MÉTHODES DE LECTURE ===
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsBySousSectionId(String id) {
        return questionRepository.findBySousSectionIdSousSection(id);
    }

    public Optional<Question> getQuestionById(String id) {
        return questionRepository.findById(id);
    }

    public void deleteQuestion(String id) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question non trouvée");
        }
        questionRepository.deleteById(id);
    }

    // === DEBUG ===
    public List<Question> getQuestionsBySectionId(String idSection) {
        List<Question> questions = questionRepository.findBySousSectionSectionIdSection(idSection);
        System.out.println("Questions pour section " + idSection + " : " + questions.size());
        return questions;
    }
}