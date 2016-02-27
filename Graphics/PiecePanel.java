package Graphics;

import javax.swing.*;


public class PiecePanel extends JPanel {
    private int column;

    public  PiecePanel (int column) {
        this.column = column;
    }

    public int getColumn () {
        return column;
    }
}
