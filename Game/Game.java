package Game;
import Board.*;
import Graphics.GameWindow;
import Player.*;
import Event.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Game {
    Board board;
    Player player1;
    Player player2;
    Player currentPlayer;
    ArrayList<Event> eventStack;
    boolean finished;
    public Dice dice1;
    public Dice dice2;
    public Dice dice3;
    public Dice dice4;
    GameWindow window;

    public Game (Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        board = new Board();
        eventStack = new ArrayList<Event>();
        this.finished = false;
        dice1 = new Dice();
        dice2 = new Dice();
        dice3 = new Dice();
        dice4 = new Dice();
        dice3.setUsed(true);
        dice4.setUsed(true);
    }

    public Board getBoard () {
        return board;
    }

    public void setWindow (GameWindow window) {
        this.window = window;
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



        for (Piece p : board.getWhiteBar()) {
            System.out.printf("W ");
        }
        System.out.println();

        for (Piece p : board.getBlackBar()) {
            System.out.printf("B ");
        }
        System.out.println();

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

    private void useDice () {
        dice1.setUsed(true);
        dice2.setUsed(true);
        dice3.setUsed(true);
        dice4.setUsed(true);
    }

    private void handleEvent (Event event) {
        if (event instanceof Move) {
            if (board.move((Move)event, dice1, dice2, dice3, dice4)) {
                eventStack.add(event);
            }
        } else if (event instanceof Quit) {
            finished = true;
            eventStack.add(event);
        } else if (event instanceof Message) {
            System.out.println(((Message) event).getMessage());
            eventStack.add(event);
        } else if (event instanceof Clear) {
            if (board.clear((Clear)event, dice1, dice2, dice3, dice4)) {
                eventStack.add(event);
                if(board.isGameWon()){
                    System.out.println(currentPlayer.getTag() + " wins");
                    handleEvent(new Quit());
                }
            }
        } else if (event instanceof Revive) {
            if (board.revive((Revive)event, dice1, dice2, dice3, dice4)) {
                eventStack.add(event);
            }
        } else if (event instanceof Skip) {
            eventStack.add(event);
            useDice();
        }
        window.repaint();
    }

    private void handleEvents (ArrayList<Event> events) {
        for (Event event : events) {
            handleEvent(event);
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

    private void decideOnFirst () {
        do{
            System.out.print(player1.getTag() + " rolls: ");
            dice1.roll();
            System.out.print(dice1.getValue() + "\n");

            System.out.print(player2.getTag() + " rolls: ");
            dice2.roll();
            System.out.print(dice2.getValue() + "\n");

            if(dice2.getValue() > dice1.getValue()) {
                Player temp = player2;
                player2 = player1;
                player1 = temp;
                player1.setWhite(true);
                player2.setWhite(false);
            }



        } while(dice1.getValue() == dice2.getValue());
    }

    private void updatePlayer (Player player) {
        ArrayList<Event> events = new ArrayList<Event>();

        if (player instanceof LocalAIPlayer) {
            ((LocalAIPlayer) player).setBoard(board);
            ((LocalAIPlayer) player).setDice(dice1, dice2, dice3, dice4);
        }

        while (!(dice1.used() && dice2.used() && dice3.used() && dice4.used()) && !finished) {
            events = player.fetchNextEvent();
            handleEvents(events);
        }
        handleEvents(events);
    }

    public void play () {

        System.out.println(player1.getTag() + " to start");

        decideOnFirst();

        while (!finished) {
            window.repaint();
            System.out.println(player1.getTag() + "(white)'s move");
            currentPlayer = player1;

            updatePlayer(currentPlayer);

            if (finished) {
                break;
            }

            rollDice();
            System.out.println(player2.getTag() + "(black)'s move");
            currentPlayer = player2;

            updatePlayer(currentPlayer);

            rollDice();
        }
    }

    public void setClearState () {
        board.setClearState();
    }

    public void setToReviveState () {
        board.setToReviveState();
    }
}
