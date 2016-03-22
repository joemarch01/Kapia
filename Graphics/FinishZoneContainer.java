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
        Image pieceImage;
        if (isWhite) {
            pieceImage = ResourceManager.getWhitePieceImage();
        } else {
            pieceImage = ResourceManager.getBlackPieceImage();
        }

        for (int i = 0; i < stackSize; i ++) {
            g.drawImage(pieceImage, 25, i * 14, 50, 50, null);
        }
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
