## Règles de nommage des issues
#### Titre
Les titres doivent être formés suivant cette template : 
(`[ID parent(si sous tâche)]`)-`[Langage/Logiciel/Matériel]`-`[Type d'action]` : `[message]`

###### Type d'action (non exhaustive) :
* `FEATURE`
* `UPGRADE`
* `ADDING`
* `BUG`

###### Exemple
* Tâche 
    `JAVA-FEATURE : Ajout d'un système de session`
* Tâche Logiciel : 
    `GIT-ADDING : Création de la branche Test`
* Tâche Matériel : 
    `SERVER-FEATURE : Mise en place du serveur web`
* Sous tâche : 
    `(#152346)-JAVA-FEATURE : Ajout d'un système de session`

#### Les labels
Pour les labels, ajouter le type d'action et le langage/logiciel/matériel.

## Règle de nommage des commits
* Avec issue:
    (`Close/Fix`:`[ID Issue]`):`[message]`
* Sans issue:
    `[Langage/Logiciel/Matériel]`-`[Type d'action]`:`[message]`

Pour plus d'infos : [Managing Issues](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html) 