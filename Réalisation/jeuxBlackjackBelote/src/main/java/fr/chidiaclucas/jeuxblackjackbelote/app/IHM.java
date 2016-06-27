package fr.chidiaclucas.jeuxblackjackbelote.app;

import java.util.Scanner;

/**
 *
 * @author Maxime
 */
public class IHM {
    private Scanner in = new Scanner(System.in);
    
    public int menuInitial(){
        int choix;
        
        afficherln("************************");
        afficherln("* JEU BLACKJACK BELOTE *");
        afficherln("************************");
        afficherln("A quel jeu souhaitez-vous jouer ?");
        afficherln("1. Blackjack");
        afficherln("2. Belote");
        
        choix = saisieEntier();
        while( choix != 1 && choix != 2 )
        {
            afficherln("[Erreur] Saisissez 1 ou 2.");
            choix = saisieEntier();
        }
        return choix;
    }
    
    public GameBelote menuBelote()
    {
        afficherln("************************");
        afficherln("*        BELOTE        *");
        afficherln("************************");
        
        return(new GameBelote());
    }
    
    public GameBlackjack menuBlackjack()
    {
        int nbPaquets, nbJoueurs;
        
        afficherln("************************");
        afficherln("*       BLACKJACK      *");
        afficherln("************************");
        
        afficherln("Combien de paquets de 52 cartes souhaitez-vous utiliser ?");
        nbPaquets = saisieEntier();
        while( nbPaquets <= 0 )
        {
            afficherln("[Erreur] Saisissez un nombre de paquets strictement positif.");
            nbPaquets = saisieEntier();
        }  
        
        afficherln("Combien de joueurs désirent jouer ? ( Sans compter la Banque )");
        nbJoueurs = saisieEntier();
        while( nbJoueurs <= 0 )
        {
            afficherln("[Erreur] Saisissez un nombre de joueurs strictement positif.");
            nbJoueurs = saisieEntier();
        }
        return(new GameBlackjack(nbPaquets,nbJoueurs));
    }
     
    
    public void afficherln(String str)
    {
        System.out.println(str);
    }
    
    public void afficher(String str)
    {
        System.out.print(str);
    }
    
    public int saisieEntier()
    {
        afficher("-> ");
        return Integer.parseInt(in.nextLine());
    }
    
    public boolean rejouerTour()
    {
        afficherln("Voulez-vous rejouer un tour ?");
        afficherln("1. Oui");
        afficherln("2. Non");
        
        int choix = saisieEntier();
        while(choix != 1 && choix !=2)
        {
            afficherln("[Erreur] Entrez 1 ou 2 !");
            choix = saisieEntier();
        }
        
        if(choix == 1)
        {
            return true;
        }
        return false;
    }
}

