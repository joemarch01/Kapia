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

    public int getTo () {
        return to;
    }
}
