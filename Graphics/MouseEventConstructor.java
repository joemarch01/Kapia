package Graphics;

import Event.*;

public class MouseEventConstructor {
    private static Event currentEvent = new Event();
    private static boolean eventConstructed = false;

    public static void registerColumnInput (int column) {
         if (currentEvent instanceof Move && !eventConstructed) {
             ((Move) currentEvent).setTo(column);
            eventConstructed = true;
        } else if (currentEvent instanceof Revive && !eventConstructed) {
            ((Revive) currentEvent).setTo(column);
            eventConstructed = true;
        } else if (!(currentEvent instanceof Move) || eventConstructed) {
            //Start making new move
            currentEvent = new Move(column, 0, true);
            eventConstructed = false;
        }
    }

    public static void registerBarInput () {
        currentEvent = new Revive(0, true);
        eventConstructed = false;
    }

    public static void registerFinishZoneInput () {
        if (eventConstructed) {
            //Do nothing
        } else if (!(currentEvent instanceof Move)) {
            //Do nothing
        } else {
            currentEvent = new Clear(((Move) currentEvent).getFrom(), true);
            eventConstructed = true;
        }
    }

    public static Event nextEvent () {
        if (!eventConstructed) {
            return null;
        } else {
            eventConstructed = false;
            Event temp = currentEvent;
            currentEvent = new Event();
            return temp;
        }
    }
}
