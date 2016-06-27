/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.panels;

import fr.chidiaclucas.jeuxblackjackbelote.gui.frames.fMain;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Maxime
 */
public class pGameChoice extends JPanel implements ActionListener, MouseListener{
    private fMain window;
    private JButton buttonBelote, buttonBlackjack;

    public pGameChoice(fMain w) {
        /* INITIALISATION ATTRIBUT */
        this.window = w;
        
        /* INITIALISATION GRAPHIQUE */
        setLayout(null);
        setBackground(new Color(21,21,21));
        
        /* INSTANCIATION DES COMPONENTS & MISE EN FORME */
        buttonBelote = new JButton("BELOTE");
        buttonBelote.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonBelote.addActionListener(this); // Permet d'enregistrer un ActionListener sur ce bouton
        buttonBelote.addMouseListener(this); // Permet d'enregistrer un MouseListener sur ce bouton
        
        buttonBlackjack = new JButton("BLACKJACK");
        buttonBlackjack.setFont(new Font("SansSerif", Font.BOLD, 22));
        buttonBlackjack.addActionListener(this);
        buttonBlackjack.addMouseListener(this);
        
        /* AJOUT DES COMPONENTS AU JPANEL */
        add(buttonBelote);
        add(buttonBlackjack);
        
        /* INITIALISATION DU FOCUS */
        setFocusable(true);
        requestFocus(true);
        
        /* PLACEMENT DES COMPONENTS SUR LE JPANEL */
            int windowWidth = (int) window.getWidth();
            int windowHeight = (int) window.getHeight();
        
            int spaceBetweenButtons = 50;
            int buttonWidth = 200;
            int buttonHeight = 30;
        
            int buttonBeloteX = windowWidth / 2 - spaceBetweenButtons / 2 - buttonWidth;
            int buttonBeloteY = 200;
        
            int buttonBlackjackX = windowWidth/2 + spaceBetweenButtons / 2;
            int buttonBlackjackY = 200;
        
        buttonBelote.setBounds(buttonBeloteX,buttonBeloteY,buttonWidth,buttonHeight);
        buttonBlackjack.setBounds(buttonBlackjackX,buttonBlackjackY,buttonWidth,buttonHeight);
    } 
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        /* Appel de l'environnement graphique Graphics2D :
            - Améliore le rendu
            - Ajout d'antialiasing // Suppression du crénelage
        */
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
        
        String text;
        FontMetrics metrics;
        Font font;
        int posX, posY;
        
        /* MISE EN FORME ET AFFICHAGE DE TEXTE */
        text = "QUE VOULEZ-VOUS FAIRE ?";
        font = new Font("SansSerif", Font.BOLD, 40);
        metrics = g.getFontMetrics(font); // Permet d'obtenir la taille pixellique du texte // Pour le centrer dans la fenêtre
        g2.setFont(font);
        g2.setColor(new Color(255,255,255));
        posX = (window.getWidth() - metrics.stringWidth(text))/2;
        posY = 150;
        g2.drawString(text,posX,posY);
    }

    /* METHODE POUR REAGIR AU SURVOL D'UN BOUTON ET CHANGER LA COULEUR DE SON TEXTE */
    @Override
    public void mouseEntered(MouseEvent me) {
        Object src = me.getSource();
        
        if(src == buttonBlackjack)
        {
            buttonBlackjack.setForeground(new Color(37,134,209));
        }
            
        else if(src == buttonBelote)
            buttonBelote.setForeground(new Color(37,134,209));
    }

     /* METHODE POUR REAGIR AU SURVOL D'UN BOUTON ET CHANGER LA COULEUR DE SON TEXTE */
    @Override
    public void mouseExited(MouseEvent me) {
        Object src = me.getSource();
        
        if(src == buttonBlackjack)
            buttonBlackjack.setForeground(new Color(0,0,0));
        else if(src == buttonBelote)
            buttonBelote.setForeground(new Color(0,0,0));
    }

    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getActionCommand();
        window.getContentPane().removeAll();
        window.getContentPane().revalidate();
        
        if(src == "BELOTE")
            window.printPBelote();
        else if(src == "BLACKJACK")
            window.printPBlackjack();
        window.getContentPane().repaint();
    }
    
    public void mouseClicked(MouseEvent me) {}
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
}

