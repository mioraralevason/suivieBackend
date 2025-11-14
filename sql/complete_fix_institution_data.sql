-- Complete script to create all required data in proper order to satisfy foreign key constraints

-- First, ensure we have utilisateur records
INSERT INTO utilisateur (id_utilisateur, email, mot_de_passe, nom, prenom, cree_le, modifie_le, id_role)
SELECT 'USR001', 'admin@lbcft.mg', '$2y$10$example_hashed_password', 'Administrateur', 'Système', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1'
WHERE NOT EXISTS (SELECT 1 FROM utilisateur WHERE id_utilisateur = 'USR001');

INSERT INTO utilisateur (id_utilisateur, email, mot_de_passe, nom, prenom, cree_le, modifie_le, id_role)
SELECT 'USR002', 'contact@bnm.mg', '$2y$10$example_hashed_password', 'Directeur', 'BNM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2'
WHERE NOT EXISTS (SELECT 1 FROM utilisateur WHERE id_utilisateur = 'USR002');

INSERT INTO utilisateur (id_utilisateur, email, mot_de_passe, nom, prenom, cree_le, modifie_le, id_role)
SELECT 'USR003', 'info@sogem.mg', '$2y$10$example_hashed_password', 'Gestionnaire', 'SOGEM', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2'
WHERE NOT EXISTS (SELECT 1 FROM utilisateur WHERE id_utilisateur = 'USR003');

INSERT INTO utilisateur (id_utilisateur, email, mot_de_passe, nom, prenom, cree_le, modifie_le, id_role)
SELECT 'USR004', 'contact@caamada.mg', '$2y$10$example_hashed_password', 'Avocat', 'Principal', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '2'
WHERE NOT EXISTS (SELECT 1 FROM utilisateur WHERE id_utilisateur = 'USR004');

-- Ensure the institution table has the required columns
ALTER TABLE institution 
ADD COLUMN IF NOT EXISTS denomination_sociale VARCHAR(255) NOT NULL,
ADD COLUMN IF NOT EXISTS nom_commercial VARCHAR(255),
ADD COLUMN IF NOT EXISTS forme_juridique VARCHAR(100),
ADD COLUMN IF NOT EXISTS date_debut_operations DATE,
ADD COLUMN IF NOT EXISTS adresse_siege_social VARCHAR(500),
ADD COLUMN IF NOT EXISTS adresse_activite_principale VARCHAR(500),
ADD COLUMN IF NOT EXISTS adresses_secondaires VARCHAR(1000),
ADD COLUMN IF NOT EXISTS numero_telephone VARCHAR(20),
ADD COLUMN IF NOT EXISTS adresse_email VARCHAR(255),
ADD COLUMN IF NOT EXISTS liste_activites TEXT,
ADD COLUMN IF NOT EXISTS activite_principale VARCHAR(255),
ADD COLUMN IF NOT EXISTS activites_secondaires TEXT;

-- Update existing records to have denomination_sociale if they don't already
UPDATE institution 
SET denomination_sociale = COALESCE(denomination_sociale, nom)
WHERE denomination_sociale IS NULL OR denomination_sociale = '';

-- Insert institution records
INSERT INTO institution (id_institution, denomination_sociale, nom_commercial, forme_juridique, date_debut_operations, adresse_siege_social, adresse_activite_principale, numero_telephone, adresse_email, liste_activites, activite_principale, activites_secondaires, cree_le, modifie_le, id_utilisateur)
SELECT 'INST001', 'Banque Nationale de Madagascar', 'BNM', 'Société Anonyme', '1960-06-26', '123 Avenue Andriamanelo, Antananarivo', '123 Avenue Andriamanelo, Antananarivo', '+261 20 23 456', 'contact@bnm.mg', 'Banque, Épargne, Crédit', 'Banque', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'USR002'
WHERE NOT EXISTS (SELECT 1 FROM institution WHERE id_institution = 'INST001');

INSERT INTO institution (id_institution, denomination_sociale, nom_commercial, forme_juridique, date_debut_operations, adresse_siege_social, adresse_activite_principale, numero_telephone, adresse_email, liste_activites, activite_principale, activites_secondaires, cree_le, modifie_le, id_utilisateur)
SELECT 'INST002', 'Société Générale de Madagascar', 'SOGEM', 'SARL', '1980-03-15', '45 Route de l''Aéroport, Antananarivo', '45 Route de l''Aéroport, Antananarivo', '+261 20 23 789', 'info@sogem.mg', 'Banque, Microfinance', 'Microfinance', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'USR003'
WHERE NOT EXISTS (SELECT 1 FROM institution WHERE id_institution = 'INST002');

INSERT INTO institution (id_institution, denomination_sociale, nom_commercial, forme_juridique, date_debut_operations, adresse_siege_social, adresse_activite_principale, numero_telephone, adresse_email, liste_activites, activite_principale, activites_secondaires, cree_le, modifie_le, id_utilisateur)
SELECT 'INST003', 'Cabinet d''Avocats Associés', 'CAA', 'SCP', '2000-01-10', '67 Rue du Commerce, Antananarivo', '67 Rue du Commerce, Antananarivo', '+261 20 23 321', 'contact@caamada.mg', 'Droit Civil, Droit Commercial, Notariat', 'Droit Commercial', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'USR004'
WHERE NOT EXISTS (SELECT 1 FROM institution WHERE id_institution = 'INST003');

-- Insert EPNFD sectors if they don't exist
INSERT INTO epnfd_sectors (id_sector, libelle, description) VALUES
('A1.1', 'Casinos / jeux de hasard (incl. en ligne)', 'EPNFD sector for casinos and gambling'),
('A1.2', 'Agents immobiliers', 'EPNFD sector for real estate agents'),
('A1.3', 'Négoçiants en métaux précieux / pierres précieuses', 'EPNFD sector for precious metals/gems dealers'),
('A1.4', 'Avocats / Notaires / Juristes indépendants', 'EPNFD sector for legal professionals'),
('A1.5', 'Comptables / Experts-comptables', 'EPNFD sector for accountants'),
('A1.6', 'Fournisseurs de services aux sociétés et fiducies (TCSP)', 'EPNFD sector for trust/company service providers'),
('A1.7', 'Concessionnaires et importateurs/vendeurs de véhicules', 'EPNFD sector for vehicle dealers'),
('A1.8', 'Transporteurs de fonds', 'EPNFD sector for cash transporters')
ON CONFLICT (id_sector) DO NOTHING;

-- Insert activity details if they don't exist
INSERT INTO activity_details (id_activity, libelle, description) VALUES
('B1', 'Casinos/Jeux : Réalisez-vous des opérations réglées en espèces >= 3 000 USD?', 'Cash operations >= 3000 USD for casinos/gambling'),
('B2', 'Casinos/Jeux : Observez-vous du fractionnement d’achats en espèces pour rester sous 3 000 USD?', 'Observing cash transaction structuring under 3000 USD threshold'),
('B3', 'Immobilier : Gérez-vous des fonds d’acquisition pour des transactions immobilières ?', 'Real estate: Managing acquisition funds for property transactions'),
('B4', 'Négoçiants en métaux précieux : Réalisez-vous des opérations réglées en espèces >= 15 000 USD?', 'Precious metals: Cash operations >= 15000 USD'),
('B5', 'Négoçiants en métaux précieux : Observez-vous du fractionnement d’achats en espèces ?', 'Precious metals: Observing cash transaction structuring'),
('B6', 'Juridique : Achat/vente d’immeubles pour le compte de clients', 'Legal services: Buying/selling real estate for clients'),
('B7', 'Juridique : Gestion de fonds, titres ou autres actifs des clients', 'Legal services: Managing client funds, securities, assets'),
('B8', 'Juridique : Gestion de comptes bancaires/d’épargne/de titres pour les clients', 'Legal services: Managing client bank/savings/securities accounts'),
('B9', 'Juridique : Organisation des apports pour création/exploitation/gestion de sociétés', 'Legal services: Organizing capital contributions for company setup/operation/management'),
('B10', 'Juridique : Création/exploitation/gestion de personnes morales ou constructions juridiques', 'Legal services: Creating/operating/managing legal entities or juridical structures'),
('B11', 'Juridique : Achat/vente d’entreprises pour les clients', 'Legal services: Buying/selling businesses for clients'),
('B12', 'Comptables : Réalisez-vous des opérations visées ci-dessus ?', 'Accountants: Performing operations mentioned above'),
('B13', 'TCSP : Constitution de personnes morales ou constructions juridiques pour des clients', 'TCSP: Creating legal entities or juridical structures for clients'),
('B14', 'TCSP : Fourniture de siège social/domiciliation', 'TCSP: Providing registered office services'),
('B15', 'TCSP : Fourniture de dirigeants/administrateurs/actionnariat nominee', 'TCSP: Providing nominee directors/shareholders'),
('B16', 'TCSP : Fourniture d’un fiduciaire / fiduciaire remplaçant / actionnaire fiduciaire', 'TCSP: Providing trustees/substitute trustees/nominee shareholders'),
('B17', 'TCSP : Autres services équivalents pour création/gestion/administration de véhicules juridiques', 'TCSP: Other equivalent services for legal entity creation/management/administration'),
('B18', 'Importateur ou concessionnaire de véhicules (auto, moto, bateau, avion) neufs ou d''occasions', 'Vehicle importers/dealers of new or used vehicles'),
('B19', 'Activités de transports de fonds au niveau national', 'Cash transportation services at national level')
ON CONFLICT (id_activity) DO NOTHING;

-- Now insert the questionnaire responses
INSERT INTO institution_epnfd_responses (id_institution, id_sector, is_applicable) VALUES
('INST001', 'A1.4', FALSE),
('INST001', 'A1.5', FALSE),
('INST002', 'A1.6', TRUE),
('INST003', 'A1.4', TRUE),
('INST003', 'A1.6', FALSE)
ON CONFLICT DO NOTHING;

INSERT INTO institution_activity_responses (id_institution, id_activity, response, justification) VALUES
-- For INST001 (Bank)
('INST001', 'B6', FALSE, 'Not applicable for banking services'),
('INST001', 'B7', FALSE, 'We don''t manage client securities directly'),
('INST001', 'B8', TRUE, 'We do manage client bank accounts'),
-- For INST002 (Microfinance)
('INST002', 'B13', TRUE, 'We provide company formation services'),
('INST002', 'B14', TRUE, 'We provide registered office services'),
('INST002', 'B15', FALSE, 'We don''t provide nominee services'),
-- For INST003 (Law Firm)
('INST003', 'B6', TRUE, 'We handle real estate transactions for clients'),
('INST003', 'B7', TRUE, 'We manage client funds for legal proceedings'),
('INST003', 'B10', TRUE, 'We create legal entities for clients')
ON CONFLICT DO NOTHING;