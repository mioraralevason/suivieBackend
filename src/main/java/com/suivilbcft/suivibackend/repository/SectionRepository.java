package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> {
    Optional<Section> findByLibelleIgnoreCase(String libelle);
    
    // ← Nouvelle méthode pour charger la structure nested : Section + SousSections + Questions
    @Query("SELECT DISTINCT s FROM Section s " +
           "LEFT JOIN FETCH s.sousSections ss " +
           "LEFT JOIN FETCH ss.questions q " +
           "WHERE s.idSection = :idSection")
    Section findByIdSectionWithSousSectionsAndQuestions(@Param("idSection") String idSection);
}