package Player;


import Event.*;
import Graphics.MouseEventConstructor;
import org.w3c.dom.events.EventListener;

import java.util.ArrayList;
import java.util.Scanner;

public class LocalHumanPlayer extends Player {

    private MouseEventConstructor eventConstructor;

    public LocalHumanPlayer (String tag, boolean isWhite) {
        super(tag, isWhite);
    }

    public void setEventConstructor (MouseEventConstructor eventConstructor) {
        this.eventConstructor = eventConstructor;
    }

    public ArrayList<Event> fetchNextEvent () {

        Event result;
        ArrayList<Event> resultSet = new ArrayList<Event>();

        do {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            result = eventConstructor.nextEvent();
        } while (result == null);

        if (result instanceof Move) {
            ((Move) result).setWhite(isWhite);
        } else if (result instanceof Revive) {
            ((Revive) result).setWhite(isWhite);
        } else if (result instanceof Clear) {
            ((Clear) result).setWhite(isWhite);
        }

        System.out.println("Done");

        resultSet.add(result);

        return resultSet;
    }
}
