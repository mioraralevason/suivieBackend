-- SQL migration script to normalize the institution questionnaire data
-- This creates separate tables for EPNFD sectors and activity responses

-- First, update the institution table to only contain basic information (I2-I13)
-- Add the fields that are core to the institution entity
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
ADD COLUMN IF NOT EXISTS liste_activites TEXT, -- Changed to TEXT to allow JSON or separated values
ADD COLUMN IF NOT EXISTS activite_principale VARCHAR(255),
ADD COLUMN IF NOT EXISTS activites_secondaires TEXT;

-- Update existing data to match the new structure
UPDATE institution
SET denomination_sociale = COALESCE(denomination_sociale, nom)
WHERE denomination_sociale IS NULL OR denomination_sociale = '';

-- Create EPNFD sectors reference table (A1-A1.8)
CREATE TABLE IF NOT EXISTS epnfd_sectors (
    id_sector VARCHAR(10) PRIMARY KEY,
    libelle VARCHAR(255) NOT NULL,
    description TEXT
);

-- Insert EPNFD sectors data
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

-- Create junction table for institution EPNFD sector responses
CREATE TABLE IF NOT EXISTS institution_epnfd_responses (
    id_institution VARCHAR(50),
    id_sector VARCHAR(10),
    is_applicable BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_institution, id_sector),
    FOREIGN KEY (id_institution) REFERENCES institution(id_institution) ON DELETE CASCADE,
    FOREIGN KEY (id_sector) REFERENCES epnfd_sectors(id_sector)
);

-- Create activity details reference table (B1-B19)
CREATE TABLE IF NOT EXISTS activity_details (
    id_activity VARCHAR(10) PRIMARY KEY,
    id_sector VARCHAR(10),
    libelle VARCHAR(255) NOT NULL,
    description TEXT,
    FOREIGN KEY (id_sector) REFERENCES epnfd_sectors(id_sector)
);

-- Insert activity details data with associated sectors
INSERT INTO activity_details (id_activity, id_sector, libelle, description) VALUES
('B1', 'A1.1', 'Casinos/Jeux : Réalisez-vous des opérations réglées en espèces >= 3 000 USD?', 'Cash operations >= 3000 USD for casinos/gambling'),
('B2', 'A1.1', 'Casinos/Jeux : Observez-vous du fractionnement d’achats en espèces pour rester sous 3 000 USD?', 'Observing cash transaction structuring under 3000 USD threshold'),
('B3', 'A1.2', 'Immobilier : Gérez-vous des fonds d’acquisition pour des transactions immobilières ?', 'Real estate: Managing acquisition funds for property transactions'),
('B4', 'A1.3', 'Négoçiants en métaux précieux : Réalisez-vous des opérations réglées en espèces >= 15 000 USD?', 'Precious metals: Cash operations >= 15000 USD'),
('B5', 'A1.3', 'Négoçiants en métaux précieux : Observez-vous du fractionnement d’achats en espèces ?', 'Precious metals: Observing cash transaction structuring'),
('B6', 'A1.4', 'Juridique : Achat/vente d’immeubles pour le compte de clients', 'Legal services: Buying/selling real estate for clients'),
('B7', 'A1.4', 'Juridique : Gestion de fonds, titres ou autres actifs des clients', 'Legal services: Managing client funds, securities, assets'),
('B8', 'A1.4', 'Juridique : Gestion de comptes bancaires/d’épargne/de titres pour les clients', 'Legal services: Managing client bank/savings/securities accounts'),
('B9', 'A1.4', 'Juridique : Organisation des apports pour création/exploitation/gestion de sociétés', 'Legal services: Organizing capital contributions for company setup/operation/management'),
('B10', 'A1.4', 'Juridique : Création/exploitation/gestion de personnes morales ou constructions juridiques', 'Legal services: Creating/operating/managing legal entities or juridical structures'),
('B11', 'A1.4', 'Juridique : Achat/vente d’entreprises pour les clients', 'Legal services: Buying/selling businesses for clients'),
('B12', 'A1.5', 'Comptables : Réalisez-vous des opérations visées ci-dessus ?', 'Accountants: Performing operations mentioned above'),
('B13', 'A1.6', 'TCSP : Constitution de personnes morales ou constructions juridiques pour des clients', 'TCSP: Creating legal entities or juridical structures for clients'),
('B14', 'A1.6', 'TCSP : Fourniture de siège social/domiciliation', 'TCSP: Providing registered office services'),
('B15', 'A1.6', 'TCSP : Fourniture de dirigeants/administrateurs/actionnariat nominee', 'TCSP: Providing nominee directors/shareholders'),
('B16', 'A1.6', 'TCSP : Fourniture d’un fiduciaire / fiduciaire remplaçant / actionnaire fiduciaire', 'TCSP: Providing trustees/substitute trustees/nominee shareholders'),
('B17', 'A1.6', 'TCSP : Autres services équivalents pour création/gestion/administration de véhicules juridiques', 'TCSP: Other equivalent services for legal entity creation/management/administration'),
('B18', 'A1.7', 'Importateur ou concessionnaire de véhicules (auto, moto, bateau, avion) neufs ou d''occasions', 'Vehicle importers/dealers of new or used vehicles'),
('B19', 'A1.8', 'Activités de transports de fonds au niveau national', 'Cash transportation services at national level')
ON CONFLICT (id_activity) DO NOTHING;

-- Create junction table for institution activity responses
CREATE TABLE IF NOT EXISTS institution_activity_responses (
    id_institution VARCHAR(50),
    id_activity VARCHAR(10),
    response BOOLEAN,
    justification TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_institution, id_activity),
    FOREIGN KEY (id_institution) REFERENCES institution(id_institution) ON DELETE CASCADE,
    FOREIGN KEY (id_activity) REFERENCES activity_details(id_activity)
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_institution_epnfd ON institution_epnfd_responses(id_institution, id_sector);
CREATE INDEX IF NOT EXISTS idx_institution_activity ON institution_activity_responses(id_institution, id_activity);

-- Sample data for EPNFD responses
INSERT INTO institution_epnfd_responses (id_institution, id_sector, is_applicable) VALUES
('INST001', 'A1.4', FALSE),
('INST001', 'A1.5', FALSE),
('INST002', 'A1.6', TRUE),
('INST003', 'A1.4', TRUE),
('INST003', 'A1.6', FALSE);

-- Sample data for activity responses
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
('INST003', 'B10', TRUE, 'We create legal entities for clients');



