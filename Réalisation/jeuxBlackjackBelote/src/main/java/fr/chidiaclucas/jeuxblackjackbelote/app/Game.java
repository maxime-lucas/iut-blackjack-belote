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
public abstract class Game 
{
    /****ATTRIBUTS***/
    protected int nbCardPacks;
    protected int nbTeams;
    
    protected ArrayList<Team> listTeams = new ArrayList();
    protected ArrayList<Team> listWinners = new ArrayList();
    private ArrayList<Team> listDeuces = new ArrayList();
    protected Deck deck;
    
    /****CONSTRUCTEUR
     * @param nbCardPacks
     * @param nbTeams ***/
    public Game(int nbCardPacks, int nbTeams) 
    {
        this.nbCardPacks = nbCardPacks;
        this.nbTeams = nbTeams;  
    }
    
    /****ACCESSEUR
     * @return S***/
    public int getNbCardPacks() { return nbCardPacks; }
    public void setNbCardPacks(int nbCardPacks) { this.nbCardPacks = nbCardPacks; }

    public ArrayList<Team> getListTeams() { return listTeams; }
    public void setListTeams(ArrayList<Team> listTeams) { this.listTeams = listTeams; }
    
    public ArrayList<Team> getListWinners() { return listWinners; }
    public void setListWinners(ArrayList<Team> listWinners) { this.listWinners = listWinners; }
    
    public ArrayList<Team> getListDeuces() { return listDeuces; }
    public void setListDeuces(ArrayList<Team> listDeuces) { this.listDeuces = listDeuces; }

    public Deck getDeck() { return deck; }
    public void setDeck(Deck deck) { this.deck = deck; }

    /****AUTRES METHODES
     * @param team
     * ***/
    public void addTeam(Team team) { this.listTeams.add(team); }

    
}
