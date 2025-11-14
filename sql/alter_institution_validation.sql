-- Script pour ajouter les colonnes de validation et de complétion des informations

-- Ajouter les colonnes à la table institution
ALTER TABLE institution 
ADD COLUMN IF NOT EXISTS validated_at TIMESTAMP,
ADD COLUMN IF NOT EXISTS completed_info_at TIMESTAMP;

-- Mise à jour des institutions existantes pour indiquer que les informations doivent être complétées
-- Si l'institution n'a pas de denomination_sociale ou un champ critique vide, on considère que les infos ne sont pas complétées
UPDATE institution 
SET completed_info_at = NULL
WHERE denomination_sociale IS NULL 
   OR denomination_sociale = ''
   OR adresse_siege_social IS NULL 
   OR numero_telephone IS NULL 
   OR adresse_email IS NULL;

-- Si une institution a tous les champs critiques remplis, on peut considérer que c'est complété
-- mais on va laisser completed_info_at à NULL pour forcer la validation via l'application
UPDATE institution 
SET completed_info_at = CURRENT_TIMESTAMP
WHERE denomination_sociale IS NOT NULL AND denomination_sociale != ''
  AND adresse_siege_social IS NOT NULL
  AND numero_telephone IS NOT NULL  
  AND adresse_email IS NOT NULL
  AND date_debut_operations IS NOT NULL;