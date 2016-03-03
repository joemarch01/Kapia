package Graphics;

import javax.imageio.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.awt.image.BufferedImage;

public class ResourceManager {

    private static BufferedImage boardImage;
    private static BufferedImage whitePieceImage;
    private static BufferedImage blackPieceImage;
    private static BufferedImage barImage;
    private static BufferedImage dice[] = new BufferedImage[6];
    private static BufferedImage bottomImage;

    public static void load () {
        try {
            boardImage =  ImageIO.read(new File("../Images/Board.png"));
            whitePieceImage = ImageIO.read(new File("../Images/WhitePiece.png"));
            blackPieceImage = ImageIO.read(new File("../Images/BlackPiece.png"));
            barImage = ImageIO.read(new File("../Images/Bar.png"));
            dice[0] = ImageIO.read(new File("../Images/Dice1.png"));
            dice[1] = ImageIO.read(new File("../Images/Dice2.png"));
            dice[2] = ImageIO.read(new File("../Images/Dice3.png"));
            dice[3] = ImageIO.read(new File("../Images/Dice4.png"));
            dice[4] = ImageIO.read(new File("../Images/Dice5.png"));
            dice[5] = ImageIO.read(new File("../Images/Dice6.png"));
            bottomImage = ImageIO.read(new File("../Images/BottomPiece.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static BufferedImage getBoardImage () {
        return boardImage;
    }

    public static BufferedImage getBarImage () {
        return barImage;
    }

    public static BufferedImage getWhitePieceImage () {
        return whitePieceImage;
    }

    public static BufferedImage getBlackPieceImage () {
        return blackPieceImage;
    }

    public static BufferedImage getDiceImage (int n) {
        if (n < 0 || n >= dice.length) {
            return null;
        }
        return dice[n];
    }

    public static BufferedImage getBottomImage () {
        return bottomImage;
    }
}
