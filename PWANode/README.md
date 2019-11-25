# Instruction serveur PWA
### Installation
* Installer nodejs et npm
* Saisir ```npm install```
### Lancement serveur
* Saisir ```npm start```
* Se rendre sur [localhost:8081](http://localhost:8081/)

### Analyse de code
* Lancer serveur sonar
* Saisir ```npm run sonar```
* Patienter jusqu'a la fin de l'éxecution
* Se rendre sur [localhost:9000](http://localhost:9000/) pour voir l'analyse

### Test unitaires avec Selenium et Mocha (Fonction seulement avec Firefox)
* Ajouter ```[project]\node_modules\geckodriver\geckodriver.exe``` dans la variable environement PATH
* Saisir ```npm test``` pour lancer les tests
