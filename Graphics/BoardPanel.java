package Graphics;

import Board.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JLabel {

    Board board;

    public BoardPanel (ImageIcon imageIcon, Board board) {

        super(new ImageIcon(imageIcon.getImage().getScaledInstance(GameWindow.WIDTH, GameWindow.HEIGHT, Image.SCALE_DEFAULT)));
        setBounds(0, 0, GameWindow.WIDTH, GameWindow.HEIGHT);

        this.board = board;

        for (int i = 0; i < Board.SIZE; i ++) {
            add(new ColumnContainer(i, board.getColumn(i)));
        }

        add(new BarContainer(board.getWhiteBar(), true));
        add(new BarContainer(board.getBlackBar(), false));
    }

    public void paintComponent (Graphics g) {
        g.drawImage(ResourceManager.getBoardImage(), 0, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
    }

}
