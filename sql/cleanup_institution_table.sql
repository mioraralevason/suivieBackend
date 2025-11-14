-- SQL script to completely clean up the institution table from questionnaire fields
-- and prepare it for the normalized schema approach

-- Remove ALL questionnaire columns that were added to the institution table
ALTER TABLE institution 
-- Remove EPNFD sector columns (A1-A1.8)
DROP COLUMN IF EXISTS epnfd_casinos,
DROP COLUMN IF EXISTS epnfd_agents_immobiliers,
DROP COLUMN IF EXISTS epnfd_negociants_materiaux_precieux,
DROP COLUMN IF EXISTS epnfd_avocats_notaires,
DROP COLUMN IF EXISTS epnfd_comptables,
DROP COLUMN IF EXISTS epnfd_tcsp,
DROP COLUMN IF EXISTS epnfd_concessionnaires_vehicules,
DROP COLUMN IF EXISTS epnfd_transporteurs_fonds,
-- Remove activity detail columns (B1-B19) 
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

-- Keep only the core institution information columns (I2-I13)
-- These are appropriate to keep in the main institution table:
-- denomination_sociale, nom_commercial, forme_juridique, date_debut_operations,
-- adresse_siege_social, adresse_activite_principale, adresses_secondaires,
-- numero_telephone, adresse_email, liste_activites, activite_principale, activites_secondaires

-- Ensure the denomination_sociale field has a value (use the old name field if needed)
UPDATE institution 
SET denomination_sociale = COALESCE(denomination_sociale, nom) 
WHERE denomination_sociale IS NULL OR denomination_sociale = '';

-- If the 'nom' field is no longer needed, you can drop it as well:
-- ALTER TABLE institution DROP COLUMN IF EXISTS nom;