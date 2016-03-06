package Player;

import Event.Event;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


public class NetworkHumanPlayer extends Player {

    private BufferedReader inputStream;
    private DataOutputStream outputStream;

    public NetworkHumanPlayer (String tag, boolean isWhite, Socket socket) {
        super(tag, isWhite);
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {

        }

    }

    private ArrayList<Event> parseEventString (String line) {



        return new ArrayList<Event>();
    }

    public ArrayList<Event> fetchNextEvent () {
        try {
            while (!inputStream.ready()) {
                //Wait for input
            }
        } catch (Exception e) {

        }

        return new ArrayList<Event>();
    }
}
