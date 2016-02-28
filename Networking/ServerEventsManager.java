package Networking;

import Event.*;

public class ServerEventsManager {

    static int portNumber;
    static String hostName;

    public ServerEventsManager (int portNumber, String hostName) {
        this.portNumber = portNumber;
        this.hostName = hostName;
    }

    public Event parseEventString (String eventString) {
        return new Event();
    }

    public Event fetchEvent (long timeOut) {
        return new Event();
    }
}
