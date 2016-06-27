/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.app;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class Team 
{
    /****ATTRIBUTS***/
    private String name;
    private int score;
    private int bet;
    private ArrayList<Player> listPlayers = new ArrayList();
    private ArrayList<Trick> listTricks = new ArrayList(); //pour gérer les plis à la belote
    private boolean bank; 

    /****CONSTRUCTEURS***/
    /*Création d'une "équipe" de blackjack*/
    public Team(Player playerBlackjack, int capital, boolean bank) 
    {
        this.name = playerBlackjack.getName();
        this.score = capital;
        this.listPlayers.add(playerBlackjack);
        this.bank = bank;
        this.bet = 0;
    }
    /*Création d'une "équipe" de belote*/
    public Team(Player player1, Player player2, String name) 
    {
        this.name = name;
        this.score = 0;
        this.listPlayers.add(player1);
        this.listPlayers.add(player2);
    }

    /****ACCESSEURS***/
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public ArrayList<Player> getListPlayers() { return listPlayers; }
    public void setListPlayers(ArrayList<Player> listPlayers) { this.listPlayers = listPlayers; }
    
    public int getBet() { return bet; }
    public void setBet(int bet) { this.bet = bet; }
    
    public boolean isBank() { return bank; }
    public void setBank(boolean bank) { this.bank = bank; }

    public ArrayList<Trick> getListTricks() { return listTricks; }
    public void setListTricks(ArrayList<Trick> listTricks) { this.listTricks = listTricks; }
    
    /****AUTRES METHODES***/
    /**
     *  Récupère le score/gain/perte obtenu par le joueur au dernier tour
     *  et l'ajoute à son total de points
     */
    public void modifScore(int points) { this.score += points; }
    
    @Override
    public String toString()
    {
        String str = "";
        str += "////////////////////////////\n";
        str += "// Equipe : "+ getName() + "\n";
        
        if(!isBank())
        {
            str += "// Score/Balance = " + getScore() + "\n";
        }
        
        for(Player j : this.getListPlayers())
        {
            str += j.toString();
        }
        
        return str;
    }
}
