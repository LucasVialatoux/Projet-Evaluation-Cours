INSERT INTO utilisateur(adresseMail,mdpHash) VALUES ('jean.dupont@jd.fr','hash');
INSERT INTO matiere(idProf,nomMatiere) VALUES ('jean.dupont@jd.fr','Web');
INSERT INTO sondage(idProf, idMatiere, dateSondage) VALUES ('jean.dupont@jd.fr', 'Web', extract(epoch FROM now()) * 1000);
INSERT INTO miseadispo(idSondage,code,dateCreation) VALUES (1,'AB544',extract(epoch FROM now()) * 1000);
INSERT INTO matiere(idProf,nomMatiere) VALUES ('jean.dupont@jd.fr','Réseaux');
INSERT INTO sondage(idProf, idMatiere, dateSondage) VALUES ('jean.dupont@jd.fr', 'Réseaux', extract(epoch FROM now()) * 1000);
INSERT INTO miseadispo(idSondage,code,dateCreation) VALUES (2,'544AB',extract(epoch FROM now()) * 1000);

