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
    Dice dice3;
    Dice dice4;

    public Game (Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        board = new Board();
        eventStack = new Stack<Event>();
        this.finished = false;
        dice1 = new Dice();
        dice2 = new Dice();
        dice3 = new Dice();
        dice4 = new Dice();
        dice3.setUsed(true);
        dice4.setUsed(true);
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
        if(dice1.used() == false){
            System.out.println( "Move: " + dice1.getValue());
        }
        if(dice2.used() == false){
            System.out.println( "Move: " + dice2.getValue());
        }
        if(dice3.used() == false){
            System.out.println( "Move: " + dice3.getValue());
        }
        if(dice4.used() == false){
            System.out.println( "Move: " + dice4.getValue());
        }


    }

    private void handleEvent (Event event) {
        if (event instanceof Move) {
            if (board.move((Move)event, dice1, dice2, dice3, dice4)) {
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
            if (board.revive((Revive)event, dice1, dice2, dice3, dice4)) {
                displayBoardCommandLine();
            }
        }
    }

    private void rollDice () {
        dice1.roll();
        dice2.roll();
        if(dice1.getValue() == dice2.getValue()){
            dice3.setUsed(false);
            dice4.setUsed(false);
            dice3.setValue(dice1.getValue());
            dice4.setValue(dice1.getValue());
        }
    }

    public void play () {
        do{
            System.out.print(player1.getTag() + " rolls: ");
            dice1.roll();
            System.out.print(dice1.getValue() + "\n");

            System.out.print(player2.getTag() + " rolls: ");
            dice2.roll();
            System.out.print(dice2.getValue() + "\n");

            if(dice2.getValue() > dice1.getValue()) {
                Player temp = player2;
                player2 = new LocalHumanPlayer(player1.getTag(), false);
                player1 = new LocalHumanPlayer(temp.getTag(), true);
            }



        } while(dice1.getValue() == dice2.getValue());

        System.out.println(player1.getTag() + " to start");

        while (!finished) {
            displayBoardCommandLine();
            System.out.println(dice1.getValue() + " : " + dice2.getValue());
            System.out.println(player1.getTag() + "(white)'s move");
            currentPlayer = player1;
            Event event = new Event();

            while (!(dice1.used() && dice2.used() && dice3.used() && dice4.used()) && !(event instanceof Quit)) {
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
            System.out.println(player2.getTag() + "(black)'s move");
            currentPlayer = player2;

            while (!(dice1.used() && dice2.used() && dice3.used() && dice4.used()) && !(event instanceof Quit)) {
                event = player2.fetchNextEvent();
                handleEvent(event);
            }
            handleEvent(event);

            rollDice();
        }
    }
}
