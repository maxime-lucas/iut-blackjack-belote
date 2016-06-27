/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.frames;

import fr.chidiaclucas.jeuxblackjackbelote.app.GameBelote;
import fr.chidiaclucas.jeuxblackjackbelote.app.IHM;
import fr.chidiaclucas.jeuxblackjackbelote.gui.panels.pBeginning;
import fr.chidiaclucas.jeuxblackjackbelote.gui.panels.pBelote;
import fr.chidiaclucas.jeuxblackjackbelote.gui.panels.pBlackjack;
import fr.chidiaclucas.jeuxblackjackbelote.gui.panels.pGameChoice;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Maxime
 */
public class fMain extends JFrame{
    public fMain(){
        int screenHeight, screenWidth, windowHeight, windowWidth;
        
        screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        
        windowWidth = 800;
        windowHeight = 600;
        
        setTitle("Blackjack/Belote - Par Chichi & Max");
        setResizable(false);
        setSize(windowWidth,windowHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation((screenWidth - windowWidth)/2, (screenHeight - windowHeight)/2);
        
        setVisible(true);
        
        printPBeginning();
    }
    public void printPBeginning() {
        pBeginning panelBeginning = new pBeginning(this);
        getContentPane().add(panelBeginning);
    }
    
    public void printPGameChoice() {
        pGameChoice panelGameChoice = new pGameChoice(this);
        getContentPane().add(panelGameChoice);
    }
    
    public void printPBelote() 
    {
        pBelote panelBelote = new pBelote(this);
        getContentPane().add(panelBelote);
    }
    
    public void printPBlackjack() {
        pBlackjack panelBlackjack = new pBlackjack(this);
        getContentPane().add(panelBlackjack); 
    }
}
