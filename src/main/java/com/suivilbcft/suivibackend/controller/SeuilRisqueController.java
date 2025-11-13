package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.dto.SeuilRisqueRequest;  
import com.suivilbcft.suivibackend.model.SeuilRisque;
import com.suivilbcft.suivibackend.service.SeuilRisqueService;
import org.springframework.http.HttpStatus;  
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seuils-risque")

public class SeuilRisqueController {

    private final SeuilRisqueService seuilRisqueService;

    public SeuilRisqueController(SeuilRisqueService seuilRisqueService) {
        this.seuilRisqueService = seuilRisqueService;
    }

   
    @GetMapping
    public ResponseEntity<List<SeuilRisque>> getAllSeuilsRisque() {
        List<SeuilRisque> seuils = seuilRisqueService.getAllSeuilsRisque();
        return ResponseEntity.ok(seuils);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeuilRisque> getSeuilRisqueById(@PathVariable String id) {
        return seuilRisqueService.getSeuilRisqueById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   
    @PostMapping
    public ResponseEntity<SeuilRisque> createSeuilRisque(@RequestBody SeuilRisqueRequest request) {
        try {
            SeuilRisque created = seuilRisqueService.createSeuilRisque(request.getTauxMin(), request.getTauxMax(), request.getDescription());
            return ResponseEntity.status(HttpStatus.CREATED).body(created); 
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);  
        }
    }
}