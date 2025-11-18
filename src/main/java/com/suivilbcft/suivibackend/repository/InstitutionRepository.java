package com.suivilbcft.suivibackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suivilbcft.suivibackend.model.Institution;
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, String> {
    Optional<Institution> findByUtilisateur_IdUtilisateur(String idUtilisateur);
      Optional<Institution> findByUtilisateur_Email(String email);
}

