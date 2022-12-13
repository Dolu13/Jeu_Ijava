# <INTELLIBOSS>

## Présentation d' INTELLIBOSS

INTELLIBOSS est un jeu vidéoludique qui permet a un joueur d'affronter un boss
dans un combat effrénée avec comme seul arme, des questions d'orthographe ou de conjguaison.

Le fonctionnement du jeu :

Dans le menu, tout d'abord le joueur entre son nom d'utilisateur pour enregistrer sa partie par la suite.
Il peut ensuite consulter les règles du jeu grâce a un alias "!règles" ou alors quitter le jeu en entrant "!quit".
Pour passez ces options il n'a qu'a appuyez sur la touche entrée.

Enfin s'il veut commencer a jouer, il se voit proposer 2 choix de thèmes :  - conjuguaison
                                                                            - orthographe

Peu importe le choix de son thème il rentre ensuite soit dans le mode facile, soit dans le mode difficile. Grâce a un choix identiques au précédent.

    - Le mode facile permet d'affronter le boss qui perd des points de vie lorsque l'on obtient une bonne réponse aux questions.
        La partie se termine lorsque les points de vie du boss atteignent 0.

    - Le mode difficile engage aussi un combat contre le boss mais cette fois-ci lorsque le joueur rentre une mauvaise réponse 
        le boss se régenère de 20 points de vie. La partie se termine lorsque les points de vie du boss atteignent 0.

Au cours de la partie, le joueur peut decider de retourner au menu principal pour choisir un autre thème ou un autre niveau de difficulté 
grâce a l'alias "!menu".

Il peut aussi décider d'abandonner sa partie en rentrant l'alias "!abandonner".

Losrsque sa partie se voit terminer, peut importe la manière, on lui demande s'il veut rejouer. Il répond soir par oui ou non. Si oui il rejoue au même mode de jeu.

A la fin du jeu un tableau avec les joueurs ayant déja effectuer une partie et leur score s'affiche.


Des captures d'écran illustrant le fonctionnement du logiciel sont proposées dans le répertoire shots.


## Utilisation de INTELLIBOSS

Tout d'abord, il vas falloir modifier les script bash en y mettant votre chemin d'accés pour l'ap.jar (telecharger-le si ce n'est pas fait), n'ayant pas reussi a faire fonctionner l'ap.jar livré avec le squellette de l'archive. allez donc dans run.sh et compile.sh, et remplacez `/lib/ap.jar` par votre chemin relatif pour votre ap.jar.
N'oubliez pas de rajouter ":." a la fin de votre chemin si vous êtes sous linux ou MacOS.
Sinon rajoutez ";." pour Windows.

Ensuite, pour compiler les fichiers présents dans 'src' et création des fichiers '.class' dans 'classes':
Dans votre terminal :
./compile.sh

  //Pour lancer le jeu

./run.sh jeu
