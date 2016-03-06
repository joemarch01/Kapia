package Player;


import Event.Event;

import java.util.ArrayList;

public abstract class Player {
    String tag;
    boolean isWhite;

    public Player (String tag, boolean isWhite) {
        this.tag = tag;
        this.isWhite = isWhite;
    }

    public void setWhite (boolean isWhite) {
        this.isWhite = isWhite;
    }

    public void updateGameState (ArrayList<Event> events) {

    }

    public String getTag () {
        return tag;
    }

    public abstract ArrayList<Event> fetchNextEvent ();
}
