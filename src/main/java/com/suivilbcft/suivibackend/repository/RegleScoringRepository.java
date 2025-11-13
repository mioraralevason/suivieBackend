package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.RegleScoring;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegleScoringRepository extends JpaRepository<RegleScoring, String> {
    List<RegleScoring> findByIdQuestion(String idQuestion);
    boolean existsByIdQuestionAndIdRegleNot(String idQuestion, String idRegle);
}