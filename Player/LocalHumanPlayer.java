package Player;


import Event.*;

import java.util.Scanner;

public class LocalHumanPlayer extends Player {

    public LocalHumanPlayer (String tag, boolean isWhite) {
        super(tag, isWhite);
    }

    public Event fetchNextEvent () {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        String[] commandArgs = command.split(":");

        if (commandArgs[0].equals("move")) {
            return new Move(Integer.valueOf(commandArgs[1]), Integer.valueOf(commandArgs[2]), isWhite);
        } else if (commandArgs[0].equals("quit")) {
            return new Quit();
        } else if (commandArgs[0].equals("message")) {
            return new Message(commandArgs[1]);
        } else if (commandArgs[0].equals("clear")) {
            return new Clear(Integer.valueOf(commandArgs[1]), isWhite);
        } else if (commandArgs[0].equals("revive")) {
            return new Revive(Integer.valueOf(commandArgs[1]), isWhite);
        } else if (commandArgs[0].equals("skip")) {
            return new Skip();
        }
        return new Event();
    }
}
