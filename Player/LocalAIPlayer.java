package Player;

import Board.*;
import Event.*;
import Game.Dice;

import java.util.ArrayList;
import java.util.Stack;

public abstract class LocalAIPlayer extends Player {
    protected Dice dice1, dice2, dice3, dice4;

    public LocalAIPlayer (String tag, boolean isWhite) {
        super(tag, isWhite);
    }

    public void setDice (Dice dice1, Dice dice2, Dice dice3, Dice dice4) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.dice3 = dice3;
        this.dice4 = dice4;
    }

    public ArrayList<Event> generateCaptureMoves (Dice dice) {
        ArrayList<Event> result = new ArrayList<Event>();
        for (int i = 0; i < board.SIZE; i ++) {
            Stack<Piece> column = board.getColumn(i);
            Move capture;

            if (column.empty()) {
                //Do nothing
            } else if (isWhite && column.peek() instanceof WhitePiece) {
                capture = new Move(i, i - dice.getValue(), isWhite);
                if (i - dice.getValue() >= 0 && board.getColumn(i + dice.getValue()).size() == 1
                        && board.getColumn(i + dice.getValue()).peek() instanceof BlackPiece) {
                    result.add(capture);
                }
            } else if (!isWhite && column.peek() instanceof BlackPiece) {
                capture = new Move(i, i + dice.getValue(), isWhite);
                if (i + dice.getValue() < board.SIZE && board.getColumn(i + dice.getValue()).size() == 1
                        && board.getColumn(i + dice.getValue()).peek() instanceof BlackPiece) {
                    result.add(capture);
                }
            }
        }

        //Check if any revives can capture [FIX]
/*        if (isWhite && !board.getWhiteBar().empty()) {
            if (board.getColumn(dice.getValue() - 1).size() == 1 && board.getColumn(dice.getValue() - 1).peek() instanceof BlackPiece) {
                result.add(new Revive(dice.getValue() - 1, isWhite));
            }
        } else if (!isWhite && !board.getBlackBar().empty()) {
            if (board.getColumn(board.SIZE - dice.getValue()).size() == 1 && board.getColumn(board.SIZE - dice.getValue()).peek() instanceof WhitePiece) {
                result.add(new Revive(board.SIZE - dice.getValue(), isWhite));
            }
        }*/

        return result;
    }

    //A move is said to be a kapia if it moves a piece on it's own onto a stack of one or more pieces, thus preventing potential captures
    //This method returns a list of any such moves

    public ArrayList<Event> generateKapiaMoves (Dice dice) {
        ArrayList<Event> result = new ArrayList<Event>();
        for (int i = 0; i < Board.SIZE; i ++) {
            Stack<Piece> column = board.getColumn(i);

             if (isWhite && column.size() == 1 && column.peek() instanceof WhitePiece) {
                Move move = new Move(i, i - dice.getValue(), isWhite);
                 if (board.isMoveLegal(move, dice) && board.getColumn(i - dice.getValue()).size() >= 1
                         && board.getColumn(i - dice.getValue()).peek() instanceof WhitePiece) {
                     result.add(move);
                 }
             } else if (!isWhite && column.size() == 1 && column.peek() instanceof BlackPiece) {
                 Move move = new Move(i, i + dice.getValue(), isWhite);
                 if (board.isMoveLegal(move, dice) && board.getColumn(i + dice.getValue()).size() >= 1
                         && board.getColumn(i + dice.getValue()).peek() instanceof BlackPiece) {
                     result.add(move);
                 }
             }
        }

        //[FIX]
/*        if (isWhite && !board.getWhiteBar().empty()) {
            if (board.getColumn(dice.getValue() - 1).size() >= 1 && board.getColumn(dice.getValue() - 1).peek() instanceof WhitePiece) {
                result.add(new Revive(dice.getValue() - 1, isWhite));
            }
        } else if (!isWhite && !board.getBlackBar().empty()) {
            if (board.getColumn(board.SIZE - dice.getValue()).size() >= 1 && board.getColumn(board.SIZE - dice.getValue()).peek() instanceof BlackPiece) {
                result.add(new Revive(board.SIZE - dice.getValue(), isWhite));
            }
        }*/

        return result;
    }
}
