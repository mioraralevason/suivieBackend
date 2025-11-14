-- Script de correction pour la table utilisateur

-- Supprimer la contrainte d'unicité existante si présente
ALTER TABLE IF EXISTS utilisateur DROP CONSTRAINT IF EXISTS uk_utilisateur_email;

-- Supprimer les éventuels doublons en gardant le premier (par ID)
DELETE FROM utilisateur
WHERE id_utilisateur NOT IN (
    SELECT DISTINCT ON (email) id_utilisateur
    FROM utilisateur
    ORDER BY email, id_utilisateur
);

-- Ajouter la contrainte d'unicité 
ALTER TABLE utilisateur ADD CONSTRAINT uk_utilisateur_email UNIQUE (email);

-- Vérifier qu'il n'y a plus de doublons
SELECT email, COUNT(*) as count
FROM utilisateur
GROUP BY email
HAVING COUNT(*) > 1;