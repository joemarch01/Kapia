package Graphics;

import Board.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

public class ColumnContainer extends JPanel {

    int column;
    boolean clicked;
    Stack<Piece> stack;

    ColumnContainer(int column, Stack<Piece> stack){
        super();
        if (column < Board.SIZE / 2) {
            setBounds((int)(GameWindow.WIDTH - GameWindow.WIDTH*((column + 1) / 12.0)), GameWindow.HEIGHT/2, GameWindow.WIDTH/12, GameWindow.HEIGHT/2);
        } else {
            setBounds((int)(GameWindow.WIDTH - GameWindow.WIDTH*((Board.SIZE - column)/12.0)), 0, GameWindow.WIDTH/12, GameWindow.HEIGHT/2);

        }
        this.stack = stack;
        this.column = column;
        setOpaque(false);
        addMouseListener(new ColumnInputListener(column));
    }

    public void paintComponent (Graphics g) {
        for (int i = 0; i < stack.size(); i ++) {
            if (column < Board.SIZE / 2) {
                if (stack.peek() instanceof WhitePiece) {
                    g.drawImage(ResourceManager.getWhitePieceImage(),0, (GameWindow.HEIGHT / 2) - (25 * i) - 50, 50, 50, null);
                } else {
                    g.drawImage(ResourceManager.getBlackPieceImage(),0, (GameWindow.HEIGHT / 2) - (25 * i) - 50, 50, 50, null);
                }
            } else {
                if (stack.peek() instanceof WhitePiece) {
                    g.drawImage(ResourceManager.getWhitePieceImage(),0,25 * i, 50, 50, null);
                } else {
                    g.drawImage(ResourceManager.getBlackPieceImage(),0, 25 * i, 50, 50, null);
                }
            }
        }
    }

}
