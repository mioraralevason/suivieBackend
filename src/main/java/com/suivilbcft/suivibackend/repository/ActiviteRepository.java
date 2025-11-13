package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.Activite;
import com.suivilbcft.suivibackend.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActiviteRepository extends JpaRepository<Activite, String> {
    List<Activite> findByCategorie(Categorie categorie);
    List<Activite> findByLibelleContainingIgnoreCase(String libelle);
}