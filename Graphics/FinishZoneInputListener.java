package Graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FinishZoneInputListener implements MouseListener {
    public void mouseEntered(MouseEvent event){

    }

    public void mouseExited (MouseEvent event) {

    }

    public void mouseClicked (MouseEvent event) {
        System.out.println("Finish Zone Clicked");
        MouseEventConstructor.registerFinishZoneInput();
    }

    public void mousePressed (MouseEvent event) {

    }

    public void mouseReleased (MouseEvent event) {

    }
}
