/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.panels;

import fr.chidiaclucas.jeuxblackjackbelote.app.Card;
import fr.chidiaclucas.jeuxblackjackbelote.app.Team;
import fr.chidiaclucas.jeuxblackjackbelote.app.GameBlackjack;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Maxime
 */
public class pBlackjackBoard extends JPanel implements ActionListener{
    private final pBlackjack panel;
    private final int boardWidth = 660;
    private final int boardHeight = 521;
    
    private GameBlackjack board = null;
    private boolean gameInProgress;
    private int nbPlayers = 4;
    private Image cardImage;
    private int currentPlayer = 0;
    
    public pBlackjackBoard(pBlackjack panel) {
        this.panel = panel;
        
        /* Mise en forme graphique du Panel */
        this.setBackground(Color.WHITE);
        
        /* On définie les différents composants */
        this.panel.setLabelMessage(new JLabel("Cliquez sur 'Nouvelle partie' pour commencer !", JLabel.CENTER));
        this.panel.setButtonNewGame(new JButton("Nouvelle partie"));
        this.panel.setButtonResign(new JButton("Abandonner"));
        this.panel.setButtonDraw(new JButton("Piocher"));
        this.panel.setButtonDrop(new JButton("Se coucher"));
        this.panel.setButtonBack(new JButton("Retour"));
        
        /* Mise en forme graphique du Label */
        this.panel.getLabelMessage().setBackground(new Color(51,51,51));
        this.panel.getLabelMessage().setForeground(new Color(37,134,209));
        this.panel.getLabelMessage().setOpaque(true);
        this.panel.getLabelMessage().setFont(new Font("SansSerif", Font.BOLD, 15));
        
        /* Initialisation de l'accessibilité aux boutons */
        this.panel.getButtonNewGame().setEnabled(true);
        this.panel.getButtonResign().setEnabled(false);
        
        /* On assigne aux boutons un Listener */
        this.panel.getButtonBack().addActionListener(this);
        this.panel.getButtonNewGame().addActionListener(this);
        this.panel.getButtonResign().addActionListener(this);
        this.panel.getButtonDraw().addActionListener(this);
        this.panel.getButtonDrop().addActionListener(this);
        
        /* Par défaut, on n'affiche pas les boutons de jeu */
        this.panel.getButtonDraw().setVisible(false);
        this.panel.getButtonDrop().setVisible(false);
        
        /* On charge l'image des cartes */
        loadImage();
    }
    public void doNewGame() {
        // Si un jeu est en cours, il est impossible d'en commencer un nouveau
        if(gameInProgress)
        {
            this.panel.getLabelMessage().setText("Une partie est déjà en cours. Vous ne pouvez pas en commencer une nouvelle !");
            return;
        }
        
        // On rend inaccessible le bouton pour commencer une nouvelle partie, et réciproquement avec celui pour abandonner
        this.panel.getButtonNewGame().setEnabled(false);
        this.panel.getButtonResign().setEnabled(true);
        
        // On démarre un jeu
        gameInProgress = true;
        
        // On demande des données à l'utilisateur
        boolean check; 
        int nbPaquets = 0;
        int nbJoueurs = 0;
        
        do {
            check = true;
            try {
                String str = JOptionPane.showInputDialog(null,"Combien souhaitez-vous utiliser de paquets de 52 cartes");
                nbPaquets = Integer.parseInt(str);
            }
            catch(NumberFormatException ec){
                check = false;
            }
            
            if(!check || nbPaquets <= 0) {
                this.panel.getLabelMessage().setText("[Erreur dans la saisie] Saisissez un entier > 0"); 
                check = false;
            }
        } while (check == false);
        
        do {
            check = true;
            try {
                String str = JOptionPane.showInputDialog(null,"Combien de joueurs souhaitent jouer ? (Sans compter la banque)");
                nbJoueurs = Integer.parseInt(str);
            }
            catch(NumberFormatException ec){
                check = false;
            }
            
            if(!check || nbJoueurs < 1 || nbJoueurs > 4) {
                this.panel.getLabelMessage().setText("[Erreur dans la saisie] Saisissez un 0 < entier < 5 "); 
                check = false;
            }
        } while (check == false);
        
        // On commence une nouvelle partie avec ces données
        board = new GameBlackjack(nbPaquets, nbJoueurs); 
        
        this.nbPlayers = nbJoueurs;
        repaint();
        
        
        
        doNewRound();
    }
    public void doResign() {
        if(!gameInProgress)
        {
            this.panel.getLabelMessage().setText("Aucune partie n'est en cours. Vous ne pouvez donc pas déjà abandonner !");
            return;
        }
        gameOver("Un joueur a décidé de mettre fin au jeu. La partie est terminée !");
    }
    public void doBack() {
        panel.getWindow().getContentPane().removeAll();
        panel.getWindow().getContentPane().revalidate();
        panel.getWindow().getContentPane().repaint();
        panel.getWindow().printPGameChoice();
    }
    public void doDraw(){
        if(!gameInProgress) {
            this.panel.getLabelMessage().setText("Aucune partie en cours ! Opération impossible");
            return;
        }
        
        Team e = board.getListTeams().get(currentPlayer);
        // Si le joueur pioche alors qu'il a plus de 21, alors il se couche automatiquement
        if(e.getListPlayers().get(0).calculateHand() >= 21) {
            doDrop();
            return;
        }
        board.givePlayerCard(e,true);
        repaint();
        if(e.getListPlayers().get(0).calculateHand() >= 21) {
            doDrop();
            return;
        }
    }
    public void doDrop(){
        if(!gameInProgress) {
            this.panel.getLabelMessage().setText("Aucune partie en cours ! Opération impossible");
            return;
        }
        Team e = board.getListTeams().get(currentPlayer);
        JOptionPane.showMessageDialog(null, "Votre score est de : "+e.getListPlayers().get(0).calculateHand()+"\n Passage au joueur suivant !");
        this.panel.getLabelMessage().setText(e.getName()+", votre tour est terminé !");
        currentPlayer++;
        
        if(board.getListTeams().get(currentPlayer).isBank()) {
            doBankRound();
        } 
        else
        {
            this.panel.getLabelMessage().setText(board.getListTeams().get(currentPlayer).getName()+", C'est votre tour ! Que voulez-vous faire ? -->");
        }
        
    }
    public void doNewRound() {
        currentPlayer = 0;
        
        resetBet();
        
        // On commence un nouveau tour
        for(int i = 1 ; i <= nbPlayers ; i++) {
            Team e = board.getListTeams().get(i-1);
            int mise = 0;
            boolean check;
            
            do {
                check = true;
                try {
                    JOptionPane optionPane = new JOptionPane(e.getName()+" :\n Vous disposez de : "+e.getScore()+" €\n Combien voulez-vous miser ?"
                            , JOptionPane.PLAIN_MESSAGE
                            , JOptionPane.DEFAULT_OPTION
                            , null, null, "");
                    optionPane.setWantsInput(true);
                    JDialog dialog = optionPane.createDialog(null, "Mise du joueur");
                    
                    int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
                    int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
                    int windowWidth = this.panel.getWindow().getWidth();
                    int windowHeight = this.panel.getWindow().getHeight();
                    int dialogX = screenWidth/2 - windowWidth/2 + 660 - dialog.getWidth() - 20;
                    int dialogY = screenHeight/2 - windowHeight/2 + 50;
                    dialog.setLocation(dialogX, dialogY);
                    dialog.setVisible(true);
                    String str = (String) optionPane.getInputValue();
                    mise = Integer.parseInt(str);
                }
                catch(NumberFormatException ec){
                    check = false;
                }

                if(!check || mise <= 0 || mise > e.getScore()) {
                    this.panel.getLabelMessage().setText("[Erreur dans la saisie] Saisissez un entier > 0 et inférieur à votre balance"); 
                    check = false;
                }
            } while (check == false);
            
            e.setBet(mise);
            repaint();
        }
        
        board.distributeFirstCards();
        board.distributeSecondCards();
        toggleGameButtons();
        repaint();
        
        // On commence avec le premier joueur
        this.panel.getLabelMessage().setText(board.getListTeams().get(currentPlayer).getName()+", C'est votre tour ! Que voulez-vous faire ? -->");    
    }
    public void doBankRound() {
        Team e = board.getListTeams().get(currentPlayer);
        while(e.getListPlayers().get(0).calculateHand() <= 16)
        {
            board.givePlayerCard(e,true);
        }
        e.getListPlayers().get(0).showCards();
        this.panel.getLabelMessage().setText("Banque, votre tour est terminé !");
        doEndOfTurn();
        return;
    }
    public void doEndOfTurn() {
        toggleGameButtons();
        board.getListTeams().get(nbPlayers).getListPlayers().get(0).showCards();
        repaint();
        String endMessage = board.getWinners();
        endMessage += "Voulez-vous faire un nouveau tour ?";
        
        int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int windowWidth = this.panel.getWindow().getWidth();
        int windowHeight = this.panel.getWindow().getHeight();
        int dialogX = screenWidth/2 + windowWidth/2;
        int dialogY = screenHeight/2 - windowHeight/2;
        
        JOptionPane optionPane = new JOptionPane(endMessage, JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION, null, null, "");
        JDialog dialog = optionPane.createDialog(null, "Fin du tour !");
        dialog.setLocation(dialogX + 10, dialogY);
        dialog.setVisible(true);
        int reply = (int) optionPane.getValue();
        if (reply == JOptionPane.YES_OPTION) {
            board.resetPlayerHand();
            repaint();
            doNewRound();
            return;
        }
        board.resetPlayerHand();
        resetBet();
        repaint();
        this.panel.getLabelMessage().setText("Fin de la partie");
    }
    public void resetBet() {
        // Réinitialisation des mises
        for(int i = 1 ; i <= nbPlayers ; i++) {
            board.getListTeams().get(i-1).setBet(0);
        }
    }
    public void gameOver(String str) {
        this.panel.getLabelMessage().setText(str);
        this.panel.getButtonNewGame().setEnabled(true);
        this.panel.getButtonResign().setEnabled(false);
        toggleGameButtons();
        gameInProgress = false;
    }
    private void toggleGameButtons() {
        this.panel.getButtonDraw().setVisible(!this.panel.getButtonDraw().isVisible());
        this.panel.getButtonDrop().setVisible(!this.panel.getButtonDrop().isVisible());
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        /* Initialisation de l'environnement graphique : Meilleur rendu grâce à Graphics2D */
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
        
        /* Dessin du plateau du jeu */
        int margin = 10;
        // Bords du plateau
        g2.setColor(new Color(21,21,21));
        g2.drawRect(margin, margin, boardWidth - 2*margin, boardHeight - 2*margin);
        g2.drawRect(margin+1, margin+1, boardWidth - 2*margin, boardHeight - 2*margin);
        // Intérieur du plateau
        g2.setColor(new Color(62,140,72));
        g2.fillRect(margin+1, margin+1, boardWidth - (2*margin+1), boardHeight - (2*margin+1));
        
        /* Affichage des éléments du plateau :
        - Noms des joueurs
        - Mises
        - Balance personnelle
        - Cartes dans la main
        */
        FontMetrics metrics;
        Font font;
        
        // On définit la police, et on récupére un objet metrics de cette police : pour aligner les textes par la suite
        font = new Font("SansSerif", Font.BOLD, 12);
        metrics = g.getFontMetrics(font);
        g2.setFont(font);
        
        // Données utiles
        int widthCard = 79;
        int heightCard = 123;
        int milieuEmplacementX = 660/(nbPlayers+1);
        
        // Affichage de l'emplacement du croupier, et de ses cartes
        int posEmplacementCroupierX = milieuEmplacementX - widthCard/2;
        int posEmplacementCroupierY = 50;
        int posCarteCroupierX = posEmplacementCroupierX;
        int posCarteCroupierY = posEmplacementCroupierY + 7;
        
        g2.setColor(Color.BLACK);
        g2.drawRect(posCarteCroupierX - 1,posCarteCroupierY - 1, widthCard + 1, heightCard + 1); // Les - 1 et + 1 permettent de grossir l'emplacement d'1 pixel pour qu'il dépasse de la carte
        String str = "Banque";
        int widthStringCroupierX = metrics.stringWidth(str);
        int posStringCroupierX = posEmplacementCroupierX + widthCard / 2 - widthStringCroupierX / 2;
        
        g2.setColor(new Color(21,21,21));
        g2.drawString(str, posStringCroupierX ,posEmplacementCroupierY);
        
        if(board != null)
        {
            int j = 0;
            int pas = 20;
            for(Card c : board.getListTeams().get(nbPlayers).getListPlayers().get(0).getHand()) {
                drawCard(g2, c, posCarteCroupierX + j * pas, posCarteCroupierY);
                j++;
            }
        }
        
        for(int i = 1; i <= nbPlayers ; i++) {
            int posEmplacementX = milieuEmplacementX * i - widthCard/2;
            int posEmplacementY = 220;
            
            int posCarteX = posEmplacementX;
            int posCarteY = posEmplacementY + 40;
            
            g2.setColor(Color.BLACK);
            g2.drawRect(posCarteX - 1,posCarteY - 1, widthCard + 1, heightCard + 1); // Les - 1 et + 1 permettent de grossir l'emplacement d'1 pixel pour qu'il dépasse de la carte
            
            // Si la board n'est pas nulle, ça veut dire qu'on a commencé une nouvelle partie, et donc on affiche les joueurs et leurs cartes
            if(board != null)
            {
                // On récupère le joueur courant
                Team e = board.getListTeams().get(i-1);
                
                // On cherche à centrer le 'Nom' du joueur
                int widthStringX = metrics.stringWidth(board.getListTeams().get(i-1).getName());
                int posStringX = posEmplacementX + widthCard / 2 - widthStringX / 2;
                
                g2.setColor(new Color(21,21,21));
                g2.drawString(e.getName(), posStringX ,posEmplacementY);
                
                // On cherche à centrer la 'Balance' du joueur
                String balance = "Balance : " + board.getListTeams().get(i-1).getScore() + " €";
                int widthBalanceX = metrics.stringWidth(balance); 
                int posBalanceX = posEmplacementX + widthCard / 2 - widthBalanceX / 2;
                
                g2.setColor(new Color(255,255,255));
                g2.drawString(balance, posBalanceX ,posEmplacementY + 17);
                
                // On cherche à centrer la 'Mise' du joueur
                String mise = "Mise : " + board.getListTeams().get(i-1).getBet() + " €";
                int widthMiseX = metrics.stringWidth(mise); 
                int posMiseX = posEmplacementX + widthCard / 2 - widthMiseX / 2;
                
                g2.setColor(new Color(255,255,255));
                g2.drawString(mise, posMiseX ,posEmplacementY + 30);
                
                // Affichage des cartes, si la main n'est pas vide
                if(!e.getListPlayers().get(0).getHand().isEmpty()) {
                    int j = 0;
                    int pas = 31;
                    for(Card c : e.getListPlayers().get(0).getHand()) {
                        drawCard(g2, c, posCarteX, posCarteY + j * pas);
                        j++;
                    }
                }
            }
        }
    }
    private void drawCard(Graphics g, Card c, int x, int y) {
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
        g.drawImage(cardImage,x,y,x+79, y + 123, cx, cy, cx + 79, cy + 123, this);
    }
    private void loadImage() {
        try {
            cardImage = ImageIO.read(new File("../../GUI/IMG/cards.png"));
        } catch (IOException ex) {
            Logger.getLogger(pBeginning.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getActionCommand();
        if(src == "Retour") {
            doBack();
        }
        else if(src == "Nouvelle partie")
        {
            doNewGame();
        }
        else if(src == "Abandonner")
        {
            doResign();
        }
        else if(src == "Piocher")
        {
            doDraw();
        }
        else if(src == "Se coucher")
        {
            doDrop();
        }
    }
}
