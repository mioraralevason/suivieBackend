package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.model.TypeReponse;
import com.suivilbcft.suivibackend.repository.TypeReponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TypeReponseService {

    private final TypeReponseRepository typeReponseRepository;

    public TypeReponseService(TypeReponseRepository typeReponseRepository) {
        this.typeReponseRepository = typeReponseRepository;
    }

    public TypeReponse createTypeReponse(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new RuntimeException("Le type doit être non null et non vide");
        }
        if (typeReponseRepository.findByTypeIgnoreCase(type).isPresent()) {
            throw new RuntimeException("Type de réponse '" + type + "' existe déjà");
        }

        String idTypeReponse = UUID.randomUUID().toString();  
        TypeReponse typeReponse = new TypeReponse(idTypeReponse, type);
        typeReponse.setCreeLe(LocalDateTime.now());
        typeReponse.setModifieLe(LocalDateTime.now());
        return typeReponseRepository.save(typeReponse);
    }

    public List<TypeReponse> getAllTypeReponse() {
        return typeReponseRepository.findAll();
    }

    public Optional<TypeReponse> getTypeReponseById(String idTypeReponse) {
        return typeReponseRepository.findById(idTypeReponse);
    }

    public Optional<TypeReponse> getTypeReponseByType(String type) {
        return typeReponseRepository.findByTypeIgnoreCase(type);
    }

    public TypeReponse updateTypeReponse(String idTypeReponse, String type) {
        Optional<TypeReponse> typeReponseOpt = typeReponseRepository.findById(idTypeReponse);
        if (typeReponseOpt.isEmpty()) {
            throw new RuntimeException("Type de réponse non trouvé: " + idTypeReponse);
        }

        if (type != null && !type.trim().isEmpty() && !typeReponseRepository.findByTypeIgnoreCase(type).map(t -> t.getIdTypeReponse().equals(idTypeReponse)).orElse(true)) {
            throw new RuntimeException("Type '" + type + "' existe déjà pour un autre type de réponse");
        }

        TypeReponse typeReponse = typeReponseOpt.get();
        if (type != null && !type.trim().isEmpty()) {
            typeReponse.setType(type);
        }
        typeReponse.setModifieLe(LocalDateTime.now());
        return typeReponseRepository.save(typeReponse);
    }

    public void deleteTypeReponse(String idTypeReponse) {
        if (!typeReponseRepository.existsById(idTypeReponse)) {
            throw new RuntimeException("Type de réponse non trouvé: " + idTypeReponse);
        }
        typeReponseRepository.deleteById(idTypeReponse);
    }
}