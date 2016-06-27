package fr.chidiaclucas.jeuxblackjackbelote.app;
/**
 *
 * @author Maxime
 */
public class GameBlackjack extends Game {
    
    public GameBlackjack(int nbPaquets, int nbEquipe) {
        super(nbPaquets, nbEquipe);
        int i;
        Pack p = new Pack(52);
        // Création de la deck avec un ou plusieurs paquets, selon le choix de l'utilisateur
        deck = new Deck(p,nbPaquets);
        deck.shuffleCardDeck();
        deck.cutCardDeck();
        
        // Création des équipes et ajout dans la collection : Au Blackjack, une équipe n'est composée que d'un joueur
        for(i = 1; i<= nbEquipe; i++)
        {
            Player j = new Player("Joueur "+i); 
            listTeams.add( new Team(j, 100, false) ); 
        }
        // Ajout du croupier dans la collection
        Player j = new Player("Croupier");
        listTeams.add( new Team(j, 100, true) ); 
    }
   
    
    /*Création d'un tour de jeu*/
    public String getWinners()
    {
        String str = "";
        
        // Pour déterminer le gagnant, on étudie d'abord le croupier
        Team croupier = getListTeams().get(getListTeams().size() - 1);
        int nbPointsCroupier = croupier.getListPlayers().get(0).calculateHand();
        int nbCartesCroupier = croupier.getListPlayers().get(0).getHand().size();
        boolean gagnantCroupier = true;
        
        
        if(exceedBlackjack(getListTeams().get(getListTeams().size() - 1)))
        {
            gagnantCroupier = false;
        }
        
        for( Team eq : getListTeams() )
        {
            if(!eq.isBank())
            {
                // On regarde d'abord si le joueur est éligible à la victoire
                if(!exceedBlackjack(eq))
                {
                    // Si le croupier dépasse 21, alors le joueur gagne automatiquement
                    if(gagnantCroupier == false)
                    {
                        getListWinners().add(eq);
                    }
                    // Sinon,
                    else
                    {
                        int nbPointsJoueurs = eq.getListPlayers().get(0).calculateHand();
                        int nbCartesJoueurs = eq.getListPlayers().get(0).getHand().size();

                        // Le joueur sera éligible à la victoire s'il a autant, ou plus de points, que le croupier
                        if(nbPointsJoueurs >= nbPointsCroupier)
                        {
                            // Si il a autant de points que le croupier, on regarde s'il peut avoir fait ou non un Blackjack
                            if(nbPointsJoueurs == nbPointsCroupier)
                            {
                                // Un joueur a un blackjack s'il obtient 21 pts en 2 cartes. En cas d'égalité, le joueur qui fait BJ gagne.
                                if(nbPointsJoueurs == 21)
                                {
                                    // Si le joueur fait un bj, et que le croupier n'en fait pas, alors il gagne
                                    if(doBlackjack(eq) && !doBlackjack(croupier))
                                    {
                                        getListWinners().add(eq);
                                    }
                                    // Sinon,
                                    else
                                    {
                                        // Si les deux font bj, ou si aucun des deux ne le fait, alors le joueur n'est ni gagnant, ni perdant
                                        if((doBlackjack(eq) && doBlackjack(croupier) ) || ( !doBlackjack(eq) && !doBlackjack(croupier)))
                                        {
                                            getListDeuces().add(eq);
                                        }
                                    }
                                }
                                // Sinon le joueur récupére sa mise
                                else
                                {
                                    getListDeuces().add(eq);
                                }
                            }
                            // Si il a strictement plus de points que le croupier, alors il est automatiquement gagnant
                            else
                            {
                                getListWinners().add(eq);
                            }
                        }
                    }
                }
            }
        }
        str += "Croupier : ";
        if(!gagnantCroupier) {
            str += "PERDU !";
        }
        
        if(doBlackjack(croupier)) {
            str += "BLACKJACK !";
        }
        str += "\n";
        str += "* Points : " + croupier.getListPlayers().get(0).calculateHand()+"\n";
        str += "* Nombre de cartes : " + croupier.getListPlayers().get(0).getHand().size()+"\n";
        
        str += "\n\n";
        // On distribue les gains/pertes et on vide les mains des joueurs
        for(Team eq : getListTeams())
        {
            if(!eq.isBank())
            {
                str += eq.getName()+" : ";
                if(doBlackjack(eq)) {
                    str += "BLACKJACK !";
                }
                str += "\n";
                
                // Si le joueur est gagnant, il remporte le double de sa mise
                if(getListWinners().contains(eq))
                {
                    eq.setScore(eq.getScore() + eq.getBet());
                    str += "Vous remportez le double de votre mise ! \n";
                    str += "* Vous avez  : "+eq.getListPlayers().get(0).calculateHand()+" pts \n";
                    str += "* Vous aviez  : "+eq.getListPlayers().get(0).getHand().size()+" cartes \n";
                    str += "* Vous avez désormais : "+eq.getScore()+" € !\n"; 
                }
                // Sinon, si il n'appartient pas non plus aux Egalités, il perd sa mise
                else
                {
                    if(getListDeuces().contains(eq))
                    {
                        str += "Vous êtes arrivé(e) à égalité, vous gagnez votre mise !\n";
                        str += "* Vous avez  : "+eq.getListPlayers().get(0).calculateHand()+" pts \n";
                        str += "* Vous aviez  : "+eq.getListPlayers().get(0).getHand().size()+" cartes \n";
                        str += "* Vous avez toujours : "+eq.getScore()+" € !\n";
                    }
                    else
                    {
                        eq.setScore(eq.getScore() - eq.getBet());
                        str += "Vous avez perdu votre mise !\n";
                        str += "* Vous avez  : "+eq.getListPlayers().get(0).calculateHand()+" pts \n";
                        str += "* Vous aviez  : "+eq.getListPlayers().get(0).getHand().size()+" cartes \n";
                        str += "* Vous avez désormais : "+eq.getScore()+" € ! \n";
                    }
                }
            }
            str += "\n\n";
        }
        
        // On vide les tableaux de gagnants et d'égalités
        getListWinners().clear();
        getListDeuces().clear();
        
        return str;
    } 
    public void resetPlayerHand() {
        for(Team e : getListTeams())
            e.getListPlayers().get(0).getHand().clear();
    }
    public void distributeFirstCards()
    {
        for(Team e : listTeams)
        {
            givePlayerCard(e,true);
        }
    } 
    public void distributeSecondCards()
    {
        for(Team e : listTeams)
        {
            if(!e.isBank())
            {
                givePlayerCard(e,true);
            }
            else
            {
                givePlayerCard(e,false);
            }
        }
    }
    public void givePlayerCard(Team e, boolean visible)
    {
        Card c = deck.pickCardFromDeck();
        c.setVisible(visible);
        e.getListPlayers().get(0).addCardToHand((CardBlackjack) c );
    }
    public boolean exceedBlackjack(Team e)
    {
        int limite = 21;
        int pts = e.getListPlayers().get(0).calculateHand();
        
        if(pts > limite)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean doBlackjack(Team e)
    {
        int pts = e.getListPlayers().get(0).calculateHand();
        int nbCartes = e.getListPlayers().get(0).getHand().size();
        
        if(pts == 21 && nbCartes == 2)
        {
            return true;
        }
        return false;
    }
}
