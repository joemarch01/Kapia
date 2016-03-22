package Graphics;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FinishZoneContainer extends JPanel implements MouseListener {
    boolean isWhite;
    int stackSize;
    MouseEventConstructor eventConstructor;

    public FinishZoneContainer (MouseEventConstructor eventConstructor, boolean isWhite) {
        this.isWhite = isWhite;
        this.stackSize = 0;
        this.eventConstructor = eventConstructor;
        if (isWhite) {
            setBounds(GameWindow.WIDTH + 100, 0, 100, GameWindow.HEIGHT/2);
        } else {
            setBounds(GameWindow.WIDTH + 100, GameWindow.HEIGHT / 2, 100, GameWindow.HEIGHT/2);
        }
        addMouseListener(this);
    }

    public void setStackSize (int stackSize) {
        this.stackSize = stackSize;
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

    @Override
    public void mouseClicked(MouseEvent e) {
        eventConstructor.registerFinishZoneInput();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
