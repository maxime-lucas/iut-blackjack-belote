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
public abstract class Card {
    protected int color;
    protected int number;
    protected int value;
    protected boolean visible;
    
    protected static String[] numbers = {"As","2","3","4","5","6","7","8","9","10","Valet","Dame","Roi"};
    protected static String[] colors = {"Carreau","Pique","Coeur","Trèfle"};
    
    Card(int color,int number, boolean visible)
    {
        this.number = number;
        this.color = color;
        this.visible = visible;
    }
    
    @Override
    public String toString()
    {
        if(isVisible())
        {
            return "[ " + getNumbers()[getNumber()-1] + " de " + getColors()[getColor()-1] + " - "+getValue()+"pt(s) ]";
        }
        else
        {
            return "[ Carte cachée ]";
        }  
    }

    /**
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * @param couleur the color to set
     */
    public void setColor(int couleur) {
        this.color = couleur;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param chiffre the number to set
     */
    public void setNumber(int chiffre) {
        this.number = chiffre;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param valeur the value to set
     */
    public void setValue(int valeur) {
        this.value = valeur;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * @return the numbers
     */
    public static String[] getNumbers() {
        return numbers;
    }

    /**
     * @param aChiffres the numbers to set
     */
    public static void setNumbers(String[] aChiffres) {
        numbers = aChiffres;
    }

    /**
     * @return the colors
     */
    public static String[] getColors() {
        return colors;
    }

    /**
     * @param aCouleurs the colors to set
     */
    public static void setColors(String[] aCouleurs) {
        colors = aCouleurs;
    }
}
