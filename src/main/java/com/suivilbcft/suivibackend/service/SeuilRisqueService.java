package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.model.SeuilRisque;
import com.suivilbcft.suivibackend.repository.SeuilRisqueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SeuilRisqueService {

    private final SeuilRisqueRepository seuilRisqueRepository;

    public SeuilRisqueService(SeuilRisqueRepository seuilRisqueRepository) {
        this.seuilRisqueRepository = seuilRisqueRepository;
    }

  
    public SeuilRisque createSeuilRisque(BigDecimal tauxMin, BigDecimal tauxMax, String description) {
        if (tauxMin == null || tauxMax == null || tauxMin.compareTo(tauxMax) >= 0) {
            throw new RuntimeException("Taux min doit être inférieur à taux max et non null");
        }
        if (seuilRisqueRepository.findByDescriptionIgnoreCase(description).isPresent()) {
            throw new RuntimeException("Seuil de risque avec description '" + description + "' existe déjà");
        }

        String idSeuil = UUID.randomUUID().toString().substring(0, 13);  // Trunc pour VARCHAR(50)
        SeuilRisque seuilRisque = new SeuilRisque(idSeuil, tauxMin, tauxMax, description);
        seuilRisque.setCreeLe(LocalDateTime.now());
        seuilRisque.setModifieLe(LocalDateTime.now());
        return seuilRisqueRepository.save(seuilRisque);
    }

    
    public List<SeuilRisque> getAllSeuilsRisque() {
        return seuilRisqueRepository.findAll();
    }

    public Optional<SeuilRisque> getSeuilRisqueById(String idSeuil) {
        return seuilRisqueRepository.findById(idSeuil);
    }

    public Optional<SeuilRisque> getSeuilRisqueByDescription(String description) {
        return seuilRisqueRepository.findByDescriptionIgnoreCase(description);
    }

   
    public SeuilRisque updateSeuilRisque(String idSeuil, BigDecimal tauxMin, BigDecimal tauxMax, String description) {
        Optional<SeuilRisque> seuilRisqueOpt = seuilRisqueRepository.findById(idSeuil);  // Correction: seuilRisqueOpt au lieu de seuilRisqueOpt (typo ?)
        if (seuilRisqueOpt.isEmpty()) {  // Correction: seuilRisqueOpt
            throw new RuntimeException("Seuil de risque non trouvé: " + idSeuil);
        }

        if (description != null && !seuilRisqueRepository.findByDescriptionIgnoreCase(description).map(s -> s.getIdSeuil().equals(idSeuil)).orElse(true)) {
            throw new RuntimeException("Description '" + description + "' existe déjà pour un autre seuil");
        }

        if (tauxMin != null && tauxMax != null && tauxMin.compareTo(tauxMax) >= 0) {  
            throw new RuntimeException("Taux min doit être inférieur à taux max");
        }

        SeuilRisque seuilRisque = seuilRisqueOpt.get();
        if (tauxMin != null) {  
            seuilRisque.setTauxMin(tauxMin);  
        }
        if (tauxMax != null) {  
            seuilRisque.setTauxMax(tauxMax); 
        }
        if (description != null) {
            seuilRisque.setDescription(description);
        }
        seuilRisque.setModifieLe(LocalDateTime.now());
        return seuilRisqueRepository.save(seuilRisque);
    }

    public void deleteSeuilRisque(String idSeuil) {
        if (!seuilRisqueRepository.existsById(idSeuil)) {
            throw new RuntimeException("Seuil de risque non trouvé: " + idSeuil);
        }
        seuilRisqueRepository.deleteById(idSeuil);
    }
}