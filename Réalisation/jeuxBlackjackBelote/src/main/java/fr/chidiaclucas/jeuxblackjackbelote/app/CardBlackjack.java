/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.app;

/**
 *
 * @author Maxime
 */
public class CardBlackjack extends Card {
    CardBlackjack(int color, int number, boolean visible)
    {
        super(color, number, visible);
        
        if(number > 1 && number < 10)
        {
            this.value = number;
        }
        if(number > 9 && number < 14)
        {
            this.value = 10;
        }
        if(number == 1)
        {
            this.value = 11;
        }
    } 
    
    public void updateAce(int i)
    {
        this.value = i;
    }
}
