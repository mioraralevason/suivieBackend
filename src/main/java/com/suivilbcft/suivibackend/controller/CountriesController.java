package com.suivilbcft.suivibackend.controller;

import com.suivilbcft.suivibackend.model.Pays;
import com.suivilbcft.suivibackend.service.CountriesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries")

public class CountriesController {
    private final CountriesService countriesService;

    public CountriesController(CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping
    public ResponseEntity<List<Pays>> getAllCountries() {
        System.out.println("Fetching all countries");
        return ResponseEntity.ok(countriesService.getAllCountries());
    }

    @GetMapping("/{listType}")
    public ResponseEntity<List<Pays>> getByListType(@PathVariable String listType) {
        System.out.println("Fetching countries by type: " + listType);
        return ResponseEntity.ok(countriesService.getCountriesByListType(listType));
    }

    @PostMapping
    public ResponseEntity<?> addCountry(@RequestBody AddCountryRequest request) {
        System.out.println("Données reçues => " + request.getName() + ", " + request.getCode() + ", " + request.getListType());
        try {
          
            Pays pays = countriesService.addCountry(request.getName(), request.getCode(), request.getListType());
            System.out.println("Pays ajouté : " + pays.getLibelle() + " (ID: " + pays.getIdPays() + ")");
            return ResponseEntity.status(HttpStatus.CREATED).body(pays); 
        } catch (RuntimeException e) {
            e.printStackTrace();
           
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            System.out.println("Erreur ajout pays: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);  
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCountry(@PathVariable String id) {
        try {
            countriesService.deleteCountry(id);
            System.out.println("Pays supprimé: " + id);
            return ResponseEntity.noContent().build();  
        } catch (RuntimeException e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);  // JSON erreur
        }
    }
}

    class AddCountryRequest {
        private String name;
        private String code;
        private String listType;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getListType() { return listType; }
        public void setListType(String listType) { this.listType = listType; }
    }