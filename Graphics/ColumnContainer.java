package Graphics;

import javax.swing.*;
import java.awt.event.MouseMotionListener;

public class ColumnContainer extends JPanel {

    int column;

    ColumnContainer(int column){
        setBounds(0,0,GameWindow.WIDTH/12, GameWindow.HEIGHT/12);
        addMouseListener(new ColumnInputListener(column));
    }

}
