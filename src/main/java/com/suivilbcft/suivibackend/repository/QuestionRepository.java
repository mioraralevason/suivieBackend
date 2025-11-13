package com.suivilbcft.suivibackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import com.suivilbcft.suivibackend.model.Question;

public interface QuestionRepository extends JpaRepository<Question, String> {
    List<Question> findBySousSectionIdSousSection(String idSousSection);
    Optional<Question> findBySousSectionIdSousSectionAndLibelleIgnoreCase(String idSousSection, String libelle);
    List<Question> findBySousSectionSectionIdSection(String idSection); 
}