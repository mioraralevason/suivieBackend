// src/main/java/com/suivilbcft/suivibackend/controller/RegleScoringController.java
package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.dto.RegleScoringDTO;
import com.suivilbcft.suivibackend.model.RegleScoring;
import com.suivilbcft.suivibackend.service.RegleScoringService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scoring/regles")  // CHANGÉ : /api/scoring/regles
public class RegleScoringController {

    @Autowired
    private RegleScoringService regleScoringService;

    @PostMapping
    public ResponseEntity<?> createRegle(@Valid @RequestBody RegleScoringDTO dto) {
        try {
            RegleScoring created = regleScoringService.createRegle(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/question/{idQuestion}")
    public ResponseEntity<List<RegleScoring>> getReglesByQuestion(@PathVariable String idQuestion) {
        return ResponseEntity.ok(regleScoringService.getReglesByQuestion(idQuestion));
    }

    @PutMapping("/{idRegle}")
    public ResponseEntity<?> updateRegle(@PathVariable String idRegle, @Valid @RequestBody RegleScoringDTO dto) {
        try {
            RegleScoring updated = regleScoringService.updateRegle(idRegle, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{idRegle}")
    public ResponseEntity<Void> deleteRegle(@PathVariable String idRegle) {
        try {
            regleScoringService.deleteRegle(idRegle);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}