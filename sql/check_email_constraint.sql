-- Vérifier si la contrainte d'unicité est bien présente dans la base de données
SELECT constraint_name, constraint_type
FROM information_schema.table_constraints
WHERE table_name = 'utilisateur' AND constraint_type = 'UNIQUE'
AND column_name IN (
    SELECT column_name 
    FROM information_schema.constraint_column_usage 
    WHERE table_name = 'utilisateur'
);

-- Vérifier s'il existe un index unique sur le champ email
SELECT indexname, indexdef
FROM pg_indexes
WHERE tablename = 'utilisateur'
AND indexdef LIKE '%email%';

-- Vérifier s'il y a des valeurs NULL dans le champ email (ce qui pourrait poser problème)
SELECT COUNT(*) FROM utilisateur WHERE email IS NULL;

-- Essayons de reproduire la requête findByEmail pour voir ce qui se passe exactement
-- Remplacez 'email_test' par l'email que vous utilisez pour tester
-- SELECT * FROM utilisateur WHERE email = 'email_test';