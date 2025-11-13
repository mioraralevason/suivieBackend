package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.model.Section;
import com.suivilbcft.suivibackend.repository.SectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    /**
     * Créer une nouvelle section
     */
    public Section createSection(String libelle, BigDecimal coefficient) {
        if (sectionRepository.findByLibelleIgnoreCase(libelle).isPresent()) {
            throw new RuntimeException("Section avec libelle '" + libelle + "' existe déjà");
        }

        String idSection = UUID.randomUUID().toString();
        Section section = new Section(idSection, libelle, coefficient);
        section.setCreeLe(LocalDateTime.now());
        section.setModifieLe(LocalDateTime.now());
        return sectionRepository.save(section);
    }

    /**
     * Récupérer toutes les sections
     */
    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    /**
     * Récupérer une section par ID
     */
    public Optional<Section> getSectionById(String idSection) {
        return sectionRepository.findById(idSection);
    }

    /**
     * Récupérer une section par libelle
     */
    public Optional<Section> getSectionByLibelle(String libelle) {
        return sectionRepository.findByLibelleIgnoreCase(libelle);
    }

    /**
     * Mettre à jour une section
     */
    public Section updateSection(String idSection, String libelle, BigDecimal coefficient) {
        Optional<Section> sectionOpt = sectionRepository.findById(idSection);
        if (sectionOpt.isEmpty()) {
            throw new RuntimeException("Section non trouvée: " + idSection);
        }

        if (libelle != null && !sectionRepository.findByLibelleIgnoreCase(libelle).map(s -> s.getIdSection().equals(idSection)).orElse(true)) {
            throw new RuntimeException("Libelle '" + libelle + "' existe déjà pour une autre section");
        }

        Section section = sectionOpt.get();
        if (libelle != null) {
            section.setLibelle(libelle);
        }
        if (coefficient != null) {
            section.setCoefficient(coefficient);
        }
        section.setModifieLe(LocalDateTime.now());
        return sectionRepository.save(section);
    }

    /**
     * Supprimer une section
     */
    public void deleteSection(String idSection) {
        if (!sectionRepository.existsById(idSection)) {
            throw new RuntimeException("Section non trouvée: " + idSection);
        }
        sectionRepository.deleteById(idSection);
    }

    /**
     * ← NOUVELLE MÉTHODE : Récupérer une section avec ses sous-sections et questions imbriquées
     * Utilise la requête JPQL du repository pour charger tout en une seule fois (JOIN FETCH)
     */
    public Section getSectionWithSousSectionsAndQuestions(String idSection) {
        System.out.println("🔍 Chargement section nested pour: " + idSection);
        Section section = sectionRepository.findByIdSectionWithSousSectionsAndQuestions(idSection);
        if (section == null) {
            throw new RuntimeException("Section non trouvée: " + idSection);
        }
        
        // Debug pour vérifier le chargement nested
        System.out.println("✅ Section chargée: " + section.getLibelle() + " (" + section.getSousSections().size() + " sous-sections)");
        section.getSousSections().forEach(ss -> {
            System.out.println("  - SousSection: " + ss.getLibelle() + " (" + ss.getQuestions().size() + " questions)");
            ss.getQuestions().forEach(q -> System.out.println("    - Question: " + q.getLibelle()));
        });
        
        return section;
    }


    // public List<Section> getSimpleSections() {
    //     System.out.println("🔍 Chargement simple des sections (sans enfants)");
    //     return sectionRepository.findAll();  // Basique, sans JOIN FETCH
    // }
}