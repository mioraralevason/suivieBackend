-- Rôles
INSERT INTO role (id_role, libelle) VALUES ('role-admin', 'admin');
INSERT INTO role (id_role, libelle) VALUES ('role-institution', 'institution');
INSERT INTO role (id_role, libelle) VALUES ('role-superviseur', 'superviseur');

--categoties pays
insert into categoriepays(id_categorie_pays,libelle) values('cat-1','Liste Noire'),('cat-2','Liste Grise');


--pays
INSERT INTO pays (id_pays, libelle, code, id_categorie_pays)
VALUES
('AF', 'Afghanistan', 'AF', NULL),
('BB', 'Barbade', 'BB', NULL),
('BI', 'Burundi', 'BI', NULL),
('CD', 'République Démocratique du Congo', 'CD', NULL),
('CU', 'Cuba', 'CU', NULL),
('IR', 'Iran', 'IR', NULL),
('KP', 'Corée du Nord', 'KP', NULL),
('LY', 'Libye', 'LY', NULL),
('MM', 'Myanmar', 'MM', NULL),
('SO', 'Somalie', 'SO', NULL),
('SD', 'Soudan', 'SD', NULL),
('SY', 'Syrie', 'SY', NULL),
('VE', 'Venezuela', 'VE', NULL),
('YE', 'Yémen', 'YE', NULL);


--seuil de risque
INSERT INTO seuil_risque (id_seuil, taux_min, taux_max, description)
VALUES
('SR01', 0.00, 40.00, 'Risque faible'),
('SR02', 41.00, 70.00, 'Risque modéré'),
('SR03', 71.00, 100.00, 'Risque élevé');



--categoties
INSERT INTO categorie (id_categorie, libelle) VALUES
('CAT01', 'Institutions financières'),
('CAT02', 'Professionnels de l''immobilier'),
('CAT03', 'Professions juridiques et comptables'),
('CAT04', 'Négociants en métaux et pierres précieux'),
('CAT05', 'Concessionnaires et importateurs de véhicules'),
('CAT06', 'Casinos et jeux d''argent'),
('CAT07', 'Vente de biens et services'),
('CAT08', 'Autres produits ou services à risques');


--activités
-- 1. Institutions financières
INSERT INTO activite (id_activite, libelle, id_categorie) VALUES
('ACT001', 'Banques et établissements de crédit', 'CAT01'),
('ACT002', 'Institutions de microfinance', 'CAT01'),
('ACT003', 'Compagnies d''assurance et de réassurance', 'CAT01'),
('ACT004', 'Sociétés de transfert d''argent', 'CAT01'),
('ACT005', 'Sociétés de gestion de portefeuille ou d''investissement', 'CAT01'),
('ACT006', 'Changeurs agréés', 'CAT01');

-- 2. Professionnels de l’immobilier
INSERT INTO activite (id_activite, libelle, id_categorie) VALUES
('ACT007', 'Agences immobilières', 'CAT02'),
('ACT008', 'Promoteurs immobiliers', 'CAT02'),
('ACT009', 'Notaires en transactions immobilières', 'CAT02'),
('ACT010', 'Courtiers en immobilier', 'CAT02'),
('ACT011', 'Gestionnaires de patrimoine immobilier', 'CAT02');

-- 3. Professions juridiques et comptables
INSERT INTO activite (id_activite, libelle, id_categorie) VALUES
('ACT012', 'Avocats', 'CAT03'),
('ACT013', 'Notaires', 'CAT03'),
('ACT014', 'Experts-comptables', 'CAT03'),
('ACT015', 'Commissaires aux comptes', 'CAT03'),
('ACT016', 'Conseillers fiscaux', 'CAT03'),
('ACT017', 'Cabinets de conseil juridique', 'CAT03');

-- 4. Négociants en métaux et pierres précieux
INSERT INTO activite (id_activite, libelle, id_categorie) VALUES
('ACT018', 'Bijouteries', 'CAT04'),
('ACT019', 'Marchands d''or et de pierres précieuses', 'CAT04'),
('ACT020', 'Antiquaires', 'CAT04'),
('ACT021', 'Galeries d''art', 'CAT04'),
('ACT022', 'Marchands d''objets de collection', 'CAT04');

-- 5. Concessionnaires et importateurs de véhicules
INSERT INTO activite (id_activite, libelle, id_categorie) VALUES
('ACT023', 'Concessionnaires automobiles', 'CAT05'),
('ACT024', 'Importateurs de véhicules', 'CAT05'),
('ACT025', 'Vente de voitures de luxe', 'CAT05'),
('ACT026', 'Location de véhicules haut de gamme', 'CAT05');

-- 6. Casinos et jeux d’argent
INSERT INTO activite (id_activite, libelle, id_categorie) VALUES
('ACT027', 'Casinos physiques', 'CAT06'),
('ACT028', 'Casinos en ligne', 'CAT06'),
('ACT029', 'Salles de jeux', 'CAT06'),
('ACT030', 'Loteries et jeux à mise réelle', 'CAT06'),
('ACT031', 'Paris sportifs', 'CAT06');

-- 7. Vente de biens et services
INSERT INTO activite (id_activite, libelle, id_categorie) VALUES
('ACT032', 'Commerces de gros', 'CAT07'),
('ACT033', 'Commerces de détail', 'CAT07'),
('ACT034', 'Prestataires de services', 'CAT07'),
('ACT035', 'Vente de produits de construction', 'CAT07'),
('ACT036', 'Vente d''équipements électroniques', 'CAT07');

-- 8. Autres produits ou services à risques
INSERT INTO activite (id_activite, libelle, id_categorie) VALUES
('ACT037', 'Vente d''œuvres d''art et objets anciens', 'CAT08'),
('ACT038', 'Transport de fonds ou valeurs', 'CAT08'),
('ACT039', 'Activités liées aux crypto-monnaies', 'CAT08'),
('ACT040', 'Sociétés de consultance financière non régulées', 'CAT08'),
('ACT041', 'Plateformes en ligne manipulant des paiements électroniques', 'CAT08');

-- section:
INSERT INTO section (id_section, libelle)
VALUES
('SEC01', 'Institution'),
('SEC02', 'Clientèle'),
('SEC03', 'Produits et services'),
('SEC04', 'Canaux de distribution'),
('SEC05', 'Géographie'),
('SEC06', 'Dispositif de LBC-FT');

--sous sections

INSERT INTO sous_section (id_sous_section, libelle, id_section)
VALUES
-- SEC01 : Institution
('SSC01A', 'Renseignements sur l''institution', 'SEC01'),
('SSC01B', 'Identification du secteur (EPNFD)', 'SEC01'),
('SSC01C', 'Details des activites', 'SEC01'),

-- SEC02 : Clientele
('SSC02A', 'Donnees generales sur la clientele', 'SEC02'),
('SSC02B', 'Profil de risque', 'SEC02'),
('SSC02C', 'Declarations et incidents', 'SEC02'),

-- SEC03 : Produits et services
('SSC03A', 'Vente de biens et de valeurs', 'SEC03'),
('SSC03B', 'Secteur immobilier', 'SEC03'),
('SSC03C', 'Negociants en metaux et pierres precieux', 'SEC03'),
('SSC03D', 'Concessionnaires et importateurs de vehicules', 'SEC03'),
('SSC03E', 'Autres produits ou services a risques', 'SEC03'),

-- SEC04 : Canaux de distribution
('SSC04A', 'Recours a des apporteurs d''affaires', 'SEC04'),
('SSC04B', 'Canaux de distribution utilises', 'SEC04'),
('SSC04C', 'Paiement en espece', 'SEC04'),

-- SEC05 : Geographie
('SSC05A', 'Exposition geographique', 'SEC05'),

-- SEC06 : Dispositif de LCB-FT
('SSC06A', 'Enregistrement de la societe', 'SEC06'),
('SSC06B', 'Gouvernance et organisation', 'SEC06'),
('SSC06C', 'Evaluation des risques', 'SEC06'),
('SSC06D', 'Politique et procedures de LBC/FT', 'SEC06'),
('SSC06E', 'Formation et sensibilisation', 'SEC06'),
('SSC06F', 'Audit et revue independante', 'SEC06');

INSERT INTO sous_section (id_sous_section, libelle, id_section)
VALUES ('SSC06G', 'Déclaration et coopération', 'SEC06');
--type de reponse
INSERT INTO type_reponse (id_type_reponse, type) VALUES 
('TP01', 'Boolean'),
('TP02', 'Choix multiple'),
('TP03', 'Choix simple'),
('TP04', 'Intervalle'),
('TP05', 'Pourcentage'),
('TP06', 'Date'),
('TP07', 'Intervalle de dates'),
('TP08', 'Texte court'),
('TP09', 'Texte long'),
('TP10', 'Nombre entier'),
('TP11', 'Nombre decimal'); 



--question
{
  "label": "Exemple de question booléenne",
  "definition": "Cette question vérifie une conformité simple.",
  "type": "boolean",
  "axisId": "SSC01A",
  "required": false,
  "justificationRequired": false,
  "commentRequired": false,
  "notes": "Notes optionnelles pour cette question"
}

{
  "label": "L’institution dispose-t-elle d’un fichier client à jour ?",
  "definition": "Cette question vise à vérifier si l’institution maintient une base de données client actualisée régulièrement..",
  "type": "boolean",
  "axisId": "SSC02A",
  "required": false,
  "justificationRequired": false,
  "commentRequired": false,
  "notes":"Une base client à jour est essentielle pour identifier les risques liés à la clientèle.'"
}



-- FACTEURS DE RISQUE (RI)
INSERT INTO facteur_risque (id_facteur, libelle, poids, id_section) VALUES
('FR01', 'Clients', 0.25, 'SEC02'),
('FR02', 'Produits/Services', 0.25, 'SEC03'),
('FR03', 'Transactions', 0.20, 'SEC03'),
('FR04', 'Géographie', 0.15, 'SEC05'),
('FR05', 'Canaux', 0.10, 'SEC04'),
('FR06', 'Spécifique secteur', 0.05, 'SEC01');

-- DOMAINES DE CONTRÔLE (SC)

INSERT INTO domaine_controle (id_domaine, libelle, poids, id_section) VALUES
('DC01', 'CDD / EDD + BE', 0.25, 'SEC06'),
('DC02', 'TM + STR', 0.25, 'SEC06'),
('DC03', 'SFC + PPE', 0.20, 'SEC06'),
('DC04', 'Gouvernance', 0.10, 'SEC06'),
('DC05', 'Audit', 0.10, 'SEC06'),
('DC06', 'Formation', 0.05, 'SEC06'),
('DC07', 'IT / Externalisation', 0.05, 'SEC06');


-- exemple d'une question avec regle de notation
-- [
--     {
--         "idRegleNotation": "365f8fd3",
--         "question": {
--             "idQuestion": "32de3503-fe27-4608-a3ed-2b5e6643b4cf",
--             "libelle": "location ",
--             "definition": "Lieu",
--             "exigeDocument": true,
--             "justificationRequired": false,
--             "commentRequired": false,
--             "notes": "blabla",
--             "creeLe": "2025-11-10T08:56:21.24109",
--             "modifieLe": "2025-11-10T08:56:21.24109",
--             "typeReponse": {
--                 "idTypeReponse": "TP01",
--                 "type": "Boolean",
--                 "creeLe": null,
--                 "modifieLe": null
--             }
--         },
--         "valeur": "{\"true\":true,\"truePoints\":1,\"false\":false,\"falsePoints\":2}",
--         "points": 1.0,
--         "creeLe": "2025-11-10T08:56:37.088684",
--         "modifieLe": "2025-11-10T08:56:37.088684"
--     }
nvm use 22
-- ]
