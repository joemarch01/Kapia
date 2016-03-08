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
            } else {
                System.out.println(currentPlayer.getTag() + " made an illegal move");
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
        } else if (event instanceof SetDice) {
            switch (((SetDice) event).getDiceNumber()) {
                case 1 :
                    dice1.setValue(((SetDice) event).getValue());
                    dice1.setUsed(false);
                    break;
                case 2 :
                    dice2.setValue(((SetDice) event).getValue());
                    dice2.setUsed(false);
                    break;
                case 3 :
                    dice3.setValue(((SetDice) event).getValue());
                    dice3.setUsed(false);
                    break;
                case 4 :
                    dice4.setValue(((SetDice) event).getValue());
                    dice4.setUsed(false);
                    break;
            }
            System.out.println("Dice : " + ((SetDice) event).getDiceNumber() + " set to : " + ((SetDice) event).getValue());
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

        if (player2 instanceof NetworkHumanPlayer) {

            ((NetworkHumanPlayer) player2).writeToNetwork("Me first");
            String s = ((NetworkHumanPlayer) player2).readFromNetwork();
            rollDice();

            if (s == null) {
                System.out.println("Me first");
            } else {
                Player temp = player2;
                player2 = player1;
                player1 = temp;
                player1.setWhite(true);
                player2.setWhite(false);
                System.out.println("Other guy first");
            }
            return;
        }

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
        ArrayList<Event> gameEvents = new ArrayList<Event>();

        if (player instanceof LocalAIPlayer) {
            ((LocalAIPlayer) player).setBoard(board);
            ((LocalAIPlayer) player).setDice(dice1, dice2, dice3, dice4);
        } else if (player instanceof NetworkHumanPlayer) {
            events = player.fetchNextEvent();
            handleEvents(events);
            return;
        }

        gameEvents.add(new SetDice(1, dice1.getValue()));
        gameEvents.add(new SetDice(2, dice2.getValue()));

        if (!dice3.used()) {
            gameEvents.add(new SetDice(3, dice3.getValue()));
            gameEvents.add(new SetDice(4, dice4.getValue()));
        }

        while (!(dice1.used() && dice2.used() && dice3.used() && dice4.used()) && !finished) {
            events = player.fetchNextEvent();
            gameEvents.addAll(events);
            handleEvents(events);
        }
        gameEvents.addAll(events);
        handleEvents(events);

        if (player == player1) {
            player2.updateGameState(gameEvents);
        } else {
            player1.updateGameState(gameEvents);
        }
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
