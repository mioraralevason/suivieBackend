package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.SousSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SousSectionRepository extends JpaRepository<SousSection, String> {
    List<SousSection> findBySectionIdSection(String idSection);
    Optional<SousSection> findByLibelleIgnoreCase(String libelle);
}