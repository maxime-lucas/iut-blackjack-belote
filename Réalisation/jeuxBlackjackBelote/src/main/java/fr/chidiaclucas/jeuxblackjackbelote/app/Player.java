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
public class Player 
{
    /****ATTRIBUTS***/
    private String name;
    private ArrayList<Card> hand = new ArrayList();
    
    /****CONSTRUCTEUR***/
    public Player(String name) { this.name = name; }
    
    /****ACCESSEURS***/
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Card> getHand() { return hand; }
    public void setHand(ArrayList<Card> hand) { this.hand = hand; }
    
    /****AUTRES METHODES***/
    public void addCardToHand(Card card) { this.getHand().add(card); }
    /**
     * Retire la carte située à la position posCard de la hand du joueur
     * @param posCard
     * @return 
     */
    public Card pickCardFromHand(int posCard)
    {
        Card c = this.getHand().remove(posCard);
        return c;
    }
    
    public int sizeHand() { return this.getHand().size(); }
    public int calculateHand()
    {
        int val = 0;
        int nbAs = 0;
        
        for(Card c : hand)
        {
            if(1 < c.getNumber() && c.getNumber() < 10)
                val += c.getValue();
            if( 9 < c.getNumber() && c.getNumber() < 14)
                val += 10;
            if(c.getNumber() == 1 && nbAs == 0 && val + 11 > 21)
            {
                val += 1;
                CardBlackjack tmp = (CardBlackjack) c;
                tmp.updateAce(1);
                nbAs++;
            }
            else if(c.getNumber() == 1 && nbAs == 0 && val + 11 <= 21)
            {
                val += 11;
                CardBlackjack tmp = (CardBlackjack) c;
                tmp.updateAce(11);
                nbAs++;
            }
            else if(c.getNumber() == 1 && nbAs == 0 && val + 11 > 21)
            {
                val += 1;
                CardBlackjack tmp = (CardBlackjack) c;
                tmp.updateAce(1);
                nbAs++;
            }
            else if(c.getValue() == 1 && nbAs > 0)
            {
                val += 1;
                CardBlackjack tmp = (CardBlackjack) c;
                tmp.updateAce(1);
                nbAs++;
            }
        }
        
        return val;
    }
    @Override
    public String toString()
    {
        String str = "";
        str += "// Joueur : " + this.getName() + "\n";
        for(Card c : getHand() )
        {
            str += "// " + c.toString() + "\n";
        }
        if(getHand().isEmpty())
        {
            str += "// Aucune carte dans la main.\n";
        }
        str += "////////////////////////////\n";
        
        return str;
    }
    public void showHand()
    {
        System.out.println(this.name);
        for (Card tmp : this.hand)
            System.out.println(tmp.toString());
    }
    public void showCards()
    {
        for(Card c : getHand() )
        {
            c.setVisible(true);
        }
    }
    public void hideCards()
    {
        for(Card c : getHand() )
        {
            c.setVisible(false);
        }
    }
    
    //permet de trier les cartes par couleurs
    public void sortCards()
    {
        ArrayList<Card> newMain = new ArrayList();
        
        while (this.hand.size() > 0)
        {
            int pos = 0; //position de la carte la plus "faible"
            Card c = this.hand.get(0); //carte la plus "faible"
            for (int i=1; i<this.hand.size(); i++)
            {
                Card c1 = this.hand.get(i);
                if (c1.getColor() < c.getColor() || (c1.getColor() == c.getColor() && c1.getValue() < c.getValue()))
                {
                    pos = i;
                    c = c1;
                }
            }
            this.hand.remove(pos);
            newMain.add(c);
        }
        this.hand = newMain;
    }
}
