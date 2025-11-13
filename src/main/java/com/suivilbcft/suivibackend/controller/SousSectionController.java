package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.model.SousSection;
import com.suivilbcft.suivibackend.service.SousSectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sous-sections")
 
public class SousSectionController {

    private final SousSectionService sousSectionService;

    public SousSectionController(SousSectionService sousSectionService) {
        this.sousSectionService = sousSectionService;
    }


    @GetMapping
    public ResponseEntity<List<SousSection>> getAllSousSections() {
        List<SousSection> sousSections = sousSectionService.getAllSousSections();
        return ResponseEntity.ok(sousSections);
    }

    @GetMapping("/section/{idSection}")
    public ResponseEntity<List<SousSection>> getSousSectionsBySection(@PathVariable String idSection) {
        List<SousSection> sousSections = sousSectionService.getSousSectionsBySection(idSection);
        return ResponseEntity.ok(sousSections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SousSection> getSousSectionById(@PathVariable String id) {
        return sousSectionService.getSousSectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   
}