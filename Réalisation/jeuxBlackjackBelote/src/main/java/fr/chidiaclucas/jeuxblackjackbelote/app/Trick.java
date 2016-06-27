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
public class Trick 
{
    private ArrayList<Card> trick = new ArrayList();
    private int nbPoints;

    /****CONSTRUCTOR***/
    public Trick() { this.nbPoints = 0; }

    /****GETTERS & SETTERS ***/
    public ArrayList<Card> getTrick() { return trick; }
    public void setTrick(ArrayList<Card> trick) { this.trick = trick; }

    public int getNbPoints() { return nbPoints; }
    public void setNbPoints(int nbPoints) { this.nbPoints = nbPoints; }
    
    /****AUTRES METHODES***/
    public void calculatePoints() 
    { 
        for (Card tmp : trick)
            this.nbPoints += tmp.value;
    }
    
    public void addPoints(int pts) { this.nbPoints += pts; }
    
    public void addCard(Card c) { this.trick.add(c); }
    
    public void afficherPli() 
    {
        for (Card cb : trick)
            cb.toString();
    }
            
}
