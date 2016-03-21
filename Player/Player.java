package Player;


import Board.*;
import Event.*;
import Game.Dice;
import java.util.ArrayList;
import java.util.Stack;

public abstract class Player {
    String tag;
    boolean isWhite;
    Board board;

    public Player (String tag, boolean isWhite) {
        this.tag = tag;
        this.isWhite = isWhite;
    }

    public void setWhite (boolean isWhite) {
        this.isWhite = isWhite;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public void setTag (String tag) {
        this.tag = tag;
    }
    public boolean isWhite () {
        return isWhite;
    }

    public void updateGameState (ArrayList<Event> events) {

    }

    public String getTag () {
        return tag;
    }

    public abstract ArrayList<Event> fetchNextEvent ();

    //Checks if player can clear a piece; if so it returns that clear, returns null if none can be made
    public Event canClear (Dice dice) {
        if (isWhite) {
            for (int i = 0; i < 6; i ++) {
                Clear clear = new Clear(i, isWhite);
                if (board.isClearLegal(clear, dice)) {
                    return clear;
                }
            }
        } else {
            for (int i = board.SIZE - 1; i > 17; i --) {
                Clear clear = new Clear(i, isWhite);
                if (board.isClearLegal(clear, dice)) {
                    return clear;
                }
            }
        }
        return null;
    }

    //Checks if player can revive a piece; if so it returns that revive, returns null if none can be made
    public Event canRevive (Dice dice) {
        if (isWhite && !board.getWhiteBar().empty()) {
            //Revive white piece
            Revive revive = new Revive(Board.SIZE - dice.getValue(), true);
            if (board.isReviveLegal(revive, dice)) {
                return revive;
            } else {
                return null;
            }
        } else if (!isWhite && !board.getBlackBar().empty()) {
            //Revive black piece
            Revive revive = new Revive(dice.getValue() - 1, false);
            if (board.isReviveLegal(revive, dice)) {
                return revive;
            } else {
                return null;
            }
        }
        return null;
    }


    public boolean isMovePossible (Dice dice1, Dice dice2, Dice dice3, Dice dice4) {
        for (int i = 0; i < board.SIZE; i ++) {
            Stack<Piece> column = board.getColumn(i);
            if (!column.empty()) {
                if (isWhite && column.peek() instanceof WhitePiece) {
                    Move move1 = new Move(i, i - dice1.getValue(), isWhite);
                    Move move2 = new Move(i, i - dice2.getValue(), isWhite);
                    Move move3 = new Move(i, i - dice3.getValue(), isWhite);
                    Move move4 = new Move(i, i - dice4.getValue(), isWhite);

                    if (board.isMoveLegal(move1, dice1) || board.isMoveLegal(move2, dice2)
                            || board.isMoveLegal(move3, dice3) ||board.isMoveLegal(move4, dice4)) {
                        return true;
                    }
                } else if (!isWhite && column.peek() instanceof BlackPiece) {
                    Move move1 = new Move(i, i + dice1.getValue(), isWhite);
                    Move move2 = new Move(i, i + dice2.getValue(), isWhite);
                    Move move3 = new Move(i, i + dice3.getValue(), isWhite);
                    Move move4 = new Move(i, i + dice4.getValue(), isWhite);

                    if (board.isMoveLegal(move1, dice1) || board.isMoveLegal(move2, dice2)
                            || board.isMoveLegal(move3, dice3) ||board.isMoveLegal(move4, dice4)) {
                        return true;
                    }
                }
            }
        }

        if (canRevive(dice1) != null || canRevive(dice2) != null || canRevive(dice3) != null
                || canRevive(dice4) != null) {
            return true;
        } else if (canClear(dice1) != null || canClear(dice2) != null || canClear(dice3) != null
                || canClear(dice4) != null) {
            return true;
        }
        return false;
    }
}
