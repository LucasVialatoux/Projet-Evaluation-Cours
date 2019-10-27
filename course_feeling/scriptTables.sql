DROP TABLE IF EXISTS ressentis;
DROP TABLE IF EXISTS miseadispo;
DROP TABLE IF EXISTS sondage;
DROP TABLE IF EXISTS matiere;
DROP TABLE IF EXISTS utilisateur;
DROP TYPE  IF EXISTS Ressenti_t;

CREATE TABLE IF NOT EXISTS utilisateur (
    adresseMail VARCHAR PRIMARY KEY,
    mdpHash VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS matiere (
    idProf VARCHAR,
    nomMatiere VARCHAR,
    FOREIGN KEY (idProf) REFERENCES utilisateur(adresseMail),
    PRIMARY KEY (idProf,nomMatiere)
);

CREATE TABLE IF NOT EXISTS sondage (
    idProf VARCHAR,
    idMatiere VARCHAR,
    idSondage INT,
    FOREIGN KEY (idProf,idMatiere) REFERENCES matiere(idProf,nomMatiere),
    UNIQUE(idProf,idMatiere,idSondage),
    PRIMARY KEY (idSondage)
);

CREATE TABLE IF NOT EXISTS miseadispo (
    idSondage INT,
    code VARCHAR,
    dateCreation DATE,
    FOREIGN KEY (idSondage) REFERENCES sondage(idSondage),
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
    ress ressenti_t
);