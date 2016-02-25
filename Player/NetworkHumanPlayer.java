package Player;

import Event.Event;


public class NetworkHumanPlayer extends Player {
    public NetworkHumanPlayer (String tag, boolean isWhite) {
        super(tag, isWhite);
    }

    public Event fetchNextEvent () {
        return new Event();
    }
}
