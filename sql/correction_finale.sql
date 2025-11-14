/**
 * SOLUTION DÉFINITIVE : Vérifier les doublons et corriger le problème de base de données
 * 
 * Exécutez les commandes SQL suivantes pour corriger votre base de données :
 */
 
-- 1. Supprimer les doublons potentiels dans utilisateur
DELETE FROM utilisateur
WHERE id_utilisateur NOT IN (
    SELECT DISTINCT ON (email) id_utilisateur
    FROM utilisateur
    ORDER BY email, id_utilisateur
);

-- 2. Supprimer les institutions orphelines (liées à des utilisateurs supprimés)
DELETE FROM institution 
WHERE id_utilisateur NOT IN (SELECT id_utilisateur FROM utilisateur);

-- 3. Vérifier qu'il n'y a plus de doublons
SELECT email, COUNT(*) as count
FROM utilisateur
GROUP BY email
HAVING COUNT(*) > 1;

-- 4. Créer une contrainte d'unicité physique dans la base de données
ALTER TABLE utilisateur 
DROP CONSTRAINT IF EXISTS uk_utilisateur_email,
ADD CONSTRAINT uk_utilisateur_email UNIQUE (email);

-- 5. Si les contraintes JPA ne sont pas suffisantes, créer une contrainte physique
-- pour empêcher les doublons même si les annotations JPA ne sont pas appliquées

-- 6. Vérifier les relations
SELECT u.id_utilisateur, u.email, i.id_institution 
FROM utilisateur u
LEFT JOIN institution i ON u.id_utilisateur = i.id_utilisateur
ORDER BY u.id_utilisateur;