/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.app;

import java.util.ArrayList;

/**
 *
 * @author Maxime
 */
public class Pack {
    private int nbCards;
    private ArrayList<Card> pack = new ArrayList();
    
    Pack(int nbCards)
    {
        int i,j;
        this.nbCards = nbCards;
        
        if(nbCards == 32)
        {
            for(i = 1; i<=4; i++)
            {
                this.pack.add(new CardBelote(i,1,true));

                for(j = 7; j <= 13; j++)
                {
                    this.pack.add(new CardBelote(i,j,true));
                }
            }
        }
        else
        {
            for(i = 1; i<=4; i++)
            {
                for(j = 1; j <= 13; j++)
                {
                    this.pack.add(new CardBlackjack(i,j,true));
                }
            }
        }
    }
    
    @Override
    public String toString()
    {
        String str = "";
        
        for (Card carte : getPack()) {
            str += carte.toString() + "\n";
        }
        return str;
    }

    /**
     * @return the nbCards
     */
    public int getNbCards() {
        return nbCards;
    }

    /**
     * @param nbCards the nbCards to set
     */
    public void setNbCards(int nbCards) {
        this.nbCards = nbCards;
    }

    /**
     * @return the pack
     */
    public ArrayList<Card> getPack() {
        return pack;
    }

    /**
     * @param cardPack the pack to set
     */
    public void setPack(ArrayList<Card> cardPack) {
        this.pack = cardPack;
    }
}
