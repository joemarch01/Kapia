package Graphics;

import Board.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

public class ColumnContainer extends JPanel implements MouseListener {

    int column;
    boolean clicked;
    Stack<Piece> stack;
    MouseEventConstructor eventConstructor;

    ColumnContainer(MouseEventConstructor eventConstructor, int column, Stack<Piece> stack){
        this.eventConstructor = eventConstructor;
        if (column < Board.SIZE / 2) {
            setBounds((int)(GameWindow.WIDTH - GameWindow.WIDTH*((column + 1) / 12.0)) + 100, GameWindow.HEIGHT/2, GameWindow.WIDTH/12, GameWindow.HEIGHT/2);
        } else {
            setBounds((int)(GameWindow.WIDTH - GameWindow.WIDTH*((Board.SIZE - column)/12.0)) + 100, 0, GameWindow.WIDTH/12, GameWindow.HEIGHT/2);

        }
        this.stack = stack;
        this.column = column;
        setOpaque(false);
        addMouseListener(this);
    }

    public void paintComponent (Graphics g) {
        for (int i = 0; i < stack.size(); i ++) {
            if (column < Board.SIZE / 2) {
                if (stack.peek() instanceof WhitePiece) {
                    g.drawImage(ResourceManager.getWhitePieceImage(),11, (GameWindow.HEIGHT / 2) - (15 * i) - 50, 50, 50, null);
                } else {
                    g.drawImage(ResourceManager.getBlackPieceImage(),11, (GameWindow.HEIGHT / 2) - (15 * i) - 50, 50, 50, null);
                }
            } else {
                if (stack.peek() instanceof WhitePiece) {
                    g.drawImage(ResourceManager.getWhitePieceImage(),11,15 * i, 50, 50, null);
                } else {
                    g.drawImage(ResourceManager.getBlackPieceImage(),11, 15 * i, 50, 50, null);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        eventConstructor.registerColumnInput(column);
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
