-- SQL script to remove the questionnaire fields from the institution table
-- These will be stored in the normalized tables instead

-- Remove EPNFD sector columns (A1-A1.8) that were added
ALTER TABLE institution 
DROP COLUMN IF EXISTS epnfd_casinos,
DROP COLUMN IF EXISTS epnfd_agents_immobiliers,
DROP COLUMN IF EXISTS epnfd_negociants_materiaux_precieux,
DROP COLUMN IF EXISTS epnfd_avocats_notaires,
DROP COLUMN IF EXISTS epnfd_comptables,
DROP COLUMN IF EXISTS epnfd_tcsp,
DROP COLUMN IF EXISTS epnfd_concessionnaires_vehicules,
DROP COLUMN IF EXISTS epnfd_transporteurs_fonds;

-- Remove activity detail columns (B1-B19) that were added 
ALTER TABLE institution 
DROP COLUMN IF EXISTS act_b1_operations_espaces_3000usd,
DROP COLUMN IF EXISTS act_b2_fractionnement_espaces_3000usd,
DROP COLUMN IF EXISTS act_b3_fonds_acquisition_immobilier,
DROP COLUMN IF EXISTS act_b4_operations_espaces_15000usd,
DROP COLUMN IF EXISTS act_b5_fractionnement_espaces_seuil,
DROP COLUMN IF EXISTS act_b6_achat_vente_immeubles,
DROP COLUMN IF EXISTS act_b7_gestion_actifs_clients,
DROP COLUMN IF EXISTS act_b8_gestion_comptes_clients,
DROP COLUMN IF EXISTS act_b9_organisation_apports,
DROP COLUMN IF EXISTS act_b10_creation_gestion_entites,
DROP COLUMN IF EXISTS act_b11_achat_vente_entreprises,
DROP COLUMN IF EXISTS act_b12_operations_comptables,
DROP COLUMN IF EXISTS act_b13_constitution_entites_juridiques,
DROP COLUMN IF EXISTS act_b14_fourniture_siege_social,
DROP COLUMN IF EXISTS act_b15_fourniture_dirigeants_nominees,
DROP COLUMN IF EXISTS act_b16_fiduciaire_actionnaire_fiduciaire,
DROP COLUMN IF EXISTS act_b17_autres_services_equivalents,
DROP COLUMN IF EXISTS act_b18_importateur_concessionnaire_vehicules,
DROP COLUMN IF EXISTS act_b19_transports_fonds_national;

-- Note: Keep the basic institution information columns that are core to the institution entity
-- These are: denomination_sociale, nom_commercial, forme_juridique, date_debut_operations,
--           adresse_siege_social, adresse_activite_principale, adresses_secondaires,
--           numero_telephone, adresse_email, liste_activites, activite_principale, activites_secondaires
-- These columns are part of the core institution data and should remain

-- Update the denomination_sociale to use the name value if denomination_sociale is empty
-- This maintains data integrity for the core institution name field
UPDATE institution 
SET denomination_sociale = COALESCE(denomination_sociale, nom) 
WHERE denomination_sociale IS NULL OR denomination_sociale = '';