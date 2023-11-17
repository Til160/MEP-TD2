# MEP-TD2

## Build en local  

Pour effectuer un build en local, cloner le dépot avec la commande 'git clone https://github.com/Til160/MEP-TD2.git', se placer dans covid-api et lancer la commande ./gradlew build  
Le fichier créé par ce build se trouve dans build/lib/covid-api-0.0.1-SNAPSHOT.jar

On peut ensuite lancer l'application en localhost en lancant le .jar : java -jar build/libs/covid-api-0.0.1-SNAPSHOT.jar  
L'application sera alors disponible sur le navigateur sur http://localhost:8080/api/center/getAll
  
## Lancement de docker en local  

On se place a la racine du projet puis on lance la commande 'docker compose up --build -d'  
L'application sera alors disponible sur le navigateur sur http://localhost:8080/api/center/getAll

Pour les 2 tests précédent, la réponse à la requête devrait être celle  la : 
```json
[
    {
        "id":1,
        "nom":"Hopital Central",
        "adresse":"12 rue de la Paix",
        "codePostal":"57000",
        "ville":"Metz"
    },
    {
        "id":2,
        "nom":"Centre hospitalier",
        "adresse":"21 rue de la Republique",
        "codePostal":"54000",
        "ville":"Nancy"
    }
]
```
## Prérequis pour le build de l'image et le push sur dockerhub  
  
Il faut aller sur Dashboard -> Manage Jenkins -> Tools -> Global Tool Configuration -> Docker afin d'installer Docker depuis  Jenkins. Il faudra lui donner l'ID docker ainsi que de cocher la case installation automatique.  
  
Il faut également aller dans Dashboard -> Manage Jenkins -> Manage Plugins -> Available > Docker afin d'installer le plugin Docker.   
  
Il faut enfin aller dans Dashboard -> Manage Jenkins -> Credentials -> System -> Global credentials afin de créer une nouvelle crédential, en renseignant le nom et le mot de passe avec les token générés sur dockerhub. Il faudra mettre docker-hub comme id pour la credential.  
  
## Lancement du build et du push   
  
Retournez sur la page d'accueil Jenkins et créer un nouvel item de type pipeline et placez y le contenu du Jenkinsfile de ce repository.  
Il faudra possiblement changer les liens des repository si nécessaire.  
Vous pouvez enfin lancer le build de pipeline.
  
Vous pouvez accéder a mon dockerhub sur ce lien : https://hub.docker.com/repository/docker/til160/mep_td2/general  


# Auteurs
## Personne ayant travaillé sur le TD2 : 
REY Thibault

## Personne ayant travaillé sur le Back-End : 
WAGNER Frédéric  
REY Thibault
