package Graphics;

import Event.*;

public class MouseEventConstructor {
    private Event currentEvent;
    private boolean eventConstructed;


    public MouseEventConstructor () {
        currentEvent = new Event();
        eventConstructed = false;
    }

    public void registerColumnInput (int column) {
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

    public void registerBarInput () {
        currentEvent = new Revive(0, true);
        eventConstructed = false;
    }

    public void registerFinishZoneInput () {
        if (!eventConstructed) {
            if ((currentEvent instanceof Move)) {
                currentEvent = new Clear(((Move) currentEvent).getFrom(), true);
                eventConstructed = true;
            }
        }
    }

    public Event nextEvent () {
        if (eventConstructed == false) {
            return null;
        } else {
            eventConstructed = false;
            Event temp = currentEvent;
            currentEvent = new Event();
            return temp;
        }
    }

}
