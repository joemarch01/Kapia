package Graphics;

import javax.swing.*;

import Board.*;
import Game.*;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameWindow extends JFrame implements WindowListener {
    private Game game;

    static int WIDTH = 800;
    static int HEIGHT = 500;

    private BoardPanel boardPanel;

    public GameWindow (Game game) {
        this.game = game;
        this.setVisible(true);
        this.setBounds(0, 0, WIDTH + 200, HEIGHT + 120);
        this.setResizable(false);
        this.addWindowListener(this);
        game.setWindow(this);
        boardPanel = new BoardPanel(new ImageIcon(ResourceManager.getBoardImage()), game);
        getContentPane().add(boardPanel);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        game.exit();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
