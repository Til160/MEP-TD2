# MEP-TD2

## Build en local
Pour effectuer un build en local, cloner le dépot avec la commande 'git clone
https://github.com/Til160/MEP-TD2.git', se placer dans covid-api et lancer la commande ./gradlew build
Le fichier créé par ce build se trouve dans build/lib/covid-api-0.0.1-SNAPSHOT.jar

On peut ensuite lancer l'application en localhost en lancant le .jar : java -jar build/libs/covid-api-0.0.1-SNAPSHOT.jar
L'application sera alors disponible sur le navigateur sur https://localhost:8080/

## Lancement de docker en local

On se place a la racine du projet puis on lance la commande 'docker compose up --build -d'
L'application sera alors disponible sur le navigateur sur https://localhost:8080/
