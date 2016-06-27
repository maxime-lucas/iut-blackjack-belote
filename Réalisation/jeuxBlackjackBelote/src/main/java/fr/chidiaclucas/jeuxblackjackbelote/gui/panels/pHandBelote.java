/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.panels;

import fr.chidiaclucas.jeuxblackjackbelote.app.Card;
import fr.chidiaclucas.jeuxblackjackbelote.app.Player;
import fr.chidiaclucas.jeuxblackjackbelote.app.GameBelote;
import fr.chidiaclucas.jeuxblackjackbelote.app.Trick;
import fr.chidiaclucas.jeuxblackjackbelote.gui.frames.fHandBelote;
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
public class pHandBelote extends JPanel implements MouseListener
{
    private fHandBelote window;
    private pBeloteBoard panel;
    private Player currentPlayer;
    private GameBelote game;
    private int nbPlayerPlayed = 0;
    private int belote = 0;
    
    private Image cardImage;
    private int boardWidth = 600;
    private int boardHeight = 300;
    private int widthCard = 79;
    private int heightCard = 123;
    
    private JLabel playerName;
    
    public pHandBelote(fHandBelote window)
    {
        setLayout(null);
        setBackground(new Color(21,21,21));
        setPreferredSize(new Dimension(500,300));
        
        this.window = window;
        this.panel = window.getPanel();
        this.game = window.getPanel().getBoard();
        this.currentPlayer = window.getPanel().getBoard().getListPlayerOrdered().get(window.getPanel().getBoard().getCurrentPlayer());
        
        //affiche le nom du joueur à l'écran
        this.window.setTitle(currentPlayer.getName());
//        add(playerName); 
//        playerName = new JLabel(this.currentPlayer.getName(), JLabel.CENTER);
//        playerName.setBounds(0,0,500,50 );
//        playerName.setBackground(new Color(51,51,51));
//        playerName.setForeground(new Color(37,134,209));
//        playerName.setOpaque(true);
//        playerName.setFont(new Font("SansSerif", Font.BOLD, 15));
        
        this.addMouseListener(this);
        /* On charge l'image des cartes */
        loadImage();
        
        /* On affiche les cartes du joueurs */
        for(Card c : currentPlayer.getHand())
        {
            c.setVisible(true);
        }
    }

    public pBeloteBoard getPanel() { return panel; }
    public void setPanel(pBeloteBoard panel) { this.panel = panel; }
    
    public void updateCurrentPlayer() { this.currentPlayer = game.getListPlayerOrdered().get(game.getCurrentPlayer()); }
    public void updateWindowTitle() { this.window.setTitle(currentPlayer.getName()); }
    
    public void pickCard(int n)
    {
        if(game.getTurnedCard() == null)
        {
            Card c = currentPlayer.getHand().get(n);
            Trick currentTrick = this.game.getCurrentTrick();

            if(this.game.isValid(c))  
            {   
                currentPlayer.getHand().remove(n);
                c.setVisible(true);
                if (game.getCurrentPlayer() == game.getPlayerWithBelote())
                {
                    if (c.getColor() == game.getAtout())
                    {
                        if(c.getValue() == 4 || c.getValue() == 3)
                        {
                            JOptionPane beloteAlert = new JOptionPane();
                            if (belote == 0)
                                beloteAlert.showMessageDialog(null, "Belote !", "Belote", JOptionPane.INFORMATION_MESSAGE);
                            if (belote == 1)
                                beloteAlert.showMessageDialog(null, "Rebelote !", "Belote", JOptionPane.INFORMATION_MESSAGE);
                            belote++;
                        }
                    }
                }
                this.game.updateMaster(c);
                currentTrick.getTrick().add(c);
                panel.repaint();
                game.goToNextPlayer();

                updateCurrentPlayer();
                updateWindowTitle();

                nbPlayerPlayed++;

                if(nbPlayerPlayed == 4)
                {
                    game.getCurrentTrick().calculatePoints();
                    panel.incrementNbTrickPlayed(); 
                    
                    if (panel.getNbTrickPlayed() == 8)
                    {
                        game.getCurrentTrick().addPoints(10);
                        game.addTrick();
                        game.setOpener(game.getMaster()); //Le 1er joueur du prochain pli est celui qui est maitre à la fin du dernier
                        game.setCurrentTrick(new Trick());
                        window.dispose();
                        game.updateScore();
                        game.restaureDeck();
                        game.updateDealer();
                        panel.doNewRound();
                    }
                    else
                    {
                        game.addTrick();
                        game.setOpener(game.getMaster()); //Le 1er joueur du prochain pli est celui qui est maitre à la fin du dernier
                        game.setCurrentTrick(new Trick());
                        window.dispose();
                        panel.doNewTrick();
                    }
                    
                }
                else
                    repaint();
                //this.window.dispose();
            }
            else
            {
                JOptionPane warning = new JOptionPane();
                warning.showMessageDialog(null, "Vous ne pouvez pas poser cette carte !", "Faute de jeu", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void loadImage() 
    {
        try {
            cardImage = ImageIO.read(new File("../../GUI/IMG/cards.png"));
        } catch (IOException ex) {
            Logger.getLogger(pBeginning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        /* Initialisation de l'environnement graphique : Meilleur rendu grâce à Graphics2D */
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );        

        g2.setColor(new Color(62,140,72));
        g2.fillRect(0, 0, boardWidth, boardHeight);
        
        // Affichage de l'emplacement de chaque joueur
        int posHandX = 35;
        int posHandY = boardHeight/2 - heightCard/2;

        // Affichage des cartes, si la main n'est pas vide
        if(!currentPlayer.getHand().isEmpty()) 
        {
            int j = 0;
            int pas = 50;
            currentPlayer.showCards();
            //affichage
            for (Card c : currentPlayer.getHand())
            {
                drawCard(g2, c, posHandX + j*pas, posHandY);
                j++;
            }
            currentPlayer.hideCards();
        }
    }
    
    public void drawCard(Graphics g, Card c, int x, int y) 
    {
        int cx;    // x-coord du coin supérieur de la carte dans cardImage
        int cy;    // y-coord du coin supérieur de la carte dans cardImage

        // On traite la carte cachée
        if(!c.isVisible()) {
            cx = 79 * 2;
            cy = 123 * 4;
        }
        else {
            cx = (c.getNumber()-1)*79;
            switch(c.getColor()) {
                case 1: //CARREAU
                    cy = 123;
                    break;
                case 2: // PIQUE
                    cy = 123 *3;
                    break;
                case 3: // COEUR
                    cy = 123 *2;
                    break;
                default: // TREFLE
                    cy = 0;
                    break;
            }
        }
        System.out.println(c);
        System.out.println(cx);
        System.out.println(cy);
        g.drawImage(cardImage,x,y,x+79, y + 123, cx, cy, cx + 79, cy + 123, this);
        //g.drawImage(cardImage, x, y, x+79, y+123, 0,0, 79,123, this);
    }

    
    public void mouseClicked(MouseEvent me) {}
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) 
    {
        int xMouse = me.getX();
        int yMouse = me.getY();
        int nbCard = this.currentPlayer.getHand().size();
        
        if((xMouse < 35) || (xMouse > (35 + (nbCard-1)*50 + 79)))
            return;
        
        else if ((yMouse < boardHeight/2 - heightCard/2) || (yMouse > boardHeight/2 - heightCard/2 + heightCard))
            return;
        
        if (xMouse > (35 + (nbCard-1)*50))
            pickCard(nbCard-1);
        else
            pickCard((int)((xMouse-35)/50));
    }
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
}
