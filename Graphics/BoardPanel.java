package Graphics;

import Board.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JLabel {

    public BoardPanel (ImageIcon imageIcon) {

        super(new ImageIcon(imageIcon.getImage().getScaledInstance(GameWindow.WIDTH, GameWindow.HEIGHT, Image.SCALE_DEFAULT)));
        setBounds(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);
    }

    public void paintComponent (Graphics g) {
        g.drawImage(ResourceManager.getBoardImage(), 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
    }
}
