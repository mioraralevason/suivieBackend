-- Vérifier les contraintes uniques sur la table utilisateur
SELECT tc.table_name, tc.constraint_name, tc.constraint_type, kcu.column_name
FROM information_schema.table_constraints AS tc
JOIN information_schema.key_column_usage AS kcu
  ON tc.constraint_name = kcu.constraint_name
  AND tc.table_name = kcu.table_name
WHERE tc.table_name = 'utilisateur' 
  AND tc.constraint_type = 'UNIQUE';

-- Alternative: Vérifier toutes les contraintes
SELECT constraint_name, constraint_type
FROM information_schema.table_constraints
WHERE table_name = 'utilisateur';

-- Vérifier si la colonne email existe
SELECT column_name, is_nullable, data_type, column_default
FROM information_schema.columns
WHERE table_name = 'utilisateur' AND column_name = 'email';

-- Vérifier s'il y a des doublons dans la base de données
SELECT email, COUNT(*) as count
FROM utilisateur
GROUP BY email
HAVING COUNT(*) > 1;


SELECT 
         u.id_utilisateur, 
         u.email, 
         COUNT(i.id_institution) as institution_count
     FROM utilisateur u
     LEFT JOIN institution i ON u.id_utilisateur = i.id_utilisateur
     GROUP BY u.id_utilisateur, u.email
     HAVING COUNT(i.id_institution) > 1
    ORDER BY u.email;

SELECT u.id_utilisateur, u.email, u.id_role, i.id_institution, i.denomination_sociale
    FROM utilisateur u
    LEFT JOIN institution i ON u.id_utilisateur = i.id_utilisateur
    ORDER BY u.email;
