package com.suivilbcft.suivibackend.repository;
import com.suivilbcft.suivibackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByLibelle(String libelle);
}