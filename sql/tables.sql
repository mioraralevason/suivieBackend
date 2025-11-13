CREATE TABLE role(
   id_role VARCHAR(250) ,
   libelle VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_role)
);

CREATE TABLE utilisateur(
   id_utilisateur VARCHAR(250) ,
   email VARCHAR(50) ,
   mot_de_passe VARCHAR(250) ,
   nom VARCHAR(50)  NOT NULL,
   prenom VARCHAR(250),
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   id_role VARCHAR(250)  NOT NULL,
   PRIMARY KEY(id_utilisateur),
   UNIQUE(email),
   FOREIGN KEY(id_role) REFERENCES role(id_role)
);

CREATE TABLE institution(
   id_institution VARCHAR(250) ,
   nom VARCHAR(250) ,
   adresse VARCHAR(250),
   description VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   id_utilisateur VARCHAR(250)  NOT NULL,
   PRIMARY KEY(id_institution),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE categorie(
   id_categorie VARCHAR(50) ,
   libelle VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_categorie)
);
CREATE TABLE categorie_institution(
   id_institution VARCHAR(250) ,
   id_categorie VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_institution, id_categorie),
   FOREIGN KEY(id_institution) REFERENCES institution(id_institution),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie)
);

CREATE TABLE activite (
   id_activite VARCHAR(250),
   libelle VARCHAR(250) NOT NULL,
   id_categorie VARCHAR(50) NOT NULL,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_activite),
   FOREIGN KEY(id_categorie) REFERENCES categorie(id_categorie)
);


CREATE TABLE categoriePays(
   id_categorie_pays VARCHAR(50) ,
   libelle VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_categorie_pays)
);

CREATE TABLE pays(
   id_pays VARCHAR(50) ,
   libelle VARCHAR(50) ,
   code VARCHAR(50) UNIQUE,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   id_categorie_pays VARCHAR(50) ,
   PRIMARY KEY(id_pays),
   FOREIGN KEY(id_categorie_pays) REFERENCES categoriePays(id_categorie_pays)
);


CREATE TABLE habilitation(
   id_habilitation VARCHAR(250) ,
   libelle VARCHAR(50) ,
   description VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_habilitation)
);

CREATE TABLE seuil_risque(
   id_seuil VARCHAR(50) ,
   taux_min NUMERIC(15,2),
   taux_max NUMERIC(15,2),
   description VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_seuil)
);

CREATE TABLE section(
   id_section VARCHAR(250) ,
   libelle VARCHAR(50) ,
   coefficient NUMERIC(15,2)  ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_section)
);
ALTER TABLE section DROP COLUMN IF EXISTS coefficient;

CREATE TABLE sous_section(
   id_sous_section VARCHAR(50) ,
   libelle VARCHAR(250) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   id_section VARCHAR(250) ,
   PRIMARY KEY(id_sous_section),
   FOREIGN KEY(id_section) REFERENCES section(id_section)
);




CREATE TABLE type_reponse(
   id_type_reponse VARCHAR(250) ,
   type VARCHAR(250) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_type_reponse)
);

CREATE TABLE evaluation(
   id_evaluation VARCHAR(50) ,
   date_debut TIMESTAMP,
   date_fin VARCHAR(50) ,
   status VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_evaluation)
);


CREATE TABLE question(
   id_question VARCHAR(250) ,
   libelle VARCHAR(50) ,
   definition VARCHAR(50) ,
   exige_document BOOLEAN,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   id_type_reponse VARCHAR(250) ,
   id_sous_section VARCHAR(50) ,
   PRIMARY KEY(id_question),
   FOREIGN KEY(id_type_reponse) REFERENCES type_reponse(id_type_reponse),
   FOREIGN KEY(id_sous_section) REFERENCES sous_section(id_sous_section)
);
 ALTER TABLE question ALTER COLUMN libelle TYPE VARCHAR(255);

CREATE TABLE document(
   id_document VARCHAR(250) ,
   libelle VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   id_reponse VARCHAR(250) ,
   id_question VARCHAR(250) ,
   PRIMARY KEY(id_document),
   FOREIGN KEY(id_reponse) REFERENCES reponse(id_reponse),
   FOREIGN KEY(id_question) REFERENCES question(id_question)
);


CREATE TABLE role_habilittation(
   id_role VARCHAR(250) ,
   id_habilitation VARCHAR(250) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_role, id_habilitation),
   FOREIGN KEY(id_role) REFERENCES role(id_role),
   FOREIGN KEY(id_habilitation) REFERENCES habilitation(id_habilitation)
);


CREATE TABLE question (
   id_question VARCHAR(250),
   libelle VARCHAR(250),
   definition VARCHAR(50),
   exige_document BOOLEAN DEFAULT FALSE,
   justification_required BOOLEAN DEFAULT FALSE,
   comment_required BOOLEAN DEFAULT FALSE,
   notes VARCHAR(500) DEFAULT '',
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   id_type_reponse VARCHAR(250),
   id_sous_section VARCHAR(50),
   PRIMARY KEY(id_question),
   FOREIGN KEY(id_type_reponse) REFERENCES type_reponse(id_type_reponse),
   FOREIGN KEY(id_sous_section) REFERENCES sous_section(id_sous_section)
);

CREATE TABLE evaluation_utilisateur(
   id_utilisateur VARCHAR(250) ,
   id_evaluation VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_utilisateur, id_evaluation),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur),
   FOREIGN KEY(id_evaluation) REFERENCES evaluation(id_evaluation)
);

CREATE TABLE institution_reponse(
   id_utilisateur VARCHAR(250) ,
   id_question VARCHAR(250) ,
   reponse VARCHAR(50) ,
   cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY(id_utilisateur, id_question),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur),
   FOREIGN KEY(id_question) REFERENCES question(id_question)
);


-- 1. facteur_risque (RI)

CREATE TABLE facteur_risque (
    id_facteur VARCHAR(50) PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    poids NUMERIC(5,2) CHECK (poids > 0 AND poids <= 1),
    id_section VARCHAR(250) NOT NULL,
    cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_section) REFERENCES section(id_section)
);

-- 2. domaine_controle (SC)
CREATE TABLE domaine_controle (
    id_domaine VARCHAR(50) PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    poids NUMERIC(5,2) CHECK (poids > 0 AND poids <= 1),
    id_section VARCHAR(250) NOT NULL,
    cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_section) REFERENCES section(id_section)
);


CREATE TABLE regle_scoring (
    id_regle VARCHAR(50) PRIMARY KEY,
    id_question VARCHAR(250) NOT NULL,
    condition TEXT NOT NULL,
    note_ri NUMERIC(2,1) CHECK (note_ri BETWEEN 1 AND 4),
    note_sc NUMERIC(2,1) CHECK (note_sc BETWEEN 1 AND 4),
    FOREIGN KEY (id_question) REFERENCES question(id_question)
);


-- Ajoute les colonnes manquantes
ALTER TABLE regle_scoring 
ADD COLUMN IF NOT EXISTS cree_le TIMESTAMP DEFAULT NOW(),
ADD COLUMN IF NOT EXISTS modifie_le TIMESTAMP DEFAULT NOW();

--table reponses lie a une regle de scoring et institution
CREATE TABLE reponse_regle_scoring (
    id_reponse_regle SERIAL PRIMARY KEY,
    id_regle VARCHAR(50) NOT NULL,
    id_institution VARCHAR(250) NOT NULL,
    reponse TEXT NOT NULL,
    cree_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modifie_le TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_regle) REFERENCES regle_scoring(id_regle),
    FOREIGN KEY (id_institution) REFERENCES institution(id_institution)
);


