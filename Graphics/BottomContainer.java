package Graphics;

import javax.swing.*;
import java.awt.*;
import Game.Game;

public class BottomContainer extends JPanel {

    public BottomContainer (Game game) {
        setBounds(0, GameWindow.HEIGHT, 1000, 100);
        add(new DiceContainer(game.dice1, 300, 10));
        add(new DiceContainer(game.dice2, 400, 10));
        add(new DiceContainer(game.dice3, 500, 10));
        add(new DiceContainer(game.dice4, 600, 10));
    }

    public void paintComponent (Graphics g) {
        g.drawImage(ResourceManager.getBottomImage(), 0, 0, 1000, 100, null);
    }
}
