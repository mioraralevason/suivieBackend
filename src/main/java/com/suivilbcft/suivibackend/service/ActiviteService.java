package com.suivilbcft.suivibackend.service;

import com.suivilbcft.suivibackend.model.Activite;
import com.suivilbcft.suivibackend.model.Categorie;
import com.suivilbcft.suivibackend.repository.ActiviteRepository;
import com.suivilbcft.suivibackend.repository.CategorieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ActiviteService {

    private final ActiviteRepository activiteRepository;
    private final CategorieRepository categorieRepository;

    public ActiviteService(ActiviteRepository activiteRepository, CategorieRepository categorieRepository) {
        this.activiteRepository = activiteRepository;
        this.categorieRepository = categorieRepository;
    }

    /**
     * Créer une nouvelle activité
     */
    public Activite createActivite(String libelle, String idCategorie) {
        Optional<Categorie> categorieOpt = categorieRepository.findById(idCategorie);
        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie non trouvée: " + idCategorie);
        }

        if (activiteRepository.findByLibelleContainingIgnoreCase(libelle).stream()
                .anyMatch(a -> a.getLibelle().equalsIgnoreCase(libelle))) {
            throw new RuntimeException("Activité avec libelle '" + libelle + "' existe déjà");
        }

        String idActivite = UUID.randomUUID().toString();
        Activite activite = new Activite(idActivite, libelle, categorieOpt.get());
        activite.setCreeLe(LocalDateTime.now());
        activite.setModifieLe(LocalDateTime.now());
        return activiteRepository.save(activite);
    }

    /**
     * Récupérer toutes les activités
     */
    public List<Activite> getAllActivites() {
        return activiteRepository.findAll();
    }

    /**
     * Récupérer les activités par catégorie
     */
    public List<Activite> getActivitesByCategorie(String idCategorie) {
        Optional<Categorie> categorieOpt = categorieRepository.findById(idCategorie);
        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie non trouvée: " + idCategorie);
        }
        return activiteRepository.findByCategorie(categorieOpt.get());
    }

    /**
     * Récupérer une activité par ID
     */
    public Optional<Activite> getActiviteById(String idActivite) {
        return activiteRepository.findById(idActivite);
    }

    /**
     * Mettre à jour une activité
     */
    public Activite updateActivite(String idActivite, String libelle, String idCategorie) {
        Optional<Activite> activiteOpt = activiteRepository.findById(idActivite);
        if (activiteOpt.isEmpty()) {
            throw new RuntimeException("Activité non trouvée: " + idActivite);
        }

        Optional<Categorie> categorieOpt = categorieRepository.findById(idCategorie);
        if (categorieOpt.isEmpty()) {
            throw new RuntimeException("Catégorie non trouvée: " + idCategorie);
        }

        if (!activiteRepository.findByLibelleContainingIgnoreCase(libelle).stream()
                .filter(a -> !a.getIdActivite().equals(idActivite))
                .anyMatch(a -> a.getLibelle().equalsIgnoreCase(libelle))) {
            throw new RuntimeException("Libelle '" + libelle + "' existe déjà pour une autre activité");
        }

        Activite activite = activiteOpt.get();
        activite.setLibelle(libelle);
        activite.setCategorie(categorieOpt.get());
        activite.setModifieLe(LocalDateTime.now());
        return activiteRepository.save(activite);
    }

    /**
     * Supprimer une activité
     */
    public void deleteActivite(String idActivite) {
        if (!activiteRepository.existsById(idActivite)) {
            throw new RuntimeException("Activité non trouvée: " + idActivite);
        }
        activiteRepository.deleteById(idActivite);
    }
}