package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.SeuilRisque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeuilRisqueRepository extends JpaRepository<SeuilRisque, String> {
    Optional<SeuilRisque> findByDescriptionIgnoreCase(String description);
}