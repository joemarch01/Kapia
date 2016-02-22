package Event;

/**
 * Created by jm360 on 22/02/16.
 */
public class Message extends Event {

    String message;

    public Message (String message) {
        this.message = message;
    }

    public String getMessage () {
        return message;
    }
}
