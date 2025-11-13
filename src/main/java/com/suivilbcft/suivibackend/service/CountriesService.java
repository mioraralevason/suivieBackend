package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.model.CategoriePays;
import com.suivilbcft.suivibackend.model.Pays;
import com.suivilbcft.suivibackend.repository.CategoriePaysRepository;
import com.suivilbcft.suivibackend.repository.PaysRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountriesService {
    private final PaysRepository paysRepository;
    private final CategoriePaysRepository categoriePaysRepository;

    public CountriesService(PaysRepository paysRepository, CategoriePaysRepository categoriePaysRepository) {
        this.paysRepository = paysRepository;
        this.categoriePaysRepository = categoriePaysRepository;
    }

    public List<Pays> getAllCountries() {
        return paysRepository.findAll();
    }

    // Ajouter ou relier un pays à une catégorie (mappe 'blacklist'/'greylist' vers libelle)
    public Pays addCountry(String name, String code, String listType) {
        String libelleCategorie = listType.equals("blacklist") ? "Liste Noire" : "Liste Grise";
        System.out.println("Recherche catégorie pour : [" + libelleCategorie + "]");

        Optional<CategoriePays> categorieOpt = categoriePaysRepository.findByLibelleIgnoreCase(libelleCategorie);
        System.out.println("Résultat recherche : " + categorieOpt);

        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie non trouvée: " + libelleCategorie);
        }
        CategoriePays categorie = categorieOpt.get();

        Optional<Pays> existingPaysOpt = paysRepository.findByCode(code);
        Pays pays;

        if (existingPaysOpt.isPresent()) {
            System.out.println("Pays existant trouvé pour code " + code + ", mise à jour de la catégorie");
            pays = existingPaysOpt.get();
            pays.setCategoriePays(categorie); 
            pays.setLibelle(name);  
            pays.setModifieLe(LocalDateTime.now());
        } else {
           
            System.out.println("Création nouveau pays pour code " + code);
            pays = new Pays(name, code, categorie);
            pays.setIdPays(code);
            pays.setCreeLe(LocalDateTime.now());
            pays.setModifieLe(LocalDateTime.now());
        }

        System.out.println("Avant save: " + pays.getLibelle() + " (ID: " + pays.getIdPays() + ", Catégorie: " + pays.getCategoriePays().getLibelle() + ")");
        Pays saved = paysRepository.save(pays);
        System.out.println("Après save: " + saved.getLibelle() + " lié à catégorie " + saved.getCategoriePays().getLibelle());

        return saved;
    }

    
    public Pays updateCountry(String idPays, String name, String code, String listType) {
        Optional<Pays> paysOpt = paysRepository.findById(idPays);
        if (paysOpt.isEmpty()) {
            throw new RuntimeException("Pays non trouvé: " + idPays);
        }

        Pays pays = paysOpt.get();
        
     
        if (code != null && !code.equals(pays.getCode())) {
            Optional<Pays> existingPays = paysRepository.findByCode(code);
            if (existingPays.isPresent() && !existingPays.get().getIdPays().equals(idPays)) {
                throw new RuntimeException("Code pays déjà utilisé: " + code);
            }
            pays.setCode(code);
        }

        if (name != null) {
            pays.setLibelle(name);
        }

        if (listType != null) {
            String libelleCategorie = listType.equals("blacklist") ? "Liste Noire" : "Liste Grise";
            Optional<CategoriePays> categorieOpt = categoriePaysRepository.findByLibelleIgnoreCase(libelleCategorie);
            if (categorieOpt.isEmpty()) {
                throw new RuntimeException("Catégorie non trouvée: " + libelleCategorie);
            }
            pays.setCategoriePays(categorieOpt.get());
        }

        pays.setModifieLe(LocalDateTime.now());
        return paysRepository.save(pays);
    }

    public void deleteCountry(String idPays) {
        if (!paysRepository.existsById(idPays)) {
            throw new RuntimeException("Pays non trouvé: " + idPays);
        }
        paysRepository.deleteById(idPays);
    }

    public List<Pays> getCountriesByListType(String listType) {
        String libelle = listType.equals("blacklist") ? "Liste Noire" : "Liste Grise";
        return paysRepository.findByCategoriePaysLibelle(libelle);
    }

   
    public Optional<Pays> getCountryById(String idPays) {
        return paysRepository.findById(idPays);
    }

    public Optional<Pays> getCountryByCode(String code) {
        return paysRepository.findByCode(code);
    }
}