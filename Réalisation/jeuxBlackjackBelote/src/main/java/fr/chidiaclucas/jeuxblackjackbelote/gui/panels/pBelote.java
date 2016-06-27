/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.panels;

import fr.chidiaclucas.jeuxblackjackbelote.gui.frames.fMain;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Maxime
 */
public class pBelote extends JPanel{
    /* ATTRIBUTES */
    private fMain window;
    private JButton buttonNewGame; // Bouton pour commencer une nouvelle partie
    private JButton buttonResign; // Bouton pour abandonner la partie en cours
    private JButton buttonDraw; // Bouton pour prendre
    private JButton buttonPass; // Bouton pour passer
    private JButton buttonSpades; // Bouton pour prendre à Pique
    private JButton buttonHearts; // Bouton pour prendre à Coeur
    private JButton buttonClubs; // Bouton pour prendre à Trèfles
    private JButton buttonDiamonds; // Bouton pour prendre à Carreau
    private JButton buttonBack; // Bouton pour retourner au choix du jeu
    private JLabel labelMessage; // Affiche les messages à l'utilisateur
    //Affichage du score
    private JLabel labelNameTeam1;
    private JLabel labelNameTeam2;
    private JLabel labelScoreTeam1;
    private JLabel labelScoreTeam2;
    
    private pBeloteBoard board; // Plateau de jeu

    /* CONSTRUCTOR */
    public pBelote(fMain window) 
    {
        this.window = window;
        setLayout(null);
        setBackground(new Color(21,21,21));
        setPreferredSize(new Dimension(640,480));
        
        board = new pBeloteBoard(this);
        
        add(board);
        add(buttonNewGame);
        add(buttonResign);
        add(buttonPass);
        add(buttonDraw);
        add(buttonBack);
        add(labelMessage);
        
        //Labels correspondant au score
        add(labelNameTeam1);
        add(labelScoreTeam1);
        add(labelNameTeam2);
        add(labelScoreTeam2);
        
        //Boutons correpsondants aux couleurs de cartes (pour le 2ème tour d'annonce)
        add(buttonSpades);
        add(buttonHearts);
        add(buttonClubs);
        add(buttonDiamonds);
        
        buttonNewGame.setBounds(667,20,120,30);
        buttonResign.setBounds(667,55,120,30);
        buttonPass.setBounds(667,400,120,30);
        buttonDraw.setBounds(667,435,120,30);
        buttonBack.setBounds(667,535,120,30);
        labelMessage.setBounds(0,521,660,50);
        board.setBounds(0,0,660,521);
        
         //Boutons correpsondant aux couleurs de cartes (pour le 2ème tour d'annonce)
        buttonSpades.setBounds(667,435,27,30);
        buttonHearts.setBounds(667+27+4,435,27,30);
        buttonClubs.setBounds(667+2*(27+4),435,27,30);
        buttonDiamonds.setBounds(667+3*(27+4),435,27,30);
        
        //Labels correspondant au score
        labelNameTeam1.setBounds(667,150,120,20);
        labelScoreTeam1.setBounds(667,175,120,20);
        labelNameTeam2.setBounds(667,250,120,20);
        labelScoreTeam2.setBounds(667,275,120,20);
    }
    
    /* GETTER & SETTER */
    public fMain getWindow() { return window; }
    public void setWindow(fMain window) { this.window = window; }
    
    public JButton getButtonNewGame() { return buttonNewGame; }
    public void setButtonNewGame(JButton buttonNewGame) { this.buttonNewGame = buttonNewGame; }
    
    public JButton getButtonResign() { return buttonResign; }
    public void setButtonResign(JButton buttonResign) { this.buttonResign = buttonResign; }
    
    public JButton getButtonPass() { return buttonPass; }
    public void setButtonPass(JButton buttonPass) { this.buttonPass = buttonPass; }
    
    public JButton getButtonDraw() { return buttonDraw; }
    public void setButtonDraw(JButton buttonDraw) { this.buttonDraw = buttonDraw; }
    
    public JLabel getLabelMessage() { return labelMessage; }
    public void setLabelMessage(JLabel message) { this.labelMessage = message; }
    
    public JButton getButtonBack() { return buttonBack; }
    public void setButtonBack(JButton buttonBack) { this.buttonBack = buttonBack; }

    public JButton getButtonSpades() { return buttonSpades; }
    public void setButtonSpades(JButton buttonSpades) { this.buttonSpades = buttonSpades; }

    public JButton getButtonHearts() { return buttonHearts; }
    public void setButtonHearts(JButton buttonHearts) { this.buttonHearts = buttonHearts;}

    public JButton getButtonClubs() { return buttonClubs; }
    public void setButtonClubs(JButton buttonClubs) { this.buttonClubs = buttonClubs; }

    public JButton getButtonDiamonds() { return buttonDiamonds; }
    public void setButtonDiamonds(JButton buttonDiamonds) { this.buttonDiamonds = buttonDiamonds; }
    
    public pBeloteBoard getBoard() { return board; }
    public void setBoard(pBeloteBoard board) { this.board = board;}    

    public JLabel getLabelNameTeam1() { return labelNameTeam1; }
    public void setLabelNameTeam1(JLabel labelNameTeam1) { this.labelNameTeam1 = labelNameTeam1; }

    public JLabel getLabelNameTeam2() { return labelNameTeam2; }
    public void setLabelNameTeam2(JLabel labelNameTeam2) { this.labelNameTeam2 = labelNameTeam2; }

    public JLabel getLabelScoreTeam1() { return labelScoreTeam1; }
    public void setLabelScoreTeam1(JLabel labelScoreTeam1) { this.labelScoreTeam1 = labelScoreTeam1; }

    public JLabel getLabelScoreTeam2() { return labelScoreTeam2; }
    public void setLabelScoreTeam2(JLabel labelScoreTeam2) { this.labelScoreTeam2 = labelScoreTeam2; }
}
