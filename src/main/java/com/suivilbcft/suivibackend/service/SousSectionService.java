package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.model.Section;
import com.suivilbcft.suivibackend.model.SousSection;
import com.suivilbcft.suivibackend.repository.SousSectionRepository;
import com.suivilbcft.suivibackend.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SousSectionService {

    private final SousSectionRepository sousSectionRepository;
    private final SectionRepository sectionRepository;

    public SousSectionService(SousSectionRepository sousSectionRepository, SectionRepository sectionRepository) {
        this.sousSectionRepository = sousSectionRepository;
        this.sectionRepository = sectionRepository;
    }

    /**
     * Créer une nouvelle sous-section
     */
    public SousSection createSousSection(String libelle, String idSection) {
        Optional<Section> sectionOpt = sectionRepository.findById(idSection);
        if (sectionOpt.isEmpty()) {
            throw new RuntimeException("Section non trouvée: " + idSection);
        }

        if (sousSectionRepository.findByLibelleIgnoreCase(libelle).isPresent()) {
            throw new RuntimeException("Sous-section avec libelle '" + libelle + "' existe déjà");
        }

        String idSousSection = UUID.randomUUID().toString().substring(0, 13);  // Trunc pour VARCHAR(50)
        SousSection sousSection = new SousSection(idSousSection, libelle, sectionOpt.get());
        sousSection.setCreeLe(LocalDateTime.now());
        sousSection.setModifieLe(LocalDateTime.now());
        return sousSectionRepository.save(sousSection);
    }

    /**
     * Récupérer toutes les sous-sections
     */
    public List<SousSection> getAllSousSections() {
        return sousSectionRepository.findAll();
    }

    /**
     * Récupérer les sous-sections par section
     */
    public List<SousSection> getSousSectionsBySection(String idSection) {
        return sousSectionRepository.findBySectionIdSection(idSection);
    }

    /**
     * Récupérer une sous-section par ID
     */
    public Optional<SousSection> getSousSectionById(String idSousSection) {
        return sousSectionRepository.findById(idSousSection);
    }

    /**
     * Récupérer une sous-section par libelle
     */
    public Optional<SousSection> getSousSectionByLibelle(String libelle) {
        return sousSectionRepository.findByLibelleIgnoreCase(libelle);
    }

    /**
     * Mettre à jour une sous-section
     */
    public SousSection updateSousSection(String idSousSection, String libelle, String idSection) {
        Optional<SousSection> sousSectionOpt = sousSectionRepository.findById(idSousSection);
        if (sousSectionOpt.isEmpty()) {
            throw new RuntimeException("Sous-section non trouvée: " + idSousSection);
        }

        Optional<Section> sectionOpt = sectionRepository.findById(idSection);
        if (sectionOpt.isEmpty()) {
            throw new RuntimeException("Section non trouvée: " + idSection);
        }

        if (libelle != null && !sousSectionRepository.findByLibelleIgnoreCase(libelle).map(s -> s.getIdSousSection().equals(idSousSection)).orElse(true)) {
            throw new RuntimeException("Libelle '" + libelle + "' existe déjà pour une autre sous-section");
        }

        SousSection sousSection = sousSectionOpt.get();
        if (libelle != null) {
            sousSection.setLibelle(libelle);
        }
        sousSection.setSection(sectionOpt.get());
        sousSection.setModifieLe(LocalDateTime.now());
        return sousSectionRepository.save(sousSection);
    }

    /**
     * Supprimer une sous-section
     */
    public void deleteSousSection(String idSousSection) {
        if (!sousSectionRepository.existsById(idSousSection)) {
            throw new RuntimeException("Sous-section non trouvée: " + idSousSection);
        }
        sousSectionRepository.deleteById(idSousSection);
    }
}