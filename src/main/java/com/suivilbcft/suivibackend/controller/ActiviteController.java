package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.model.Activite;
import com.suivilbcft.suivibackend.service.ActiviteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
 
public class ActiviteController {

    private final ActiviteService activiteService;

    public ActiviteController(ActiviteService activiteService) {
        this.activiteService = activiteService;
    }

   
    @GetMapping
    public ResponseEntity<List<Activite>> getAllActivites() {
        List<Activite> activities = activiteService.getAllActivites();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Activite> getActiviteById(@PathVariable String id) {
        return activiteService.getActiviteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   
    @GetMapping("/categorie/{idCategorie}")
    public ResponseEntity<List<Activite>> getActivitesByCategorie(@PathVariable String idCategorie) {
        List<Activite> activities = activiteService.getActivitesByCategorie(idCategorie);
        return ResponseEntity.ok(activities);
    }
}