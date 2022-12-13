import extensions.File;
import extensions.CSVFile;


class jeu extends Program{

    boolean abandon = false;
    boolean menue = false;    
    boolean fini = false;
    boolean jouer = true;        
    int diff = 0;
    int choix = 0;
    String nom_joueur = "";
    int score = 0;

void algorithm(){
    CSVFile csv_conj = loadCSV("../ressources/q&r_conjugaison.csv");    
    CSVFile csv_ortho = loadCSV("../ressources/q&r_ortho.csv");
    CSVFile scoreBoard = loadCSV("../ressources/save.csv");
    clearScreen(); 

    afficherMenu();
       
    boolean mode = false;

    while(jouer == true){
        while(!fini && !abandon){
                if(diff == 1){
                    if(choix == 1){
                        modeJeu(csv_conj, mode);
                    }
                    else{
                        modeJeu(csv_ortho, mode);
                    }  
                }
                else{
                    if(choix == 1){
                        modeJeu(csv_conj, !mode);
                    }
                    else{
                        modeJeu(csv_ortho, !mode);
                    } 

                    if(menue == true){
                        afficherMenu();
                            menue = false;
                    }
                }    
            rejouer();
        }
         
    }

    String[][] scoreTab = save(scoreBoard);
    afficherTableauScore(scoreTab);
}

    String[][] save(CSVFile score) {    // Fonction qui permet d'enregistrer le score et le nom d'utilisateur du joueur actuel dans le CSV des scores. 
        String[][] scoreboard = new String[rowCount(score) + 1][columnCount(score)];
        CSVToTab(score, scoreboard);
        scoreboard[rowCount(score)][0] = nom_joueur;
        scoreboard[rowCount(score)][1] = score+"";
        saveCSV(scoreboard, "../ressources/score.csv");
        return scoreboard; // Elle retourne un tableau a double dimensions qui contient le nom et le score du joueur.
    }

    void CSVToTab(CSVFile fichier, String[][] tab) { // Permet de transformer un CSV en tableau correspondant.
        for (int i = 0; i < rowCount(fichier); i++) {
            for (int y = 0; y < columnCount(fichier); y++) {
                tab[i][y] = getCell(fichier, i, y);
            }
        }
    }

    void afficherMenu(){ // Fonction qui affiche le menu du jeu. Avec la possibilité d'afficher les regles. De choisir le mode de jeu voulu.
        diff = 0;
        choix = 0;
        nom_joueur = nomU();
            String regles = demanderRegle();
            if(afficherRegles(regles) == false){
                println(afficher("../ressources/regle.txt"));
            }
    
            choix = choixMode();
    
            diff = choixDiff();
    
            while(!verifSaisie(choix,3) && !verifSaisie(diff,3)){
                println("Saisie incorrecte : entrez 1 ou 2");
                choix = readInt();
            }
            clearScreen();                                  
    }

    void rejouer(){  // Propose au joueur de rejouer au mode de jeu qu'il vient de terminer
        println("voulez vous rejouer ?");
                String rejouer = readString();
                if(equals(toLowerCase(rejouer),"oui")){
                    fini = false;
                    abandon = false;
                }
                else{
                    jouer = false;
                }
    }


    boolean retourMenu(String res){ //Commande qui permet au joueur au cours de sa partie de retourner au menu pour changer de mode de jeu.
        if(equals(res,"!menu"));{
            menue = true;
        }
        return menue;
    }

    void afficherTableauScore(String [][] score){ //Fonction qui permet d'afficher le tableau des scores.
        String cell = "";
        println("Voici le Tableau des Scores : \n");
        for (int lig=0;lig<length(score,1);lig++){
            for (int col=0;col<length(score,2);col++){		
                cell = score[lig][col];
                print(cell + " ");
            }
            print("\n");
        }
        
    }

    String demanderRegle(){ // Commnande qui permet au joueur d'afficher les regles. 

        println("Pour consulter les règles écrivez !regles\nPour passer appuyer sur entrer\n");
        String res = readString();
        return toLowerCase(res);
    }

    String demanderQuitter(){ // Demande au joueur s'il veut quitter le jeu
        println("\nPour quitter le jeu, tapez !quit Sinon appuyer sur entrée\n");
        String res = readString();
        return toLowerCase(res);
        
    }


    String nomU(){ // Demande au joueur le nom d'utilisateur qu'l veut utiliser.
        String nom;
        println("BIENVENUE DANS INTELLIBOSS");
        delay(1000);
        println("Entrez votre nom d'utilisateur :");
        nom = readString();
        return nom;
    }

    int choixMode(){    //Demande au joueur le mode de jeu auquel il veut jouer
        println("Choisissez votre type d'exercice : (répondez par 1 ou 2)");
        println("1. Conjugaison");
        println("2. Orthographe");
        int res = readInt();
        return res;
    }

    int choixDiff(){    //Demande au joueur le mode de difficulté auquel il veut se confronter
        clearScreen();
        println("Choisissez la difficulté : (répondez par 1 ou 2)");
        println("1. Normal");
        println("2. Difficile");
        int res = readInt();
        return res;
    }



    boolean verifSaisie(int saisie, int valMax){ //Verifie la saisie du joueur pour le choix du mode et de la difficulté

        return saisie>0 && saisie<valMax;
    }



    int initBoss(int pv, String nom){   //Initalise le boss au début de la partie.
        int res = pv ;
        println("---------------------" + pv + "/"+ pv +"---------------------");
        println(afficher("../ressources/boss.txt"));
        println("MOUAHAHAHAHA JE VAIS TE VAINCRE");
        return res;
    }

    String poserQuestion(CSVFile theme, int val){    // Choisie un question au hasard au sein du CSV du mode carrespondant
        println(getCell(theme,val,0));
        println(ANSI_BLUE+"  ----------------------");
        println("| Entrez votre reponse : |");
        println("  ----------------------"+ANSI_RESET);
        String res = readString();
        return res;
    }

    int verifierReponse(int val, CSVFile reponse, int pvboss, String res, boolean mode){ // Verifie la reponse du joueur avec le ficchier CSV correspondant. Retire ensuite les pv au boss en fonction de la réponse.
        int new_pv = pvboss;
        if(equals(res,"!abandon") || equals(res,"!menu")){
            return new_pv;
        }
        if(equals(toLowerCase(res), getCell(reponse,val,1))){
            println(ANSI_GREEN +"BONNE REPONSE"+ANSI_RESET);
            new_pv = new_pv - 20;
            delay(1000);
        }
        else{
            if(mode == true){
                new_pv = new_pv + 20;
            }
            println(ANSI_RED +"FAUX !! La bonne réponse était : "+ANSI_RESET +getCell(reponse,val,1));
            delay(1000);
            
        } 
        return new_pv;

    }

    void afficherBoss(String fichier, int pv, int vie){  // affiche le boss actualisé de l'état de ses points de vie.
        println("---------------------" + pv + "/"+ vie +"---------------------");
        println(ANSI_RED);
        println(afficher(fichier));
        println(ANSI_RESET);

    }

    void modeJeu(CSVFile fichier, boolean modeJ){ // Fonction qui effectue le déroulement du mode de jeu choisit precedement par le joueur  
        boolean modeJe = modeJ;
        int monBoss = initBoss(100, "toto");
        delay(1000);
        int new_pv = monBoss;
        while (new_pv != 0 && !abandon && !menue){
            clearScreen();
            afficherBoss("../ressources/boss.txt", new_pv, monBoss);
            delay(1000);
            println("Tapez !menu pour retourner au Menu principal");
            println();
            println("Pour abandonnez tapez !abandon");
            println();
            delay(1000);
            int alea = (int)(random()*rowCount(fichier));
            String res = poserQuestion(fichier, alea);
            if(equals(res,"!menu")){
                menue = true;
            }
            if(equals(res,"!abandon")){
                abandon = true;
            }
            new_pv = verifierReponse(alea, fichier, new_pv, res, modeJe);
            score++;
        }
        if(new_pv == 0){
            println(afficher("../ressources/victoire.txt"));
            fini = true;
        }
    }

    boolean afficherRegles(String res){ // Verifie l'entrée utilisateur pour afficher les règles
            boolean regles = true;
            if(equals(res,"!regles")){
                regles = false;
            }
        return regles;
    }


    String afficher(String nom_fichier){ // Fonction qui permet d'afficher des fichier textes.
        final String FILENAME = nom_fichier;    
        File f = newFile(FILENAME);
        int nbl = 0;
        String res = "";
        while(ready(f)){
            String currentLine = readLine(f);
            res = res + currentLine + "\n";
            nbl++;
        }

        return res;
    }


}