package fr.chidiaclucas.jeuxblackjackbelote.app;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maxime
 */
public class CardBelote extends Card {
    CardBelote(int color, int number, boolean visible) {
        super(color, number, visible);
        
        switch(number)
        {
            case 1:
                this.value = 11;
                break;
            case 7:
                this.value = 0;
                break;
            case 8:
                this.value = 0;
                break;
            case 9:
                this.value = 0;
                break;
            case 10:
                this.value = 10;
                break;
            case 11:
                this.value = 2;
                break;
            case 12:
                this.value = 3;
                break;
            case 13:
                this.value = 4;
                break;
        }
    } 
    
    public void updateAtout()
    {
        switch(this.number)
        {
            case 1:
                this.value = 11;
                break;
            case 7:
                this.value = 0;
                break;
            case 8:
                this.value = 0;
                break;
            case 9:
                this.value = 14;
                break;
            case 10:
                this.value = 10;
                break;
            case 11:
                this.value = 20;
                break;
            case 12:
                this.value = 3;
                break;
            case 13:
                this.value = 4;
                break;
        }
    }
}
