package Event;

public class Clear extends Event {
    private int from;
    private boolean isWhite;

    public Clear (int from, boolean isWhite) {
        this.from = from;
        this.isWhite = isWhite;
    }

    public void setWhite (boolean isWhite) {
        this.isWhite = isWhite;
    }

    public int getFrom () {
        return from;
    }

    public boolean white () {
        return isWhite;
    }

    public String toString () {
        return "(" + from + "|-1),";
    }
}
