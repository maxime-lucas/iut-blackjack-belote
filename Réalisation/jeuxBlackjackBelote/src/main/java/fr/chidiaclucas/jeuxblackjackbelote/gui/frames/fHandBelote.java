/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.frames;

import fr.chidiaclucas.jeuxblackjackbelote.app.Card;
import fr.chidiaclucas.jeuxblackjackbelote.app.Team;
import fr.chidiaclucas.jeuxblackjackbelote.app.Player;
import fr.chidiaclucas.jeuxblackjackbelote.app.GameBelote;
import fr.chidiaclucas.jeuxblackjackbelote.gui.panels.WindowHandListener;
import fr.chidiaclucas.jeuxblackjackbelote.gui.panels.pBeginning;
import fr.chidiaclucas.jeuxblackjackbelote.gui.panels.pBeloteBoard;
import fr.chidiaclucas.jeuxblackjackbelote.gui.panels.pHandBelote;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Kevin
 */
public class fHandBelote extends JFrame
{
    private pBeloteBoard panel;
    
    public fHandBelote(pBeloteBoard panel)
    {
        int screenHeight, screenWidth, windowHeight, windowWidth;
        
        screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        
        windowWidth = 500;
        windowHeight = 300;
        
        setResizable(false);
        setSize(windowWidth,windowHeight);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocation((screenWidth - windowWidth)/2, (screenHeight - windowHeight)/2);
        
        setVisible(true);
        
        this.panel = panel;
        this.addWindowListener(new WindowHandListener(this));
        
        printPHandBelote();
    }

    public pBeloteBoard getPanel() { return panel; }
    public void setPanel(pBeloteBoard panel) { this.panel = panel; }
    
    public void printPHandBelote() 
    {
        pHandBelote panelHandBelote = new pHandBelote(this);
        getContentPane().add(panelHandBelote);
    }
}
