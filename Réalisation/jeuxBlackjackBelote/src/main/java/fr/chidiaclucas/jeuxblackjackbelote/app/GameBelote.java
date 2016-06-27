/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.app;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Maxime
 */
public class GameBelote extends Game
{   
    private Scanner in = new Scanner(System.in);
    private ArrayList<Player> listPlayerOrdered; 
    private CardBelote turnedCard = null;
    private int atout;
    private Trick currentTrick = new Trick();
    private int dealer=0;
    private int currentPlayer;
    private int playerWithBelote; //joueur possédant la belote (si quelqu'un l'a)

    private int master; //joueur qui est en passe de remporter le pli
    private int taker; //joueur qui selectionne l'atout
    private int opener; //joueur qui a récupéré le dernier pli et qui ouvre le suivant
    
    public GameBelote() 
    {
        super(1, 2);
        this.listPlayerOrdered = new ArrayList();
        
        Pack p = new Pack(32);
        
        // Création de la pioche
        deck = new Deck(p,nbCardPacks);
        // Création de la deck
        deck = new Deck(p,nbCardPacks);
        deck.shuffleCardDeck();
        
        // Création des équipes
        Player j1 = new Player("Joueur 1");
        Player j2 = new Player("Joueur 2");
        Player j3 = new Player("Joueur 3");
        Player j4 = new Player("Joueur 4");
        
        Team e1 = new Team(j1,j3,"Team 1");
        Team e2 = new Team(j2,j4,"Team 2");
        this.listTeams.add(e1);
        this.listTeams.add(e2);
        
        // Placement des joueurs dans l'ordre de jeu
        this.listPlayerOrdered.add(e1.getListPlayers().get(0));
        this.listPlayerOrdered.add(e2.getListPlayers().get(0));
        this.listPlayerOrdered.add(e1.getListPlayers().get(1));
        this.listPlayerOrdered.add(e2.getListPlayers().get(1));
    }  
    
    public GameBelote(Team t1, Team t2) 
    {
        super(1, 2);
        this.listPlayerOrdered = new ArrayList();
        
        Pack p = new Pack(32);
        

        // Création de la pioche
        deck = new Deck(p,nbCardPacks);
        deck.shuffleCardDeck();
        
        // Création des équipes
        this.listTeams.add(t1);
        this.listTeams.add(t2);
        
        // Placement des joueurs dans l'ordre de jeu
        this.listPlayerOrdered.add(t1.getListPlayers().get(0));
        this.listPlayerOrdered.add(t2.getListPlayers().get(0));
        this.listPlayerOrdered.add(t1.getListPlayers().get(1));
        this.listPlayerOrdered.add(t2.getListPlayers().get(1));
    }

    public int getMaster() { return master; }
    public void setMaster(int master) { this.master = master; }
    
    public int getDealer() { return dealer; }
    public void setDealer(int dealer) { this.dealer = dealer; }

    public int getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(int currentPlayer) { this.currentPlayer = currentPlayer; }

    public int getTaker() { return taker; }
    public void setTaker(int taker) { this.taker = taker; }

    public int getOpener() { return opener; }
    public void setOpener(int opener) { this.opener = opener; }
    
    public ArrayList<Player> getListPlayerOrdered() { return listPlayerOrdered; }
    public void setListPlayerOrdered(ArrayList<Player> listPlayerOrdered) { this.listPlayerOrdered = listPlayerOrdered; }

    public CardBelote getTurnedCard() { return turnedCard; }
    public void setTurnedCard(CardBelote turnedCard) { this.turnedCard = turnedCard; }

    public int getAtout() { return atout; }
    public void setAtout(int atout) { this.atout = atout; }

    public Trick getCurrentTrick() { return currentTrick; }
    public void setCurrentTrick(Trick currentTrick) { this.currentTrick = currentTrick; }

    public int getPlayerWithBelote() { return playerWithBelote; }
    public void setPlayerWithBelote(int playerWithBelote) { this.playerWithBelote = playerWithBelote;} 
    
    //Mise en place des joueurs dans l'ordre de jeu
    public void placePlayer()
    {
        Team tmp1 = this.listTeams.get(0);
        Team tmp2 = this.listTeams.get(1);
        
        this.listPlayerOrdered.add(tmp1.getListPlayers().get(0));
        this.listPlayerOrdered.add(tmp2.getListPlayers().get(0));
        this.listPlayerOrdered.add(tmp1.getListPlayers().get(1));
        this.listPlayerOrdered.add(tmp2.getListPlayers().get(1));
    }
    
    //Méthode de distribution 
    public void deal(int nbCards, int indexPlayer)
    {
        int i;
        Card c;
        CardBelote cb;
        for (i=0; i<nbCards; i++)
        {
            c = this.deck.pickCardFromDeck();
            cb = (CardBelote)c;
            this.listPlayerOrdered.get(indexPlayer).addCardToHand(cb);
        }   
    }
    
    public void turnCard() 
    { 
        Card c = this.deck.pickCardFromDeck(); 
        CardBelote cb = (CardBelote)c;
        //cb.setVisible(true);
        this.turnedCard = cb;
    }
    
    public void goToNextPlayer() { currentPlayer = (currentPlayer+1)%4; } 

    public void updateDealer() { dealer = (dealer+1)%4; }
    
    //Mise a jour des points selon l'atout
    public void updateAtout()
    {
        CardBelote tmp3;
       
        for(Player tmp : this.listPlayerOrdered)
        {
            for (Card tmp2 : tmp.getHand())
            {
                tmp3 = (CardBelote)tmp2;
                if (tmp3.getColor() == atout)
                    tmp3.updateAtout();
            }
        }
    }
    
    /*Test pour savoir si le joueur 
     *devient maitre en posant sa carte
     */
    public boolean isMaster(Card card)
    {
        boolean isAtout = false;
        boolean master = true;
        int atoutMax = 0;
        int numberAtout = 0; //pour différencier le 7 et le 8 d'atout qui valent tous 2 0pts
        int colorRef;
        
        //si aucune card n'est posée, le 1er joueur est forcément maitre
        if (currentTrick.getTrick().isEmpty())
            return true;
        else
        {
            colorRef = currentTrick.getTrick().get(0).getColor();
            
            for (Card tmp : currentTrick.getTrick())
            //permet de connaître l'atout le plus haut présent dans le pli
            {
                if (tmp.getColor() == atout)
                {
                    isAtout = true;
                    if (tmp.getValue() >= atoutMax)
                    {
                        if (tmp.getValue() == 0)
                            if (tmp.getNumber() > numberAtout)
                                numberAtout = tmp.getNumber();
                        atoutMax = tmp.getValue();
                    }
                }
            }   
            
            //s'il n'y a pas d'atout dans le pli
            //la card la plus haute de la couleur demandee
            //est maitresse
            if(!isAtout)
            {
                if (card.getColor() == colorRef)
                    for (Card tmp : currentTrick.getTrick())
                    {
                        if (tmp.getColor() == colorRef)
                        {
                            if (tmp.getValue() == card.getValue())
                            {
                                if (tmp.getNumber() < card.getNumber())
                                    master = true;
                                else
                                    master = false;
                            }
                            else if (tmp.getValue() < card.getValue())
                                master = true;
                            else
                                return false;
                        }
                    }
                //on devient master en posant un atout
                else if (card.getColor() == atout)
                    master = true;
                else
                    master = false;
            }
            //s'il y a deja un atout en jeu
            //il faut en poser un plus haut
            //pour devenir master
            else
            {
                if(card.getColor() != atout)
                    master = false;
                else
                {
                    if (card.getValue() == atoutMax)
                    {
                        if(card.getNumber() > numberAtout)
                            master = true;
                        else
                            master = false;
                    }
                    else if (card.getValue() > atoutMax)
                        master = true;
                    else
                        master = false;
                }
            }
        }
        return master;
    }

    //Mise à jour du joueur maitre
    public void updateMaster(Card card)
    {
        if (isMaster(card))
            master = currentPlayer;
    }

    /*Test pour savoir si le joueur
     *peut poser une carte en restant
     *dans les règles
     */
    public boolean isValid(Card card)
    {
        boolean hasColor = false; //pour vérifier si le joueur possède la couleur demandée
        boolean hasAtout = false; //pour vérifier si le joueur possède un atout
        boolean hasAtoutSup = false; //pour vérifier si le joueur possède une card plus grande à l'atout
        boolean isAtout = false; //pour vérifier si un autre joueur a coupé
        int colorRef;
        int valueRef = 0; //pour comparer les cartes à l'atout
        int numberAtout = 0; //pour différencier le 7 et le 8 d'atout qui valent tous 2 0pts
        Card cardPlayed = card;
        
        if (currentTrick.getTrick().isEmpty())
            return true;
        else
        {
            colorRef = currentTrick.getTrick().get(0).getColor();
            /*on verifie s'il y a un atout dans le pli
            *et quelle est sa valeur
            */
            if (!currentTrick.getTrick().isEmpty())

            {
                for (Card tmp : currentTrick.getTrick())
                {
                    if (tmp.getColor() == atout)
                    {
                        isAtout = true;
                        if (tmp.getValue() > valueRef)
                            valueRef = tmp.getValue();
                        if (tmp.getValue() == 0)
                            numberAtout = tmp.getNumber();
                    }
                }
            }

            //on verifie si le joueur possède un atout en main
            for (Card tmp : this.listPlayerOrdered.get(currentPlayer).getHand())
            {
                if (tmp.getColor() == atout)
                    hasAtout = true;
                if (tmp.getColor() == colorRef)
                    hasColor = true;
            }

            if (colorRef == atout)
            {
                if (cardPlayed.getColor() == atout) 
                {   //on vérifie que le joueur monte bien à l'atout
                    if (cardPlayed.getValue() > valueRef)
                        return true;
                    else
                    {
                        //on vérifie si le joueur possède un atout supérieur à ceux posés
                        for (Card tmp : this.listPlayerOrdered.get(currentPlayer).getHand())
                            if (tmp.getColor() == atout)
                            {
                                if(tmp.getValue() == valueRef)
                                {
                                    if(tmp.getNumber() > numberAtout)
                                        hasAtoutSup = true;
                                    else
                                        hasAtoutSup = false;
                                }
                                else if(tmp.getValue() > valueRef)
                                    hasAtoutSup = true;
                            }
                        if (hasAtoutSup)
                            return false;
                        else 
                            return true;
                    }
                }
                else
                {
                    if (hasAtout)
                        return false;
                    else
                        return true;
                }
            }
            else
            {
                if (cardPlayed.getColor() == colorRef)
                    return true;
                else
                {
                    if (hasColor)
                        return false;

                    else if (master == (currentPlayer+2)%4) //si le partenaire est maitre, pas besoin de couper
                        return true;

                    else if (cardPlayed.getColor() == atout)
                    {
                        if (!isAtout)
                            return true;
                        else if (cardPlayed.getValue() > valueRef)
                            return true;
                        else
                        {
                            for (Card tmp : this.listPlayerOrdered.get(currentPlayer).getHand())
                                if (tmp.getColor() == atout && tmp.getValue() > valueRef)
                                    hasAtoutSup = true;

                            if (hasAtoutSup)
                                return false;
                            else 
                                return true;
                        }
                    }
                    else
                    {
                        if (hasAtout)
                            return false;
                        else
                            return true;
                    }
                }
            }
        }
    }
    
    //Test pour savoir si un joueur a la belote (roi et dame d'atout)
    public void findBelote()
    {
        for (int i=0; i<4; i++)
        {
            int belote = 0;
            for(Card card : listPlayerOrdered.get(i).getHand())
            {
                if ((card.getColor() == atout) && (card.getValue() == 4 || card.getValue() == 3))
                    belote++;
            }
            if (belote == 2)
            {
                playerWithBelote = i;
                return;
            }
        }
        playerWithBelote = -1;
    }
    
    //Donne le pli à l'équipe qui l'emporte
    public void addTrick()
    {
        if (master%2 == 0)
            listTeams.get(0).getListTricks().add(currentTrick);
        else
            listTeams.get(1).getListTricks().add(currentTrick);
    }
    
    //Gestion de l'évolution des points
    public void updateScore()
    {
        int scoreTeam1=0, scoreTeam2=0;
        
        for (Trick tmp : listTeams.get(0).getListTricks())
            scoreTeam1 += tmp.getNbPoints();
        for (Trick tmp2 : listTeams.get(1).getListTricks())
            scoreTeam2 += tmp2.getNbPoints();
        
        if (taker%2 == 0) //si un joueur de l'équipe 1 prend
        {
            if (listTeams.get(1).getListTricks().isEmpty())
                listTeams.get(0).modifScore(262); //S'il y a capot
            else if (scoreTeam1 > scoreTeam2) //si l'equipe réussi son contrat, tout le monde gagne les points marqués
            {
                this.listTeams.get(0).modifScore(scoreTeam1);
                this.listTeams.get(1).modifScore(scoreTeam2);
            }
            else
                this.listTeams.get(1).modifScore(162);
        }
        else
        {
            if (listTeams.get(0).getListTricks().isEmpty())
                listTeams.get(1).modifScore(262); //S'il y a capot
            else if (scoreTeam2 > scoreTeam1)
            {
                this.listTeams.get(0).modifScore(scoreTeam1);
                this.listTeams.get(1).modifScore(scoreTeam2);
            }
            else
                this.listTeams.get(0).modifScore(162);
        }
        if ((playerWithBelote%2) == 0) //si un joueur de l'équipe 1 a la belote
            this.listTeams.get(0).modifScore(20);
        
        if ((playerWithBelote%2) == 1) //si un joueur de l'équipe 2 a la belote
            this.listTeams.get(1).modifScore(20);
    }
    
    /*Remise des cartes dans le paquet
     *rappel : on ne mélange pas à la belote
     */
    public void restaureDeck()
    {
        for (Trick tmp : listTeams.get(0).getListTricks())
            for (Card tmp2 : tmp.getTrick())
                this.deck.getDeck().add(tmp2);
        
        for (Trick tmp : listTeams.get(1).getListTricks())
            for (Card tmp2 : tmp.getTrick())
                this.deck.getDeck().add(tmp2);
    }
    
    /*
     *Test pour savoir si une équipe gagne la partie
     *et met un terme au jeu
     */
    public boolean isWinner()
    {
        if (this.listTeams.get(0).getScore() > 1000)
            return true;
        else if (this.listTeams.get(1).getScore() > 1000)
            return true;
        else
            return false;
    }
    
    /*
     *Methodes d'affichage
     */
    public void writeln(String str) { System.out.println(str); }
    public void write(String str) { System.out.print(str); }
    public void writeWinner()
    {
        if (this.listTeams.get(0).getScore() > 1000)
            writeln(this.listTeams.get(0).getName() + " gagne " + this.listTeams.get(0).getScore() + " à " + this.listTeams.get(1).getScore());
        else
            writeln(this.listTeams.get(1).getName() + " gagne " + this.listTeams.get(1).getScore() + " à " + this.listTeams.get(0).getScore());
    }
    
    public void writePoints(int score1, int score2)
    {
        writeln("Points de " + this.listTeams.get(0).getName() + " : " + score1);
        writeln("Points de " + this.listTeams.get(1).getName() + " : " + score2);
    }
    
    public void writeScore()
    {
        writeln("Score de " + this.listTeams.get(0).getName() + " : " + this.listTeams.get(0).getScore());
        writeln("Score de " + this.listTeams.get(1).getName() + " : " + this.listTeams.get(1).getScore());
    }
    
    public int saisieEntier()
    {
        write("-> ");
        return Integer.parseInt(in.nextLine());
    }
      
    /*
     *Création d'un tour de jeu
     *Pour pouvoir changer d'opener à chaque donne, 
     *on renvoie sa position dans la liste de joueurs
     */ 
    public void newRound(int rank)
    {
        int i, j;
        currentPlayer = rank;
        opener = currentPlayer; 
        int choice;
        
        //variables de gestion de belote
        int belote = 0; //pour pouvoir distinguer la Belote de la Rebelote
        Card turnedCard, cardPlayed;
        int scoreTeam1 = 0, scoreTeam2 = 0;
        boolean validity = true;
        
        writeln("************ NOUVEAU TOUR ************");
        
        this.deck.cutCardDeck();
        //1er tour de distribution
        for (i=0; i<4; i++)
            deal(2, (rank+i) % 4);
        
        //2eme tour de distribution
        for (i=0; i<4; i++)
            deal(3, (rank+i) % 4);
        
        turnedCard = this.deck.pickCardFromDeck();
        turnedCard.setVisible(true);
        writeln(turnedCard.toString());
        
        //on trie une 1ere fois les cards de chaque joueur
        for(Player tmp : this.listPlayerOrdered)
            tmp.sortCards();

        i=0;
        //1er tour d'annonce
        //les 4 joueurs décident s'ils prennent
        //la card retournée ou non
        do
        {
            choice=0;
            
            for (Card tmp2 : this.listPlayerOrdered.get((rank + i)%4).getHand())
                tmp2.setVisible(true);
            this.listPlayerOrdered.get((rank + i)%4).showHand();
            do
            {
                writeln("Que voulez-vous faire ? 1. Prendre / 2. Passer");
                choice = saisieEntier();
                if (choice != 1 && choice != 2)
                    writeln("Veuillez choisir 1 ou 2 !");
            } while (choice != 1 && choice != 2);
          
            i++;
        } while ((choice != 1) && (i<4));
        
        i--;
        
        if (choice == 1) //un joueur prend à cet atout
        {
            taker = (rank+i)%4;
            atout = turnedCard.getColor();
            this.listPlayerOrdered.get(taker).addCardToHand(turnedCard);
           
            for (j=0; j<4; j++)
            {
                if ((rank+j)%4 == taker)
                    deal(2, (rank+j)%4);
                else
                    deal(3, (rank+j)%4);
            } 
        }
        else //personne ne prend -> 2eme tour d'annonce
            //les joueurs choisissent éventuellement
            //une autre couleur comme atout
        {
            i=0;
            do
            {
                for (Card tmp2 : this.listPlayerOrdered.get((rank + i)%4).getHand())
                    tmp2.setVisible(true);
                
                this.listPlayerOrdered.get((rank + i)%4).showHand();
                do
                {
                    writeln("Quelle couleur voulez vous ? 1. Carreau / 2. Pique / 3. Coeur / 4. Trefle / 5. Passer");
                    choice = saisieEntier();
                    if (choice < 1 || choice > 5)
                        writeln("Veuillez choisir une option entre 1 et 5 !");
                } while (choice < 1 || choice > 5);
              
                i++;
            } while ((choice == 5) && (i<4));
            
            i--;
            
            if (choice != 5)
            {
                taker = (rank+i)%4;
                atout = choice;
                this.listPlayerOrdered.get(taker).addCardToHand(turnedCard);

                for (j=0; j<4; j++)
                {
                    if ((rank+j)%4 != taker)
                        deal(3,(rank+j)%4);
                    else
                        deal(2,(rank+j)%4);
                }
            }
            //si personne ne prend au 2eme tour, la donne s'arrête et on recommence un nouveau tour
            //avec pour donneur, le joueur suivant
            //on remet alors les cards de chaque joueur dans le paquet
            else
                restaureDeck();
        }

        //on met à jour la valeur des cards à l'atout
        updateAtout();
        
        //on trie une dernière fois les cards de chaque joueur
        for(Player tmp : this.listPlayerOrdered)
            tmp.sortCards();
        
        //on recupère l'indice du joueur possédant la belote (si un joueur la possède)
        findBelote();
        
        //Debut des tours de pose de cards
        //8 cards -> 8 tours
        for (i=0; i<8; i++)
        {
            currentPlayer = opener;
            currentTrick = new Trick();
           
            //les 4 joueurs jouent à tour de role
            for (j=0; j<4; j++)
            {
                currentPlayer = (opener+j)%4;
                for (Card tmp2 : this.listPlayerOrdered.get(currentPlayer).getHand())
                    tmp2.setVisible(true);
                
                this.listPlayerOrdered.get(currentPlayer).showHand();
                do
                {
                    if (!validity)
                        writeln("Cette action de jeu n'est pas valide");
                    
                    do
                    {
                        write("Quelle card voulez-vous poser ? (indiquer sa position)");
                        choice = saisieEntier();
                        if (choice > 8-i)
                            writeln("Aucune card trouvée à cette position");
                    }
                    while (choice > 8-i);
                    Card cardToPlay = this.listPlayerOrdered.get(currentPlayer).getHand().get(choice);
                    validity = isValid(cardToPlay);
                } while (!validity);
                
                cardPlayed = this.listPlayerOrdered.get(currentPlayer).pickCardFromHand(choice - 1);
                
                //On regarde si le joueur pose la belote
                if (currentPlayer == playerWithBelote)
                {
                    if (cardPlayed.getColor() == atout)
                    {
                        if (cardPlayed.getValue() == 4 || cardPlayed.getValue() == 3)
                        {
                            if (belote == 0)
                                writeln("Belote");
                            if (belote == 1)
                                writeln("Rebelote");
                            belote++;
                        }
                    }
                }
                updateMaster(cardPlayed);
                currentTrick.addCard(cardPlayed);
                
                for (Card tmp2 : this.listPlayerOrdered.get(currentPlayer).getHand())
                    tmp2.setVisible(false);
                
                writeln("\n" + this.listPlayerOrdered.get(master).getName() + " est maitre\n");
            }
            writeln(this.listPlayerOrdered.get(master).getName() + " remporte le pli\n");
            //Gestion de la répartition des plis
           currentTrick.calculatePoints();
            if (i == 7)
                currentTrick.addPoints(10);
            
            addTrick();
            
            opener = master;
        }
        
        //Gestion de l'évolution des points
        updateScore();
        
        writeln("************ SCORE ************");
        writePoints(scoreTeam1, scoreTeam2);
        writeln("");
        writeScore();
        writeln("*******************************");
        
        /*Remise des cards dans le paquet*/
        /*rappel : on ne mélange pas à la belote*/
        restaureDeck();
    }
}
