# Projet : évaluation des cours par les étudiants

## Installation :

- Créer le .war du projet avec la commande : 

`mvn clean package`

- Déployer le .war dans le dossier webapp d'un serveur Tomcat (v9 utilisée).

## Nécessite :

Pour le moment, il n'y a pas de base de données accessible depuis Internet pour tester ce projet.

Pour tester :	
- Installer postgresql sur la machine locale.
- Créer dans la base un utilisateur nommé course_feeling, sans mot de passe.
- Créer une base de données nommée course_feeling.
- Utiliser le script scriptExemple.sql pour peupler la base de données avec un sondage disponible.

## Tester : 

Utiliser l'adresse localhost:8080/pwa/.

## Problèmes éventuels :

404 lors de la soumission d'un code au serveur ?

Rediriger les appels du port 80 vers le port 8080 avec les commandes suivantes en root :

`iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080`

`iptables -t nat -I OUTPUT -p tcp -d 127.0.0.1 --dport 80 -j REDIRECT --to-ports 8080`

Expliqué plus en détail dans ce post : [https://serverfault.com/questions/112795/how-to-run-a-server-on-port-80-as-a-normal-user-on-linux](https://serverfault.com/questions/112795/how-to-run-a-server-on-port-80-as-a-normal-user-on-linux)