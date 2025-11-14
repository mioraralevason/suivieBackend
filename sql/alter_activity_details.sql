-- Script pour modifier la table activity_details et ajouter la relation avec les secteurs EPNFD

-- Ajouter la colonne id_sector à la table activity_details
ALTER TABLE activity_details 
ADD COLUMN IF NOT EXISTS id_sector VARCHAR(10);

-- Ajouter la contrainte de clé étrangère
ALTER TABLE activity_details 
ADD CONSTRAINT fk_activity_details_sector 
FOREIGN KEY (id_sector) REFERENCES epnfd_sectors(id_sector);

-- Mettre à jour les données existantes pour associer chaque activité à son secteur approprié
UPDATE activity_details SET id_sector = 'A1.1' WHERE id_activity IN ('B1', 'B2');
UPDATE activity_details SET id_sector = 'A1.2' WHERE id_activity = 'B3';
UPDATE activity_details SET id_sector = 'A1.3' WHERE id_activity IN ('B4', 'B5');
UPDATE activity_details SET id_sector = 'A1.4' WHERE id_activity IN ('B6', 'B7', 'B8', 'B9', 'B10', 'B11');
UPDATE activity_details SET id_sector = 'A1.5' WHERE id_activity = 'B12';
UPDATE activity_details SET id_sector = 'A1.6' WHERE id_activity IN ('B13', 'B14', 'B15', 'B16', 'B17');
UPDATE activity_details SET id_sector = 'A1.7' WHERE id_activity = 'B18';
UPDATE activity_details SET id_sector = 'A1.8' WHERE id_activity = 'B19';