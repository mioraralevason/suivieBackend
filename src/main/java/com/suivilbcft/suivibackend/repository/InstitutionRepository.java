package com.suivilbcft.suivibackend.repository;

import com.suivilbcft.suivibackend.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, String> {
}