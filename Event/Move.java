package Event;

public class Move extends Event {

    private int from;
    private int to;
    private boolean white;

    public Move (int from, int to, boolean white) {
        this.from = from;
        this.to = to;
        this.white = white;
    }

    public  void setWhite (boolean white) {
        this.white = white;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public boolean getWhite () {
        return white;
    }

    public void setTo(int to) {
        this.to = to;
    }


}
