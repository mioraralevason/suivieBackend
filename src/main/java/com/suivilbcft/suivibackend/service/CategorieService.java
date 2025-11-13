package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.model.Categorie;
import com.suivilbcft.suivibackend.repository.CategorieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    /**
     * Créer une nouvelle catégorie
     */
    public Categorie createCategorie(String libelle) {
        if (categorieRepository.findByLibelleIgnoreCase(libelle).isPresent()) {
            throw new RuntimeException("Catégorie avec libelle '" + libelle + "' existe déjà");
        }

        String idCategorie = UUID.randomUUID().toString().substring(0, 13);  // Trunc pour VARCHAR(50)
        Categorie categorie = new Categorie(idCategorie, libelle);
        categorie.setCreeLe(LocalDateTime.now());
        categorie.setModifieLe(LocalDateTime.now());
        return categorieRepository.save(categorie);
    }

    /**
     * Récupérer toutes les catégories
     */
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    /**
     * Récupérer une catégorie par ID
     */
    public Optional<Categorie> getCategorieById(String idCategorie) {
        return categorieRepository.findById(idCategorie);
    }

    /**
     * Récupérer une catégorie par libelle
     */
    public Optional<Categorie> getCategorieByLibelle(String libelle) {
        return categorieRepository.findByLibelleIgnoreCase(libelle);
    }

    /**
     * Mettre à jour une catégorie
     */
    public Categorie updateCategorie(String idCategorie, String libelle) {
        Optional<Categorie> categorieOpt = categorieRepository.findById(idCategorie);
        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie non trouvée: " + idCategorie);
        }

        if (!categorieRepository.findByLibelleIgnoreCase(libelle).map(c -> c.getIdCategorie().equals(idCategorie)).orElse(true)) {
            throw new RuntimeException("Libelle '" + libelle + "' existe déjà pour une autre catégorie");
        }

        Categorie categorie = categorieOpt.get();
        categorie.setLibelle(libelle);
        categorie.setModifieLe(LocalDateTime.now());
        return categorieRepository.save(categorie);
    }

    /**
     * Supprimer une catégorie (seulement si pas d'activités liées - nécessite injection ActiviteRepository si check)
     */
    public void deleteCategorie(String idCategorie) {
        Optional<Categorie> categorieOpt = categorieRepository.findById(idCategorie);
        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie non trouvée: " + idCategorie);
        }

        // Note: Pour check activités liées, injectez ActiviteRepository si besoin
        // Ici, on suppose pas de check pour simplicité; ajoutez si requis

        categorieRepository.deleteById(idCategorie);
    }
}