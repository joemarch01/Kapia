package Event;


public class Revive extends Event {
    int to;
    boolean white;

    public Revive (int to, boolean white) {
        this.to = to;
        this.white = white;
    }

    public boolean getWhite () {
        return white;
    }

    public void setWhite (boolean white) {
        this.white = white;
    }

    public void setTo (int to) {
        this.to = to;
    }

    public int getTo () {
        return to;
    }
}
