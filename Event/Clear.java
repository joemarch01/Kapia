package Event;

/**
 * Created by jm360 on 22/02/16.
 */
public class Clear extends Event {
    private int from;
    private boolean isWhite;

    public Clear (int from, boolean isWhite) {
        this.from = from;
        this.isWhite = isWhite;
    }

    public int getFrom () {
        return from;
    }

    public boolean white () {
        return isWhite;
    }
}
