-- Script pour identifier et supprimer les doublons dans la table utilisateur

-- Identifier les emails en double
SELECT email, COUNT(*) as count
FROM utilisateur
GROUP BY email
HAVING COUNT(*) > 1;

-- Supprimer les doublons en gardant le premier enregistrement
-- (Celui avec l'ID le plus petit pour chaque email)
DELETE FROM utilisateur
WHERE id_utilisateur NOT IN (
    SELECT MIN(id_utilisateur)
    FROM utilisateur
    GROUP BY email
);

-- Vérifier qu'il n'y a plus de doublons
SELECT email, COUNT(*) as count
FROM utilisateur
GROUP BY email
HAVING COUNT(*) > 1;