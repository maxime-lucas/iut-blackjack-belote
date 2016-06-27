/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.panels;

import fr.chidiaclucas.jeuxblackjackbelote.gui.frames.fMain;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Maxime
 */
public class pBeginning extends JPanel implements KeyListener{
    private fMain window;
    public pBeginning(fMain window) {
        this.window = window;
        setBackground(new Color(21,21,21));
        this.setFocusable(true);
        this.requestFocus(true);
        addKeyListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int posX,posY, heightPreviousText;
        String text;
        Font font;
        FontMetrics metrics;
        BufferedImage image;
        
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
        
        // Affichage du texte BLACKJACK
        text = "Blackjack";
        font = new Font("Bebas", Font.PLAIN, 74);
        metrics = g.getFontMetrics(font);
        g2.setFont(font);
        g2.setColor(new Color(255,255,255));
        posX = (window.getWidth() - metrics.stringWidth(text))/2;
        posY = 150;
        g2.drawString(text,posX,posY);
        heightPreviousText = metrics.getHeight();
        
        // Affichage du texte BELOTE
        text = "Belote";
        font = new Font("Bebas", Font.PLAIN, 119);
        metrics = g.getFontMetrics(font);
        g2.setFont(font);
        g2.setColor(new Color(255,255,255));
        posX = (window.getWidth() - metrics.stringWidth(text))/2;
        posY = posY + heightPreviousText + 20;
        g2.drawString(text,posX,posY);
        heightPreviousText = metrics.getHeight();
        
        // Affichage du texte PAR CHICHI & MAX
        text = "Par Chichi & Max";
        font = new Font("Bebas", Font.PLAIN, 28);
        metrics = g.getFontMetrics(font);
        g2.setFont(font);
        g2.setColor(new Color(25,91,142));
        posX = (window.getWidth() - metrics.stringWidth(text))/2;
        posY = posY + heightPreviousText - 100;
        g2.drawString(text,posX,posY);

        // Affichage du texte APPUYEZ SUR ENTREE POUR COMMENCER
        try {
            image = ImageIO.read(new File("../../GUI/IMG/appuyezsur.png"));
            g2.drawImage(image, 400- image.getWidth()/2, 380, this);
        } catch (IOException ex) {
            Logger.getLogger(pBeginning.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Affichage de l'image de JackSparrow
        try {
            image = ImageIO.read(new File("../../GUI/IMG/jacksparrow.png"));
            g2.drawImage(image, 800-image.getWidth(), 600-image.getHeight(), this);
        } catch (IOException ex) {
            Logger.getLogger(pBeginning.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Affichage de SPARROW
        try {
            image = ImageIO.read(new File("../../GUI/IMG/sparrow.png"));
            g2.drawImage(image, image.getWidth()+430,image.getHeight()-13, this);
        } catch (IOException ex) {
            Logger.getLogger(pBeginning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(ke.getKeyCode() == 10) {
            window.getContentPane().removeAll();
            window.getContentPane().revalidate();
            window.getContentPane().repaint();
            window.printPGameChoice();
        }
    }

    public void keyReleased(KeyEvent ke) { }
    public void keyTyped(KeyEvent ke) {}
}
