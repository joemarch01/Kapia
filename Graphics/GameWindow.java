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
        this.setVisible(true);
        this.setBounds(0, 0, WIDTH, HEIGHT);
        game.setWindow(this);
        boardPanel = new BoardPanel(new ImageIcon(ResourceManager.getBoardImage()));
        getContentPane().add(boardPanel);
    }

    public void paint (Graphics g) {
        super.paint(g);
        getContentPane().removeAll();
        for (int i = 0; i < Board.SIZE; i ++) {
            for (int j = 0; j < game.getBoard().getColumn(i).size(); j ++) {
                if (game.getBoard().getColumn(i).peek() instanceof WhitePiece) {
                    getContentPane().add(new WhitePiecePanel(i));
                }
            }
        }
    }


}
