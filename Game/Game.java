package Game;
import Board.*;
import Player.*;
import Event.*;

import java.util.Random;
import java.util.Stack;

public class Game {
    Board board;
    Player player1;
    Player player2;
    Player currentPlayer;
    Stack<Event> eventStack;
    boolean finished;
    Dice dice1;
    Dice dice2;

    public Game (Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        board = new Board();
        eventStack = new Stack<Event>();
        this.finished = false;
        dice1 = new Dice();
        dice2 = new Dice();
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
            if (board.move((Move)event, dice1, dice2)) {
                displayBoardCommandLine();
            }
        } else if (event instanceof Quit) {
            finished = true;
        } else if (event instanceof Message) {
            System.out.println(((Message) event).getMessage());
        } else if (event instanceof Clear) {
            if (board.clear((Clear)event, dice1, dice2)) {
                displayBoardCommandLine();
            }
        } else if (event instanceof Revive) {
            if (board.revive((Revive)event, dice1, dice2)) {
                displayBoardCommandLine();
            }
        }
    }

    private void rollDice () {
        dice1.roll();
        dice2.roll();
    }

    public void play () {
        while (!finished) {
            displayBoardCommandLine();
            rollDice();
            System.out.println(dice1.getValue() + " : " + dice2.getValue());
            System.out.println(player1.getTag() + "'s move");
            currentPlayer = player1;
            Event event = new Event();

            while (!(dice1.used() && dice2.used()) && !(event instanceof Quit)) {
                event = player1.fetchNextEvent();
                handleEvent(event);
            }
            handleEvent(event);

            if (finished) {
                break;
            }

            displayBoardCommandLine();

            rollDice();
            System.out.println(dice1.getValue() + " : " + dice2.getValue());
            System.out.println(player2.getTag() + "'s move");
            currentPlayer = player2;
            event = player2.fetchNextEvent();

            while (!(dice1.used() && dice2.used()) && !(event instanceof Quit)) {
                event = player2.fetchNextEvent();
                handleEvent(event);
            }
            handleEvent(event);

        }
    }
}
