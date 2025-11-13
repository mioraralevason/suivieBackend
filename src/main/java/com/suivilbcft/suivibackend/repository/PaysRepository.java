package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaysRepository extends JpaRepository<Pays, String> {
    Optional<Pays> findByCode(String code);
    List<Pays> findByCategoriePaysLibelle(String libelle); 
}