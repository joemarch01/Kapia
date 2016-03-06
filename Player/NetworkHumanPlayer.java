package Player;

import Event.*;

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

    public void updateGameState (ArrayList<Event> events) {
        String moveString = "";

        for (Event event : events) {
            if (event instanceof Move) {
                moveString += "(" + ((Move) event).getFrom() + " | " + ((Move) event).getTo() + "),";
            }
        }

        if (!moveString.equals("")) {
            moveString = moveString.substring(0, moveString.lastIndexOf(','));
            moveString += ";";
            writeToNetwork(moveString);
        }
    }

    public String readFromNetwork () {
        try {
            if (!inputStream.ready()) {
                return null;
            } else {
                return inputStream.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void writeToNetwork (String toWrite) {
        if (!toWrite.endsWith("\n")) {
            toWrite += "\n";
        }

        try {
            outputStream.writeBytes(toWrite);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private ArrayList<Event> parseEventString (String line) {

        //Can only do moves for now

        ArrayList<Event> result = new ArrayList<Event>();

        if (line.startsWith("(")) {
            String [] moves = line.split(",");

            for (String move : moves) {
                move = move.replace('(', ' ');
                move = move.replace(')', ' ');
                String[] args = move.split("|");
                Move newMove = new Move(Integer.valueOf(args[0]), Integer.valueOf(args[1]), isWhite);
                result.add(newMove);
            }
        }



        return result;
    }

    public ArrayList<Event> fetchNextEvent () {
        String input = readFromNetwork();
        while (input == null) {
            input = readFromNetwork();
        }
        return parseEventString(input);
    }
}
