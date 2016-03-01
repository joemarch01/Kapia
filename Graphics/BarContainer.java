package Graphics;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import Board.*;

/**
 * Created by jm360 on 29/02/16.
 */
public class BarContainer extends JPanel {
    Stack<Piece> stack;

    public BarContainer (Stack<Piece> stack, boolean isWhite) {
        this.stack = stack;
        if (isWhite) {
            setBounds(0, 0, 100, GameWindow.HEIGHT/2);
        } else {
            setBounds(0, GameWindow.HEIGHT/2, 100, GameWindow.HEIGHT/2);
        }
        addMouseListener(new BarInputListener());
    }

    public void paintComponent (Graphics g) {
        for (int i = 0; i < stack.size(); i ++) {
            if (stack.peek() instanceof WhitePiece) {
                g.drawImage(ResourceManager.getWhitePieceImage(), 0, i*25, 50, 50, null);
            } else {
                g.drawImage(ResourceManager.getBlackPieceImage(), 0, i*25, 50, 50, null);
            }
        }
    }
}
