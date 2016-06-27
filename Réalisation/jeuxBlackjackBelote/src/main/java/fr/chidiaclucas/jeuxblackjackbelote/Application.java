/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote;

import fr.chidiaclucas.jeuxblackjackbelote.app.GameBlackjack;
import fr.chidiaclucas.jeuxblackjackbelote.app.IHM;
import fr.chidiaclucas.jeuxblackjackbelote.app.GameBelote;

/**
 *
 * @author Maxime
 */
public class Application
{
    public static void main( String[] args )
    {
        IHM ihm = new IHM();
        if (ihm.menuInitial() == 1)
        {
            GameBlackjack game = ihm.menuBlackjack();
            
            do
            {
                
            }
            while(ihm.rejouerTour() == true);
        }
        else
        {
            int i=0;
            
            GameBelote game = ihm.menuBelote();
            game.placePlayer();
            
            do
            {
                game.newRound(i);
                i = (i+1)%4;
            } while(!(game.isWinner()));
            game.writeWinner();
            
        }
    }
}
