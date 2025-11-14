-- Script complet de correction de la base de données

-- Supprimer les éventuels doublons dans la table utilisateur
DELETE FROM utilisateur
WHERE id_utilisateur NOT IN (
    SELECT DISTINCT ON (email) id_utilisateur
    FROM utilisateur
    ORDER BY email, id_utilisateur
);

-- Créer la contrainte d'unicité pour le champ email
ALTER TABLE utilisateur 
DROP CONSTRAINT IF EXISTS uk_utilisateur_email,
ADD CONSTRAINT uk_utilisateur_email UNIQUE (email);

-- Vérifier la structure de la relation entre utilisateur et institution
-- Assurer que la clé étrangère est correctement définie
ALTER TABLE institution 
DROP CONSTRAINT IF EXISTS fk_institution_utilisateur,
ADD CONSTRAINT fk_institution_utilisateur 
FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id_utilisateur);

-- Vérifier les données
SELECT email, COUNT(*) as count
FROM utilisateur
GROUP BY email
HAVING COUNT(*) > 1;

-- Afficher les relations utilisateur-institution
SELECT u.id_utilisateur, u.email, i.id_institution 
FROM utilisateur u
LEFT JOIN institution i ON u.id_utilisateur = i.id_utilisateur
ORDER BY u.email;