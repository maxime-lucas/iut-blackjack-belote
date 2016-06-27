/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.panels;

import fr.chidiaclucas.jeuxblackjackbelote.app.GameBelote;
import fr.chidiaclucas.jeuxblackjackbelote.app.Trick;
import fr.chidiaclucas.jeuxblackjackbelote.gui.frames.fHandBelote;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Kevin
 */
public class WindowHandListener implements WindowListener
{
    private fHandBelote window;
    private pBeloteBoard panel;
    private GameBelote game;

    public WindowHandListener(fHandBelote window) 
    {
        this.window = window;
        this.panel = window.getPanel();
        this.game = window.getPanel().getBoard();
        
        this.window.addWindowListener(this);
    }
    
    
            
    public void windowOpened(WindowEvent we) {}
    public void windowClosing(WindowEvent we) {}
    public void windowClosed(WindowEvent we) 
    {   
        panel.repaint();       
    }
    public void windowIconified(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowActivated(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {} 
}
