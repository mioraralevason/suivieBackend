package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

    // Requête spécifique avec DISTINCT pour éviter les duplications
    @Query("SELECT DISTINCT u FROM Utilisateur u WHERE u.email = :email")
    Optional<Utilisateur> findByEmail(@Param("email") String email);

    // Méthode pour vérifier l'existence sans charger l'objet
    boolean existsByEmail(String email);
}