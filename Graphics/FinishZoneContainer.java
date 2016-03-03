package Graphics;


import javax.swing.*;
import java.awt.*;

public class FinishZoneContainer extends JPanel {
    boolean isWhite;
    int stackSize;

    public FinishZoneContainer (boolean isWhite) {
        this.isWhite = isWhite;
        this.stackSize = 0;
        addMouseListener(new FinishZoneInputListener());
        if (isWhite) {
            setBounds(GameWindow.WIDTH + 100, 0, 100, GameWindow.HEIGHT/2);
        } else {
            setBounds(GameWindow.WIDTH + 100, GameWindow.HEIGHT / 2, 100, GameWindow.HEIGHT/2);
        }
    }

    public void incrementStack () {
        stackSize ++;
    }

    public void paintComponent (Graphics g) {
        g.drawImage(ResourceManager.getBarImage(), 100, 0, -100, 250, null);
        /*if (isWhite) {
            g.setColor(Color.white);
            g.fillRect(0,0,10000,10000);
        } else {
            g.setColor(Color.black);
            g.fillRect(0,0,10000,10000);
        }*/
    }
}
