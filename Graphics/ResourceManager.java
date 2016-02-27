package Graphics;

import javax.imageio.*;
import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;

public class ResourceManager {

    public static void load () {

    }

    public static BufferedImage getBoardImage () {
        try {
            return ImageIO.read(new File("../Board.gif"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static BufferedImage getWhitePieceImage () {
        try {
            return ImageIO.read(new File("../WhitePiece.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static BufferedImage getBlackPieceImage () {
        try {
            return ImageIO.read(new File("../BlackPiece.png"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
