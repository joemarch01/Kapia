package Graphics;

import Board.Board;

import java.awt.*;

public class WhitePiecePanel extends PiecePanel {

    public WhitePiecePanel (int column) {
        super(column);
    }

    public void paintComponent (Graphics g) {
        if (getColumn() < Board.SIZE/2) {
            //Draw at top of screen
            g.drawImage(ResourceManager.getWhitePieceImage(), 0, 0, 100, 100, null);
        } else {
            //Draw at bottom of screen
            g.drawImage(ResourceManager.getWhitePieceImage(), GameWindow.WIDTH - (GameWindow.WIDTH / (getColumn() + 1)), GameWindow.HEIGHT - (GameWindow.HEIGHT / Board.SIZE), GameWindow.WIDTH / Board.SIZE, GameWindow.HEIGHT / Board.SIZE, null);
        }
    }
}
