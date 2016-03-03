package Graphics;

import Game.Dice;

import javax.swing.*;
import java.awt.*;


public class DiceContainer extends JPanel {
    Dice dice;
    int x, y;

    public DiceContainer (Dice dice, int x, int y) {
        setLayout(null);
        this.dice = dice;
        this.x = x;
        this.y = y;
    }

    public void paintComponent (Graphics g) {
        setBounds(x, y, 80, 80);
        g.drawImage(ResourceManager.getDiceImage(dice.getValue() - 1), 0, 0, 80, 80, null);
        if (dice.used()) {
            g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.5f));
            g.fillRect(0, 0, 80, 80);
        }
    }
}
