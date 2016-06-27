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
public class pBlackjack extends JPanel{
    /* ATTRIBUTES */
    private fMain window;
    private JButton buttonNewGame; // Bouton pour commencer une nouvelle partie
    private JButton buttonResign; // Bouton pour abandonner la partie en cours
    private JButton buttonDrop; // Bouton pour se coucher
    private JButton buttonDraw; // Bouton pour piocher
    private JButton buttonBack; // BOuton pour retourner au choix du jeu
    private JLabel labelMessage; // Affiche les messages à l'utilisateur
    private pBlackjackBoard board; // Plateau de jeu

    
    /* CONSTRUCTOR */
    public pBlackjack(fMain window) {
        this.window = window;
        setLayout(null);
        setBackground(new Color(21,21,21));
        setPreferredSize(new Dimension(640,480));
        
        board = new pBlackjackBoard(this);
        
        add(board);
        add(buttonNewGame);
        add(buttonResign);
        add(buttonDrop);
        add(buttonDraw);
        add(buttonBack);
        add(labelMessage);
        
        buttonNewGame.setBounds(667,20,120,30);
        buttonResign.setBounds(667,55,120,30);
        buttonDrop.setBounds(667,400,120,30);
        buttonDraw.setBounds(667,435,120,30);
        buttonBack.setBounds(667,535,120,30);
        labelMessage.setBounds(0,521,660,50);
        board.setBounds(0,0,660,521);
    }
    
    /* GETTERS & SETTERS */
    public fMain getWindow() {
        return window;
    }
    public void setWindow(fMain window) {
        this.window = window;
    }
    public JButton getButtonNewGame() {
        return buttonNewGame;
    }
    public void setButtonNewGame(JButton buttonNewGame) {
        this.buttonNewGame = buttonNewGame;
    }
    public JButton getButtonResign() {
        return buttonResign;
    }
    public void setButtonResign(JButton buttonResign) {
        this.buttonResign = buttonResign;
    }
    public JButton getButtonDrop() {
        return buttonDrop;
    }
    public void setButtonDrop(JButton buttonDrop) {
        this.buttonDrop = buttonDrop;
    }
    public JButton getButtonDraw() {
        return buttonDraw;
    }
    public void setButtonDraw(JButton buttonDraw) {
        this.buttonDraw = buttonDraw;
    }
    public JLabel getLabelMessage() {
        return labelMessage;
    }
    public void setLabelMessage(JLabel message) {
        this.labelMessage = message;
    }
    public JButton getButtonBack() {
        return buttonBack;
    }
    public void setButtonBack(JButton buttonBack) {
        this.buttonBack = buttonBack;
    }   
    public pBlackjackBoard getBoard() {
        return board;
    }
    public void setBoard(pBlackjackBoard board) {
        this.board = board;
    }
}
