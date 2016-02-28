package Graphics;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColumnInputListener implements MouseListener {

    int column;

    public ColumnInputListener (int column) {
        this.column = column;
    }

    public void mouseEntered(MouseEvent event){

    }

    public void mouseExited (MouseEvent event) {

    }

    public void mouseClicked (MouseEvent event) {
        MouseEventConstructor.registerColumnInput(column);
    }

    public void mousePressed (MouseEvent event) {

    }

    public void mouseReleased (MouseEvent event) {

    }

}
