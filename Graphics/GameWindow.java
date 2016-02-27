package Graphics;

import javax.swing.*;

import Board.*;
import Game.*;

import java.awt.*;

public class GameWindow extends JFrame {
    private Game game;

    static int WIDTH = 800;
    static int HEIGHT = 500;

    private BoardPanel boardPanel;

    public GameWindow (Game game) {
        this.game = game;
        boardPanel = new BoardPanel();
        this.getContentPane().add(boardPanel);
        this.setVisible(true);
        this.setBounds(0, 0, WIDTH, HEIGHT);
        game.setWindow(this);
    }

    public void paint (Graphics g) {
        super.paint(g);
        boardPanel.removeAll();
        getContentPane().removeAll();
        getContentPane().add(boardPanel);
        for (int i = 0; i < Board.SIZE; i ++) {
            for (int j = 0; j < game.getBoard().getColumn(i).size(); j ++) {
                if (game.getBoard().getColumn(i).peek() instanceof WhitePiece) {
                    getContentPane().add(new WhitePiecePanel(i));
                }
            }
        }
    }


}
