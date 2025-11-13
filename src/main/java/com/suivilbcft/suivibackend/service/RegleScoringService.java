package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.dto.RegleScoringDTO;
import com.suivilbcft.suivibackend.model.RegleScoring;
import com.suivilbcft.suivibackend.repository.QuestionRepository;
import com.suivilbcft.suivibackend.repository.RegleScoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RegleScoringService {

    @Autowired
    private RegleScoringRepository regleScoringRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public RegleScoring createRegle(RegleScoringDTO dto) {
        if (!questionRepository.existsById(dto.getIdQuestion())) {
            throw new RuntimeException("Question inexistante : " + dto.getIdQuestion());
        }

        RegleScoring regle = new RegleScoring();
        regle.setIdRegle(dto.getIdRegle() != null ? dto.getIdRegle() : UUID.randomUUID().toString().substring(0, 8));
        regle.setIdQuestion(dto.getIdQuestion());
        regle.setCondition(dto.getCondition());
        regle.setNoteRi(dto.getNoteRi() != null ? dto.getNoteRi() : null);
        regle.setNoteSc(dto.getNoteSc() != null ? dto.getNoteSc() : null);

        return regleScoringRepository.save(regle);
    }

    public List<RegleScoring> getReglesByQuestion(String idQuestion) {
        return regleScoringRepository.findByIdQuestion(idQuestion);
    }

    public RegleScoring updateRegle(String idRegle, RegleScoringDTO dto) {
        RegleScoring regle = regleScoringRepository.findById(idRegle)
                .orElseThrow(() -> new RuntimeException("Règle non trouvée : " + idRegle));

        regle.setCondition(dto.getCondition());
        regle.setNoteRi(dto.getNoteRi());
        regle.setNoteSc(dto.getNoteSc());
        return regleScoringRepository.save(regle);
    }

    public void deleteRegle(String idRegle) {
        if (!regleScoringRepository.existsById(idRegle)) {
            throw new RuntimeException("Règle non trouvée : " + idRegle);
        }
        regleScoringRepository.deleteById(idRegle);
    }
}