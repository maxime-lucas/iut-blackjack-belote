/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.app;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Maxime
 */
public class Deck {
    ArrayList<Card> deck = new ArrayList();
    
    Deck(Pack p, int n) 
    {
        for(int i = 1; i <= n ; i++) {
            for(Card c : p.getPack()) {
                deck.add(c);
            }
        }
    }
    
    public void shuffleCardDeck()
    { 
        int indice1, indice2;
        Random rand = new Random();
        Card tmp;
        
        for(int i = 0; i < 100 ; i++)
        {
            indice1 = rand.nextInt(deck.size() - 1 );
            indice2 = rand.nextInt(deck.size() - 1 );
            
            tmp = deck.get(indice2);
            deck.set(indice2, deck.get(indice1));
            deck.set(indice1, tmp);
        }
    }
    
    public void cutCardDeck()
    {
        ArrayList<Card> cutDeck = new ArrayList();
        Random rand = new Random();
        
        int pivot = rand.nextInt((getDeck().size() - 3) - 3 + 1) + 3;
        
        for(int i = pivot; i<getDeck().size();i++)
        {
            Card c = getDeck().get(i);
            cutDeck.add(c);
        }
        
        for(int i = 0; i<pivot;i++)
        {
            Card c = getDeck().get(i);
            cutDeck.add(c);
        }
        
        setDeck(cutDeck);
    }
    
    @Override
    public String toString()
    {
        String str = "";
        
        if(!getDeck().isEmpty())
        {
            for (Card carte : getDeck()) {
                str += carte.toString() + "\n";
            }
        }
        else
        {
            str = "La pioche est vide.\n";
        }
            
        return str;
    }
    
    public Card pickCardFromDeck()
    {
        int indice = deck.size() - 1 ;
        Card c = deck.get(indice);
        deck.remove(indice);
        return c;
    }

    /**
     * @return the paquetDeCartes
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * @param cardDeck the paquetDeCartes to set
     */
    public void setDeck(ArrayList<Card> cardDeck) {
        this.deck = cardDeck;
    }
}
