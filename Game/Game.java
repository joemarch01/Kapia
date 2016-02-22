package Game;
import Board.*;
import Player.*;
import Event.*;

import java.util.Stack;

public class Game {
    Board board;
    Player player1;
    Player player2;
    Stack<Event> eventStack;
    boolean finished;

    public Game (Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        board = new Board();
        eventStack = new Stack<Event>();
        this.finished = false;
    }

    public void displayBoardCommandLine () {
        for (int i = 0; i < Board.SIZE/2; i ++) {
            System.out.printf(i + " : ");

            if (!board.getColumn(i).empty()) {
                if (board.getColumn(i).get(0) instanceof WhitePiece) {
                    for (int j = 0; j < board.getColumn(i).size(); j ++) {
                        System.out.printf("W");
                    }
                    for (int j = 0; j < 15 - board.getColumn(i).size(); j ++) {
                        System.out.printf(" ");
                    }
                } else {
                    for (int j = 0; j < board.getColumn(i).size(); j ++) {
                        System.out.printf("B");
                    }
                    for (int j = 0; j < 15 - board.getColumn(i).size(); j ++) {
                        System.out.printf(" ");
                    }
                }
            } else {
                System.out.printf("               ");
            }

            System.out.printf("\t\t");

            if (!board.getColumn(Board.SIZE - i - 1).empty()) {
                if (board.getColumn(Board.SIZE - i - 1).get(0) instanceof WhitePiece) {
                    for (int j = 0; j < 15 - board.getColumn(Board.SIZE - i - 1).size(); j ++) {
                        System.out.printf(" ");
                    }
                    for (int j = 0; j < board.getColumn(Board.SIZE - i - 1).size(); j ++) {
                        System.out.printf("W");
                    }
                } else {
                    for (int j = 0; j < 15 - board.getColumn(Board.SIZE - i - 1).size(); j ++) {
                        System.out.printf(" ");
                    }
                    for (int j = 0; j < board.getColumn(Board.SIZE - i - 1).size(); j ++) {
                        System.out.printf("B");
                    }
                }
            } else {
                System.out.printf("               ");
            }

            System.out.printf(" : " + (Board.SIZE - i - 1));

            System.out.println();
        }
    }

    private void handleEvent (Event event) {
        if (event instanceof Move) {
            board.move((Move)event);
        } else if (event instanceof Quit) {
            finished = true;
        } else if (event instanceof Message) {
            System.out.println(((Message) event).getMessage());
        }
    }

    public void play () {
        while (!finished) {
            displayBoardCommandLine();
            Event event = player1.fetchNextEvent();
            while (!(event instanceof Move || event instanceof Quit)) {
                handleEvent(event);
                event = player1.fetchNextEvent();
            }
            handleEvent(event);
        }
    }
}
