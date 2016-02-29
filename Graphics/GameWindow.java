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
        this.setBounds(0, 0, WIDTH, HEIGHT + 100);
        game.setWindow(this);
        boardPanel = new BoardPanel(new ImageIcon(ResourceManager.getBoardImage()), game.getBoard());
        getContentPane().add(boardPanel);
    }



}
