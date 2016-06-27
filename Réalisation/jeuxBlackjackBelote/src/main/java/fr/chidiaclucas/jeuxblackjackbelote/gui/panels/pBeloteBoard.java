/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.chidiaclucas.jeuxblackjackbelote.gui.panels;

import fr.chidiaclucas.jeuxblackjackbelote.app.Card;
import fr.chidiaclucas.jeuxblackjackbelote.app.Team;
import fr.chidiaclucas.jeuxblackjackbelote.app.Player;
import fr.chidiaclucas.jeuxblackjackbelote.app.GameBelote;
import fr.chidiaclucas.jeuxblackjackbelote.gui.frames.fHandBelote;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Kevin
 */
public class pBeloteBoard extends JPanel implements ActionListener, MouseListener, WindowListener
{
    private pBelote panel;
    private GameBelote board = null;
    private boolean gameInProgress;
//    private boolean nextPlayerTurn = false;
    private int nbTrickPlayed;
            
    private int boardWidth = 660;
    private int boardHeight = 521;
    private Image cardImage;
    
    private fHandBelote showHand;
    
    /*** CONSTRUCTOR ***/
    public pBeloteBoard(pBelote panel) {
        this.panel = panel;
        
        /* Mise en forme graphique du Panel */
        this.setBackground(Color.WHITE);
        
        /* On définit les différents composants */
        this.panel.setLabelMessage(new JLabel("Cliquez sur 'Nouvelle partie' pour commencer !", JLabel.CENTER));
        this.panel.setButtonNewGame(new JButton("Nouvelle partie"));
        this.panel.setButtonResign(new JButton("Abandonner"));
        this.panel.setButtonDraw(new JButton("Prendre"));
        this.panel.setButtonPass(new JButton("Passer"));
        this.panel.setButtonBack(new JButton("Retour"));
        this.panel.setButtonSpades(new JButton(new ImageIcon("../../GUI/IMG/spades.png")));
        this.panel.setButtonHearts(new JButton(new ImageIcon("../../GUI/IMG/hearts.png")));
        this.panel.setButtonClubs(new JButton(new ImageIcon("../../GUI/IMG/clubs.png")));
        this.panel.setButtonDiamonds(new JButton(new ImageIcon("../../GUI/IMG/diamonds.png")));
        
        this.panel.setLabelNameTeam1(new JLabel());
        this.panel.setLabelScoreTeam1(new JLabel());
        this.panel.setLabelNameTeam2(new JLabel());
        this.panel.setLabelScoreTeam2(new JLabel());
        
        /* Mise en forme graphique du Label */
        this.panel.getLabelMessage().setBackground(new Color(51,51,51));
        this.panel.getLabelMessage().setForeground(new Color(37,134,209));
        this.panel.getLabelMessage().setOpaque(true);
        this.panel.getLabelMessage().setFont(new Font("SansSerif", Font.BOLD, 15));
        
        this.panel.getLabelNameTeam1().setForeground(Color.WHITE);
        this.panel.getLabelNameTeam2().setForeground(Color.WHITE);
        this.panel.getLabelScoreTeam1().setForeground(Color.WHITE);
        this.panel.getLabelScoreTeam2().setForeground(Color.WHITE);
        
        /* Initialisation de l'accessibilité aux boutons */
        this.panel.getButtonNewGame().setEnabled(true);
        this.panel.getButtonResign().setEnabled(false);
        
        /* On assigne un Listener à la souris */
        this.addMouseListener(this);
        
        /* On assigne aux boutons un Listener */
        this.panel.getButtonBack().addActionListener(this);
        this.panel.getButtonNewGame().addActionListener(this);
        this.panel.getButtonResign().addActionListener(this);
        this.panel.getButtonDraw().addActionListener(this);
        this.panel.getButtonPass().addActionListener(this);
        this.panel.getButtonSpades().addActionListener(this);
        this.panel.getButtonHearts().addActionListener(this);
        this.panel.getButtonClubs().addActionListener(this);
        this.panel.getButtonDiamonds().addActionListener(this);
        
        /* Par défaut, on désactive les boutons de jeu */
        this.panel.getButtonDraw().setEnabled(false);
        this.panel.getButtonPass().setEnabled(false);
        
        /* Par défaut, on n'affiche pas les différents choix d'atouts */
        this.panel.getButtonSpades().setVisible(false);
        this.panel.getButtonHearts().setVisible(false);
        this.panel.getButtonClubs().setVisible(false);
        this.panel.getButtonDiamonds().setVisible(false);
        
        /* On charge l'image des cartes */
        loadImage();
    }

    public GameBelote getBoard() { return board; }
    public void setBoard(GameBelote board) { this.board = board; }

    public int getNbTrickPlayed() { return nbTrickPlayed; }
    public void setNbTrickPlayed(int nbTrickPlayed) { this.nbTrickPlayed = nbTrickPlayed; }
    public void incrementNbTrickPlayed() { this.nbTrickPlayed++; }
    
//    public boolean isNextPlayerTurn() { return nextPlayerTurn; }
//    public void setNextPlayerTurn(boolean nextPlayerTurn) { this.nextPlayerTurn = nextPlayerTurn; }
    
    public void doNewGame() 
    {
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
        Player j1, j2, j3, j4;
        String nameTeam1, nameTeam2;
        
        //Données de l'équipe 1
        j1 = new Player(JOptionPane.showInputDialog(null,"Nom du 1er joueur de la 1ère équipe"));
        j2 = new Player(JOptionPane.showInputDialog(null,"Nom du 2ème joueur de la 1ère équipe"));
        nameTeam1 = JOptionPane.showInputDialog(null,"Nom de l'équipe");
        
        //Données de l'équipe 2
        j3 = new Player(JOptionPane.showInputDialog(null,"Nom du 1er joueur de la 2èmee équipe"));
        j4 = new Player(JOptionPane.showInputDialog(null,"Nom du 2ème joueur de la 2ème équipe"));
        nameTeam2 = JOptionPane.showInputDialog(null,"Nom de l'équipe");
        
        Team e1 = new Team(j1, j2, nameTeam1);
        Team e2 = new Team(j3, j4, nameTeam2);
        
        // On commence une nouvelle partie avec ces données
        board = new GameBelote(e1, e2);
        
        // On commence un nouveau tour
        //board.nouveauTour(0);
        toggleGameButtons();
        repaint();
//        while (!board.isWinner())
            doNewRound();
//        
//        JOptionPane winner = new JOptionPane();
//        String winnerName;
//        if (board.getListTeams().get(0).getScore() > 1000)
//            winnerName = board.getListTeams().get(0).getName();
//        else
//            winnerName = board.getListTeams().get(1).getName();
//        
//        winner.showMessageDialog(null, "Equipe gagnante : " + winnerName, "Félicitations !", JOptionPane.INFORMATION_MESSAGE);
//        board = null;
//        repaint();
    }
    
    public void doNewRound()
    {
        if(!this.panel.getButtonPass().isEnabled())
            toggleGameButtons();
        if(!this.panel.getButtonDraw().isVisible())
            toggleChoiceAtoutButtons();
        
        nbTrickPlayed = 0;
        board.getDeck().cutCardDeck();
        for (int i=0; i<4; i++)
            board.deal(2, (board.getDealer()+i) % 4);
        
        //2eme tour de distribution
        for (int i=0; i<4; i++)
            board.deal(3, (board.getDealer()+i) % 4);
        
        board.turnCard();
        repaint();
        board.setOpener(board.getDealer());
        board.setCurrentPlayer(board.getOpener());

        doNewTrick();
    }
    
    public void doNewTrick()
    {
        board.setCurrentPlayer(board.getOpener()); //Le 1er joueur du pli est celui qui ouvre
        JOptionPane playerInfo = new JOptionPane();
        playerInfo.showMessageDialog(null, board.getListPlayerOrdered().get(board.getCurrentPlayer()).getName() + " ! C'est à vous !", "A vous de jouer !", JOptionPane.INFORMATION_MESSAGE);
        showPlayerHand();
    }
        
    public void doPass()
    {
        this.showHand.dispose();
        //board.setCurrentPlayer((board.getCurrentPlayer()+1)%4);
        board.goToNextPlayer();
        JOptionPane playerInfo = new JOptionPane();
        
        if (board.getCurrentPlayer() == board.getDealer())
        {
            if(this.panel.getButtonDraw().isVisible())
            {
                toggleChoiceAtoutButtons();
                toggleFlushButton(board.getTurnedCard().getColor());
            }
            else
            {
                doEndRound();
                return;
            }
        }
        playerInfo.showMessageDialog(null, board.getListPlayerOrdered().get(board.getCurrentPlayer()).getName() + "! C'est à vous !", "A vous de jouer !", JOptionPane.INFORMATION_MESSAGE);
        showPlayerHand();
    }
    
    public void showPlayerHand()
    {
        this.showHand = new fHandBelote(this);
        /* On assigne un Listener à la fenêtre fille */
        this.showHand.addWindowListener(
//            new WindowListener() 
//            {
//                @Override
//                public void windowClosed(WindowEvent we) 
//                {
//                    repaint(); 
//                }
//                public void windowOpened(WindowEvent we) {}
//                public void windowClosing(WindowEvent we) {}
//                public void windowIconified(WindowEvent we) {}
//                public void windowDeiconified(WindowEvent we) {}
//                public void windowActivated(WindowEvent we) {}
//                public void windowDeactivated(WindowEvent we) {}
//            }
            new WindowHandListener(showHand)
        );
//        board.goToNextPlayer();
    }
    
    public void doEndRound()
    {
        Card tmp2;
        for (Player tmp : board.getListPlayerOrdered())
            for (int k=0; k<tmp.getHand().size(); k++)
            {
                tmp2 = tmp.pickCardFromHand(k);
                board.getDeck().getDeck().add(tmp2);
            }
        toggleChoiceAtoutButtons();
        toggleFlushButton(board.getTurnedCard().getColor());
        board.updateDealer();
        doNewRound();
    }
    
    public void dealOtherCards()
    {
        board.setPlayerWithBelote(-1);
        int taker = board.getCurrentPlayer();
        board.setTaker(taker);
        this.showHand.dispose();
        board.getListPlayerOrdered().get(taker).addCardToHand(board.getTurnedCard());
        board.setTurnedCard(null);
        
        toggleGameButtons();
        
        for (int i=0; i<4; i++)
        {
            if ((board.getDealer()+i)%4 == taker)
                board.deal(2, taker);
            else
                board.deal(3, (board.getDealer()+i)%4);
        }
        board.updateAtout();
        board.findBelote();
        repaint();
        
        board.setCurrentPlayer(board.getDealer());
        doNewTrick();
    }   
    
    public void doResign() 
    {
        if(!gameInProgress)
        {
            this.panel.getLabelMessage().setText("Aucune partie n'est en cours. Vous ne pouvez donc pas déjà abandonner !");
            return;
        }
        gameOver("Un joueur a décidé de mettre fin au jeu. La partie est terminée !");
    }
    
    public void doBack() 
    {
        panel.getWindow().getContentPane().removeAll();
        panel.getWindow().getContentPane().revalidate();
        panel.getWindow().getContentPane().repaint();
        panel.getWindow().printPGameChoice();
    }
    
    public void gameOver(String str) 
    {
        this.panel.getLabelMessage().setText(str);
        this.panel.getButtonNewGame().setEnabled(true);
        this.panel.getButtonResign().setEnabled(false);
        toggleGameButtons();
        gameInProgress = false;
    }
    
    public void toggleGameButtons() 
    {
        this.panel.getButtonDraw().setEnabled(!this.panel.getButtonDraw().isEnabled());
        this.panel.getButtonPass().setEnabled(!this.panel.getButtonPass().isEnabled());
    }
    
    public void toggleChoiceAtoutButtons() 
    {
        this.panel.getButtonDraw().setVisible(!this.panel.getButtonDraw().isVisible());
        
        this.panel.getButtonSpades().setVisible(!this.panel.getButtonSpades().isVisible());
        this.panel.getButtonHearts().setVisible(!this.panel.getButtonHearts().isVisible());
        this.panel.getButtonClubs().setVisible(!this.panel.getButtonClubs().isVisible());
        this.panel.getButtonDiamonds().setVisible(!this.panel.getButtonDiamonds().isVisible());
        
    }
    
    public void toggleFlushButton(int atout)
    {
        //On rend incliquable le bouton de la couleur de la carte
        switch (atout)
        {
            case 1:
                this.panel.getButtonDiamonds().setEnabled(!this.panel.getButtonDiamonds().isVisible());
                break;
            case 2:
                this.panel.getButtonSpades().setEnabled(!this.panel.getButtonSpades().isVisible());
                break;
            case 3:
                this.panel.getButtonHearts().setEnabled(!this.panel.getButtonHearts().isVisible());
                break;
            case 4:
                this.panel.getButtonClubs().setEnabled(!this.panel.getButtonClubs().isVisible());
                break;
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
        - Cards faces cachées
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
        
        //si la partie est lancée, on lance l'affichage du plateau
        if(board != null) 
        {
            // Affichage de l'emplacement de chaque joueur
            int posNameP1X = 660/2 - 20 - widthCard;
            int posNameP1Y = 521-20-heightCard-20;
            int posCardP1X = 20+heightCard;
            int posCardP1Y = posNameP1Y + 20;

            int posNameP2X = 20+heightCard+20;
            int posNameP2Y = 521/2;
            int posCardP2X = 20;
            int posCardP2Y = 100;

            int posNameP3X = 660 - posNameP1X;
            int posNameP3Y = 521-posNameP1Y;
            int posCardP3X = 521-posCardP1X;
            int posCardP3Y = 20;

            int posNameP4X = 660-posNameP2X;
            int posNameP4Y = posNameP2Y;
            int posCardP4X = 660-posCardP2X-heightCard;
            int posCardP4Y = 521-200;

//            g2.setColor(Color.BLACK);
//            g2.drawRect(posCardP1X - 1,posCardP1Y - 1, widthCard + 1, heightCard + 1); // Les - 1 et + 1 permettent de grossir l'emplacement d'1 pixel pour qu'il dépasse de la carte
//            g2.drawRect(posCardP2X - 1,posCardP2Y - 1, widthCard + 1, heightCard + 1);
//            g2.drawRect(posCardP3X - 1,posCardP3Y - 1, widthCard + 1, heightCard + 1);
//            g2.drawRect(posCardP4X - 1,posCardP4Y - 1, widthCard + 1, heightCard + 1);

            //Affichage du nom de chaque joueur
            String nameJ1 = board.getListPlayerOrdered().get(0).getName();
            String nameJ2 = board.getListPlayerOrdered().get(1).getName();
            String nameP3 = board.getListPlayerOrdered().get(2).getName();
            String nameP4 = board.getListPlayerOrdered().get(3).getName();
            int widthStringP4 = metrics.stringWidth(nameP4);

            g2.setColor(new Color(21,21,21));
            g2.drawString(nameJ1, posNameP1X ,posNameP1Y);
            g2.drawString(nameJ2, posNameP2X ,posNameP2Y);
            g2.drawString(nameP3, posNameP3X ,posNameP3Y);
            g2.drawString(nameP4, posNameP4X-widthStringP4 ,posNameP4Y);
            
            //Affichage du score
            String nameTeam1 = board.getListTeams().get(0).getName();
            String scoreTeam1 = Integer.toString(board.getListTeams().get(0).getScore());
            
            String nameTeam2 = board.getListTeams().get(1).getName();
            String scoreTeam2 = Integer.toString(board.getListTeams().get(1).getScore());
            
            this.panel.getLabelNameTeam1().setText(nameTeam1);
            this.panel.getLabelScoreTeam1().setText(scoreTeam1);
            this.panel.getLabelNameTeam2().setText(nameTeam2);
            this.panel.getLabelScoreTeam2().setText(scoreTeam2);
            
            // Affichage des cartes, si la main n'est pas vide
            if(!board.getListPlayerOrdered().get(0).getHand().isEmpty()) 
            {
                int j = 0;
                int pas = 31;
                
                //tri des cartes par couleur dans la main des joueurs
                for (Player tmp : board.getListPlayerOrdered())
                {
                    tmp.sortCards();
                    tmp.hideCards();
                }
                
                //Affichage
                for(Card c : board.getListPlayerOrdered().get(0).getHand()) 
                {
                    drawCard(g2, c, posCardP1X + j*pas, posCardP1Y);
                    j++;
                }
                j=0;
                for(Card c : board.getListPlayerOrdered().get(1).getHand()) 
                {
                    drawCard(g2, c, posCardP2X, posCardP2Y + j*pas);
                    j++;
                }
                j=0;
                for(Card c : board.getListPlayerOrdered().get(2).getHand()) 
                {
                    drawCard(g2, c, posCardP3X - j*pas, posCardP3Y);
                    j++;
                }
                j=0;
                for(Card c : board.getListPlayerOrdered().get(3).getHand()) 
                {
                    drawCard(g2, c, posCardP4X, posCardP4Y - j*pas);
                    j++;
                }
            }
            //Affichage de la carte centrale
            if (board.getTurnedCard() != null)
            {
                int posReverseCardX = 660/2 - widthCard/2;
                int posReverseCardY = 521/2 - heightCard/2;
                
                drawCard(g2, board.getTurnedCard(), posReverseCardX, posReverseCardY);
            }
            
            //Affichage du pli en cours
            if (board.getCurrentTrick() != null && !board.getCurrentTrick().getTrick().isEmpty())
            {
                int middleBoardX = 660/2;
                int middleBoardY = 521/2;
                int n = 0;
                
                switch(board.getOpener())
                {
                    case 0:
                        for (Card c : board.getCurrentTrick().getTrick())
                        {
                            drawCard(g2, c, middleBoardX - widthCard/2, middleBoardY);
                            if (n == 0 || n == 3)
                                middleBoardX -= widthCard/2;
                            else
                                middleBoardX += widthCard/2;
                            
                            if (n < 2)
                                middleBoardY -= heightCard/2;
                            else
                                middleBoardY += heightCard/2;
                            n++;
                        }
                        break;
                        
                    case 1:
                        for (Card c : board.getCurrentTrick().getTrick())
                        {
                            drawCard(g2, c, middleBoardX - widthCard, middleBoardY - heightCard/2);
                            if (n < 2)
                                middleBoardX += widthCard/2;
                            else
                                middleBoardX -= widthCard/2;
                            
                            if (n == 1 || n == 2)
                                middleBoardY += heightCard/2;
                            else
                                middleBoardY -= heightCard/2;
                            n++;
                        }
                        break;
                        
                    case 2:
                        for (Card c : board.getCurrentTrick().getTrick())
                        {
                            drawCard(g2, c, middleBoardX - widthCard/2, middleBoardY - heightCard);
                            if (n == 1 || n == 2)
                                middleBoardX -= widthCard/2;
                            else
                                middleBoardX += widthCard/2;
                            
                            if (n < 2)
                                middleBoardY += heightCard/2;
                            else
                                middleBoardY -= heightCard/2;
                            n++;
                        }
                        break;
                        
                    case 3:
                        for (Card c : board.getCurrentTrick().getTrick())
                        {
                            drawCard(g2, c, middleBoardX, middleBoardY - heightCard/2);
                            if (n < 2)
                                middleBoardX -= widthCard/2;
                            else
                                middleBoardX += widthCard/2;
                            
                            if (n == 1 || n == 2)
                                middleBoardY -= heightCard/2;
                            else
                                middleBoardY += heightCard/2;
                            n++;
                        }
                        break;
                        
                    default:
                        break;
                }
            }
        }
    }
    
    public void actionPerformed(ActionEvent ae) 
    {
        Object src = ae.getActionCommand();
        if(src == "Retour") {
            doBack();
        }
        else if(src == "Nouvelle partie")
            doNewGame();
        
        else if(src == "Abandonner")
            doResign();
        
        else if(src == "Passer")
            doPass();
        
        else if(src == "Prendre")
        {
            board.setAtout(board.getTurnedCard().getColor());
            dealOtherCards();  
        }
        /* Test sur la couleur demandée */
        else if (ae.getSource() == this.panel.getButtonDiamonds())
        {
            board.setAtout(1);
            dealOtherCards();
        }
        else if (ae.getSource() == this.panel.getButtonSpades())
        {
            board.setAtout(2);
            dealOtherCards();
        }
        else if (ae.getSource() == this.panel.getButtonHearts())
        {
            board.setAtout(3);
            dealOtherCards();
        }
        else if (ae.getSource() == this.panel.getButtonClubs())
        {
            board.setAtout(4);
            dealOtherCards();
        }
    }
    
    public void mouseClicked(MouseEvent me) {}
    public void mousePressed(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    
    public void windowOpened(WindowEvent we) {}
    public void windowClosing(WindowEvent we) {}
    public void windowClosed(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowActivated(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {}
}
