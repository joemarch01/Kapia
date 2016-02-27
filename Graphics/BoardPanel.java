package Graphics;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    public void paintComponent (Graphics g) {
        g.drawImage(ResourceManager.getBoardImage(), 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
        paintChildren(g);
    }
}
