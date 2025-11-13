package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.CategoriePays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoriePaysRepository extends JpaRepository<CategoriePays, String> {
    Optional<CategoriePays> findByLibelle(String libelle);
    Optional<CategoriePays> findByLibelleIgnoreCase(String libelle);
}