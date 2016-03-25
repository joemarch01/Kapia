package Graphics;

import Board.Board;
import Game.Game;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JLabel {

    private Board board;
    private FinishZoneContainer whiteFinishZone;
    private FinishZoneContainer blackFinishZone;

    public BoardPanel (ImageIcon imageIcon, Game game) {

        super(new ImageIcon(imageIcon.getImage().getScaledInstance(GameWindow.WIDTH, GameWindow.HEIGHT, Image.SCALE_DEFAULT)));
        setBounds(0, 0, GameWindow.WIDTH + 400, GameWindow.HEIGHT);

        this.board = game.getBoard();

        for (int i = 0; i < Board.SIZE; i ++) {
            add(new ColumnContainer(game.getEventConstructor(), i, board.getColumn(i)));
        }

        whiteFinishZone = new FinishZoneContainer(game.getEventConstructor(), true);
        blackFinishZone = new FinishZoneContainer(game.getEventConstructor(), false);

        add(new BarContainer(game.getEventConstructor(), board.getWhiteBar(), true));
        add(new BarContainer(game.getEventConstructor(), board.getBlackBar(), false));
        add(whiteFinishZone);
        add(blackFinishZone);
        add(new BottomContainer(game));
    }

    public void paintComponent (Graphics g) {
        whiteFinishZone.setStackSize(15 - board.numberOfWhitePieces);
        blackFinishZone.setStackSize(15 - board.numberOfBlackPieces);

        g.drawImage(ResourceManager.getBoardImage(), 100, 0, GameWindow.WIDTH, GameWindow.HEIGHT, null);
    }

}
