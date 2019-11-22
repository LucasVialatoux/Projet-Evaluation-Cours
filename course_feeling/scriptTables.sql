DROP TABLE IF EXISTS ressentis;
DROP TABLE IF EXISTS miseadispo;
DROP TABLE IF EXISTS sondage;
DROP TABLE IF EXISTS matiere;
DROP TABLE IF EXISTS utilisateur;
DROP TYPE  IF EXISTS Ressenti_t;

CREATE TABLE IF NOT EXISTS utilisateur (
    email VARCHAR PRIMARY KEY,
    mdpHash VARCHAR NOT NULL,
    token VARCHAR
);

CREATE TABLE IF NOT EXISTS matiere (
    idProf VARCHAR,
    nomMatiere VARCHAR,
    FOREIGN KEY (idProf) REFERENCES utilisateur(email),
    PRIMARY KEY (idProf,nomMatiere)
);

CREATE TABLE IF NOT EXISTS sondage (
    idProf VARCHAR,
    idMatiere VARCHAR,
    idSondage SERIAL,
    dateSondage BIGINT,
    FOREIGN KEY (idProf,idMatiere) REFERENCES matiere(idProf,nomMatiere) on delete cascade,
    UNIQUE(idProf,idMatiere,idSondage),
    PRIMARY KEY (idSondage)
);

CREATE TABLE IF NOT EXISTS miseadispo (
    idSondage INT,
    code VARCHAR,
    dateCreation BIGINT,
    FOREIGN KEY (idSondage) REFERENCES sondage(idSondage) on delete cascade,
    PRIMARY KEY (idSondage),
    UNIQUE (code)
);

CREATE TYPE ressenti_t as ENUM(
    'Interessant', 
    'Accessible',
    'Complique',
    'Monotone',
    'Confus',
    'Effraye'
);

CREATE TABLE IF NOT EXISTS ressentis (
    idSondage INT,
    ress ressenti_t,
    FOREIGN KEY (idSondage) REFERENCES sondage(idSondage) on delete cascade
);