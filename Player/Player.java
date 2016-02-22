package Player;


import Event.Event;

public abstract class Player {
    String tag;
    boolean isWhite;

    public Player (String tag, boolean isWhite) {
        this.tag = tag;
        this.isWhite = isWhite;
    }

    public String getTag () {
        return tag;
    }

    public abstract Event fetchNextEvent ();
}
