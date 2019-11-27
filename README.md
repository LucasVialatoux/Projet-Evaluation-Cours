## README
#### Dépendances 

Dépendances du front : [lien vers le fichier package.json détaillant les dépendances](https://forge.univ-lyon1.fr/p1608372/m1if10/blob/FINAL/PWANode/package.json).

Dépendances du back : [lien vers le fichier pom.xml détaillant les dépendances](https://forge.univ-lyon1.fr/p1608372/m1if10/blob/FINAL/course_feeling/pom.xml).

#### Procédure de build

Installation de la base de données :
1. Installer postgresql.
2. Créer un utilisateur nommé course_feeling sans mot de passe.
3. Créer une base de données s'appelant course\_feeling appartenant à l'utilisateur course\_feeling.
4. Importer le script scriptTables.sql dans la base.
5. (Facultatif) Importer le script scriptExemple.sql dans la base pour remplir la table avec un exemple.

Installation du back-end :
1. Cloner le projet.
2. Installer Tomcat.
3. Exécuter la commande `mvn clean package` dans le projet.
4. Copier le .war résultant dans le dossier webapps/ de Tomcat.

Installation du front-end : 
1. Installer nodejs et npm.
2. Copier le dossier PWANode/ sur la machine.
3. Dans le dossier PWANode/, faire la commande  `npm install` pour installer les dépendances nodejs nécessasires.
4. Installer nginx.
5. Supprimer 
6. Mettre en place un reverse_proxy sur le port 8081 comme ci-dessous :
```
server {
    listen 80;
    listen [::]:80;
    location / {
        proxy_pass http://127.0.0.1:8081;
    }
}
```

Démarrage :

1. Lancer Tomcat.
2. Lancer le front-end en exécutant la commande `npm start` dans le dossier PWANode/.

#### Lien vers la VM

La VM est accessible à cette adresse : http://192.168.74.217/

#### Analyse de code
* Lancer serveur sonar
* Saisir ```npm run sonar```
* Patienter jusqu'a la fin de l'éxecution
* Se rendre sur [localhost:9000](http://localhost:9000/) pour voir l'analyse

#### Test unitaires avec Selenium et Mocha (Fonctionne seulement avec Firefox)
* Ajouter ```[project]\node_modules\geckodriver\geckodriver.exe``` dans la variable environement PATH
* Saisir ```npm test``` pour lancer les tests
