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
            boardImage =  ImageIO.read(new File("../Board.png"));
            whitePieceImage = ImageIO.read(new File("../WhitePiece1.png"));
            blackPieceImage = ImageIO.read(new File("../BlackPiece1.png"));
            barImage = ImageIO.read(new File("../Bar.png"));
            dice[0] = ImageIO.read(new File("../Dice1.png"));
            dice[1] = ImageIO.read(new File("../Dice2.png"));
            dice[2] = ImageIO.read(new File("../Dice3.png"));
            dice[3] = ImageIO.read(new File("../Dice4.png"));
            dice[4] = ImageIO.read(new File("../Dice5.png"));
            dice[5] = ImageIO.read(new File("../Dice6.png"));
            bottomImage = ImageIO.read(new File("../BottomPiece.png"));
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
