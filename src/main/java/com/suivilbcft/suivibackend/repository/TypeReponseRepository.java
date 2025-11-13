package com.suivilbcft.suivibackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.suivilbcft.suivibackend.model.TypeReponse;

import java.util.Optional;
public interface TypeReponseRepository extends JpaRepository<TypeReponse, String> {
    Optional<TypeReponse> findByTypeIgnoreCase(String type);
}