package Player;

import Event.*;
import java.util.ArrayList;

public class LocalConservativeAIPlayer extends LocalAIPlayer {

    public  LocalConservativeAIPlayer (boolean isWhite) {
        super("Conservative AI", isWhite);
    }

    public ArrayList<Event> fetchNextEvent () {
        return new ArrayList<Event>();
    }
}
