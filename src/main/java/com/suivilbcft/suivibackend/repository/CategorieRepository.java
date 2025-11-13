package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, String> {
    Optional<Categorie> findByLibelleIgnoreCase(String libelle);
}