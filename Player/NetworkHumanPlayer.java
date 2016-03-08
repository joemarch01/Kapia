package Player;

import Event.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
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
        String diceString = "";

        for (Event event : events) {
            if (event instanceof Move) {
                moveString += event.toString();
            } else if (event instanceof SetDice) {
                diceString += event.toString();
            }
        }

        if (!moveString.equals("")) {
            moveString = moveString.substring(0, moveString.lastIndexOf(','));
            moveString += ";";
        }

        if (!diceString.equals("")) {
            diceString = moveString.substring(0, moveString.lastIndexOf('-'));
            diceString += ":";
        }

        writeToNetwork(diceString + moveString);
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

        String [] diceArgs = line.substring(0, line.lastIndexOf(':')).split("-");
        String [] moveArgs = line.substring(line.lastIndexOf(":" + 1)).split(",");

        ArrayList<Event> result = new ArrayList<Event>();

        for (int i = 0; i < diceArgs.length; i ++) {
            result.add(new SetDice(i, Integer.valueOf(diceArgs[i])));
        }

        for (String move : moveArgs) {
            move = move.replace('(', ' ');
            move = move.replace(')', ' ');
            String[] args = move.split("|");
            Move newMove = new Move(Integer.valueOf(args[0]), Integer.valueOf(args[1]), isWhite);
            result.add(newMove);
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
