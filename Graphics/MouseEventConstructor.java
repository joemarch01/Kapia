package Graphics;

import Event.*;

public class MouseEventConstructor {
    private static Event currentEvent = new Event();
    private static boolean eventConstructed = false;

    public static void registerColumnInput (int column) {
        if (!(currentEvent instanceof Move) || eventConstructed) {
            //Start making new move
            currentEvent = new Move(column, 0, true);
            eventConstructed = false;
        } else if (currentEvent instanceof Move && !eventConstructed) {
             ((Move) currentEvent).setTo(column);
            eventConstructed = true;
        }
    }

    public static Event getCurrentEvent () {
        if (!eventConstructed) {
            return null;
        } else {
            return currentEvent;
        }
    }
}
