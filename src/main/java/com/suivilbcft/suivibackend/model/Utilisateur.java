package com.suivilbcft.suivibackend.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @Column(name = "id_utilisateur", length = 250)
    private String idUtilisateur;

    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String email;

    @Column(name = "mot_de_passe", length = 250, nullable = false)
    private String motDePasse;

    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    @Column(name = "prenom", length =250,nullable =true)
    private String prenom;

    @Column(name = "cree_le")
    private LocalDateTime creeLe = LocalDateTime.now();

    @Column(name = "modifie_le")
    private LocalDateTime modifieLe = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", nullable = false, referencedColumnName = "id_role")
    private Role role;

    // Constructeur sans arguments
    public Utilisateur() {}

    // Constructeur avec tous les champs si nécessaire
    public Utilisateur(String idUtilisateur, String email, String motDePasse, String nom, String prenom, Role role) {
        this.idUtilisateur = idUtilisateur;
        this.email = email;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
    }

    // Getters et setters pour tous les champs
    public String getIdUtilisateur() { return idUtilisateur; }
    public void setIdUtilisateur(String idUtilisateur) { this.idUtilisateur = idUtilisateur; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
