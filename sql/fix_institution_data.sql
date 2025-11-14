-- First, make sure the institution table exists with the correct structure
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

-- Insert sample institution records first
INSERT INTO institution (id_institution, denomination_sociale, nom_commercial, forme_juridique, date_debut_operations, adresse_siege_social, adresse_activite_principale, numero_telephone, adresse_email, liste_activites, activite_principale, activites_secondaires, id_utilisateur)
SELECT 'INST001', 'Banque Nationale de Madagascar', 'BNM', 'Société Anonyme', '1960-06-26', '123 Avenue Andriamanelo, Antananarivo', '123 Avenue Andriamanelo, Antananarivo', '+261 20 23 456', 'contact@bnm.mg', 'Banque, Épargne, Crédit', 'Banque', '', 'USR001'
WHERE NOT EXISTS (SELECT 1 FROM institution WHERE id_institution = 'INST001');

INSERT INTO institution (id_institution, denomination_sociale, nom_commercial, forme_juridique, date_debut_operations, adresse_siege_social, adresse_activite_principale, numero_telephone, adresse_email, liste_activites, activite_principale, activites_secondaires, id_utilisateur)
SELECT 'INST002', 'Société Générale de Madagascar', 'SOGEM', 'SARL', '1980-03-15', '45 Route de l''Aéroport, Antananarivo', '45 Route de l''Aéroport, Antananarivo', '+261 20 23 789', 'info@sogem.mg', 'Banque, Microfinance', 'Microfinance', '', 'USR002'
WHERE NOT EXISTS (SELECT 1 FROM institution WHERE id_institution = 'INST002');

INSERT INTO institution (id_institution, denomination_sociale, nom_commercial, forme_juridique, date_debut_operations, adresse_siege_social, adresse_activite_principale, numero_telephone, adresse_email, liste_activites, activite_principale, activites_secondaires, id_utilisateur)
SELECT 'INST003', 'Cabinet d''Avocats Associés', 'CAA', 'SCP', '2000-01-10', '67 Rue du Commerce, Antananarivo', '67 Rue du Commerce, Antananarivo', '+261 20 23 321', 'contact@caamada.mg', 'Droit Civil, Droit Commercial, Notariat', 'Droit Commercial', '', 'USR003'
WHERE NOT EXISTS (SELECT 1 FROM institution WHERE id_institution = 'INST003');

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