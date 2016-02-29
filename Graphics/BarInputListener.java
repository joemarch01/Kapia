package Graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jm360 on 29/02/16.
 */
public class BarInputListener implements MouseListener {

    public void mouseEntered(MouseEvent event){

    }

    public void mouseExited (MouseEvent event) {

    }

    public void mouseClicked (MouseEvent event) {
        System.out.println("Bar Clicked");
        MouseEventConstructor.registerBarInput();
    }

    public void mousePressed (MouseEvent event) {

    }

    public void mouseReleased (MouseEvent event) {

    }
}
