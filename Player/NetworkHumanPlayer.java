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
            } else if (event instanceof Clear) {
                moveString += event.toString();
            } else if (event instanceof Revive) {
                moveString += event.toString();
            } else if (event instanceof Skip) {
                moveString += event.toString();
            }
        }

        if (!moveString.equals("")) {
            try {
                moveString = moveString.substring(0, moveString.lastIndexOf(','));
                moveString += ";";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("move error " + moveString);
            }

        }

        if (!diceString.equals("")) {
            try {
                diceString = diceString.substring(0, diceString.lastIndexOf('-'));
                diceString += ":";
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("dice error " + diceString);
            }

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

        String[] diceArgs = null;
        String[] moveArgs = null;

        try {
            diceArgs = line.substring(0, line.lastIndexOf(':')).split("-");
            moveArgs = line.substring(line.lastIndexOf(":") + 1).split(",");
        } catch (Exception e) {
            System.out.println("Event Parsing error : " + line);
            return new ArrayList<Event>();
        }


        ArrayList<Event> result = new ArrayList<Event>();

        for (int i = 0; i < diceArgs.length; i ++) {
            result.add(new SetDice(i + 1, Integer.valueOf(diceArgs[i])));
        }

        for (String move : moveArgs) {
            String[] args = null;
            try {
                move = move.replace("(", "");
                move = move.replace(")", "");
                move = move.replace(";", "");
                args = move.split("\\|");
                if (args[0].equals("-1") && args[1].equals("-1")) {
                    result.add(new Skip());
                } else if (args[1].equals("-1")) {
                    result.add(new Clear(Integer.valueOf(args[0]), isWhite));
                } else if (args[0].equals("-1")) {
                    result.add(new Revive(Integer.valueOf(args[0]), isWhite));
                } else {
                    result.add(new Move(Integer.valueOf(args[0]), Integer.valueOf(args[1]), isWhite));
                }

            } catch (Exception e) {
                System.out.println("Move Error : " + move + " Args : " + args[0] + " " + args[1]);
                System.out.println(e.getMessage());
            }

        }

        return result;
    }

    public ArrayList<Event> fetchNextEvent () {
        String input = readFromNetwork();
        while (input == null || input.equals("Me first")) {
            input = readFromNetwork();
        }
        System.out.println("Network Events : " + input);
        return parseEventString(input);
    }
}
