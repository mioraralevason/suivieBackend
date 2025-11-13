package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.model.Section;
import com.suivilbcft.suivibackend.service.SectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    /**
     * Récupérer toutes les sections
     */
    @GetMapping
    public ResponseEntity<List<Section>> getAllSections() {
        List<Section> sections = sectionService.getAllSections();
        return ResponseEntity.ok(sections);
    }

    /**
     * Récupérer une section par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Section> getSectionById(@PathVariable String id) {
        return sectionService.getSectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/sections/{idSection}")
    public ResponseEntity<Section> getSectionWithNested(@PathVariable String idSection) {
        try {
            Section section = sectionService.getSectionWithSousSectionsAndQuestions(idSection);
            return ResponseEntity.ok(section);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @GetMapping("/sections/simple")
    // public ResponseEntity<List<Section>> getSimpleSections() {
    //     List<Section> sections = sectionService.getAllSections();  // Méthode existante, sans fetch
    //     return ResponseEntity.ok(sections);
    // }
}