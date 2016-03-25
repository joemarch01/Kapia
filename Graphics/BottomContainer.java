package Graphics;

import javax.swing.*;
import java.awt.*;

import Board.WhitePiece;
import Game.Game;

public class BottomContainer extends JPanel {

    Game game;

    public BottomContainer (Game game) {
        this.game = game;
        setBounds(0, GameWindow.HEIGHT, 1000, 100);
        add(new DiceContainer(game.dice1, 300, 10));
        add(new DiceContainer(game.dice2, 400, 10));
        add(new DiceContainer(game.dice3, 500, 10));
        add(new DiceContainer(game.dice4, 600, 10));
    }

    public void paintComponent (Graphics g) {
        g.drawImage(ResourceManager.getBottomImage(), 0, 0, 1000, 100, null);
        g.drawImage(ResourceManager.getBlackPieceImage(), 725, 25, 50, 50, null);
        g.drawImage(ResourceManager.getWhitePieceImage(), 800, 25, 50, 50, null);

        g.setColor(new Color(0, 0, 0, 0.9f));

        if (game.getCurrentPlayer().isWhite()) {
            g.fillOval(724, 24, 52, 52);
        } else {
            g.fillOval(799, 24, 52, 52);
        }

    }
}
