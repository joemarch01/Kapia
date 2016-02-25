package Board;

import java.util.ArrayList;
import java.util.Stack;
import Event.*;
import Game.Dice;

public class Board {
    
    public static final int SIZE = 24;
    public static final int NUMBER_OF_PIECES = 15;

    public int numberOfWhitePieces;
    public int numberOfBlackPieces;
    Stack<Piece> [] board;
    Stack<WhitePiece> whiteBar;
    Stack<BlackPiece> blackBar;

    public Board () {
        board =  (Stack<Piece>[]) new Stack [SIZE];
        whiteBar = new Stack<WhitePiece>();
        blackBar = new Stack<BlackPiece>();
        numberOfBlackPieces = NUMBER_OF_PIECES;
        numberOfWhitePieces = NUMBER_OF_PIECES;

        for (int i = 0; i < board.length; i ++) {
            board[i] = new Stack<Piece>();
        }

        //Initialise the Black Pieces
        board[0].add(new BlackPiece());
        board[0].add(new BlackPiece());
        board[0].add(new BlackPiece());
        board[0].add(new BlackPiece());
        board[0].add(new BlackPiece());
        board[11].add(new BlackPiece());
        board[11].add(new BlackPiece());
        board[17].add(new BlackPiece());
        board[17].add(new BlackPiece());
        board[17].add(new BlackPiece());
        board[17].add(new BlackPiece());
        board[17].add(new BlackPiece());
        board[19].add(new BlackPiece());
        board[19].add(new BlackPiece());
        board[19].add(new BlackPiece());

        //Initialise the White Pieces
        board[5].add(new WhitePiece());
        board[5].add(new WhitePiece());
        board[5].add(new WhitePiece());
        board[5].add(new WhitePiece());
        board[5].add(new WhitePiece());
        board[7].add(new WhitePiece());
        board[7].add(new WhitePiece());
        board[7].add(new WhitePiece());
        board[12].add(new WhitePiece());
        board[12].add(new WhitePiece());
        board[23].add(new WhitePiece());
        board[23].add(new WhitePiece());
        board[23].add(new WhitePiece());
        board[23].add(new WhitePiece());
        board[23].add(new WhitePiece());

        return;
    }

    public void setToReviveState () {
        //
        whiteBar.push((WhitePiece)board[23].pop());
        //
        blackBar.push((BlackPiece)board[0].pop());
    }

    public Stack<Piece> getColumn (int column) {
        if (column >= SIZE) {
            return null;
        } else {
            return board[column];
        }
    }

    public Stack<WhitePiece> getWhiteBar () {
        return whiteBar;
    }

    public Stack<BlackPiece> getBlackBar () {
        return blackBar;
    }

    public boolean isMoveLegal (Move move, Dice dice) {

        if (dice.used()) {
            return false;
        }

        if (Math.abs(move.getFrom() - move.getTo()) != dice.getValue()) {
            return false;
        } else if (move.getFrom() >= SIZE || move.getFrom() < 0 || move.getTo() >= SIZE || move.getTo() < 0) {
            return false;
        } else if (!move.getWhite() && move.getTo() - move.getFrom() < 0) {
            return false;
        } else if (move.getWhite() && move.getTo() - move.getFrom() > 0) {
            return false;
        } else if (board[move.getFrom()].size() == 0) {
            return false;
        } else if (board[move.getTo()].size() > 1 && board[move.getTo()].peek().getClass() != board[move.getFrom()].peek().getClass()) {
            return false;
        }
        return true;
    }

    public boolean isMoveLegal (Move move, Dice dice1, Dice dice2, Dice dice3, Dice dice4) {

        if (move.getWhite() && !whiteBar.empty()) {
            return false;
        } else if (!move.getWhite() && !blackBar.empty()) {
            return false;
        }

        return isMoveLegal(move, dice1) || isMoveLegal(move, dice2) || isMoveLegal(move, dice3) || isMoveLegal(move, dice4);
    }

    public boolean move (Move move, Dice dice1, Dice dice2, Dice dice3, Dice dice4) {

        if (!isMoveLegal(move, dice1, dice2, dice3, dice4)) {
            return false;
        }

        if (Math.abs(move.getFrom() - move.getTo()) == dice1.getValue() && !dice1.used()) {
            dice1.setUsed(true);
        } else if (Math.abs(move.getFrom() - move.getTo()) == dice2.getValue() && !dice2.used())  {
            dice2.setUsed(true);
        } else if (Math.abs(move.getFrom() - move.getTo()) == dice3.getValue() && !dice3.used())  {
            dice3.setUsed(true);
        } else if (Math.abs(move.getFrom() - move.getTo()) == dice4.getValue() && !dice4.used())  {
            dice4.setUsed(true);
        }

        if (board[move.getTo()].isEmpty() || board[move.getTo()].peek().getClass() == board[move.getFrom()].peek().getClass()) {
            board[move.getTo()].add(board[move.getFrom()].pop());
        } else {
            if (move.getWhite()) {
                blackBar.push((BlackPiece)board[move.getTo()].pop());
            } else {
                whiteBar.push((WhitePiece)board[move.getTo()].pop());
            }
            board[move.getTo()].add(board[move.getFrom()].pop());
        }

        return true;
    }

    private boolean isClearLegal (Clear clear, Dice dice) {

        int total = 0;
        if (clear.white()) {

            if (clear.getFrom() > 5 || clear.getFrom() < 0 || clear.getFrom() >= SIZE) {
                return false;
            }

            for (int i = 0; i < 6; i ++) {
                if (!board[i].empty() && board[i].peek() instanceof WhitePiece) {
                    total += board[i].size();
                }
            }

            if (total != numberOfWhitePieces) {
                return false;
            }

        } else {

            if (clear.getFrom() < SIZE - 6 || clear.getFrom() < 0 || clear.getFrom() >= SIZE) {
                return false;
            }

            for (int i = SIZE - 6; i < SIZE; i ++) {
                if (!board[i].empty() && board[i].peek() instanceof BlackPiece) {
                    total += board[i].size();
                }
            }

            if (total != numberOfBlackPieces) {
                return false;
            }
        }

        return true;
    }

    private boolean isClearLegal (Clear clear, Dice dice1, Dice dice2) {

        if (clear.white() && !whiteBar.empty()) {
            return false;
        } else if (!clear.white() && !blackBar.empty()) {
            return false;
        }

        if (dice1.used() && dice2.used()) {
            return false;
        } else if (dice2.used()) {
            return isClearLegal(clear, dice1);
        } else if (dice1.used()) {
            return isClearLegal(clear, dice2);
        } else {
            return isClearLegal(clear, dice1) || isClearLegal(clear, dice2);
        }
    }

    public boolean clear (Clear clear, Dice dice1, Dice dice2) {

        if (!isClearLegal(clear, dice1, dice2)) {
            return false;
        }

        board[clear.getFrom()].pop();

        if (clear.white()) {
            if (clear.getFrom() == dice1.getValue() - 1) {
                dice1.setUsed(true);
            } else {
                dice2.setUsed(true);
            }
            numberOfWhitePieces --;
        } else {
            if (SIZE - clear.getFrom() == dice1.getValue()) {
                dice1.setUsed(true);
            } else {
                dice2.setUsed(true);
            }
            numberOfBlackPieces --;
        }

        return true;
    }

    public boolean isReviveLegal (Revive revive, Dice dice) {
        if (dice.used()) {
            return false;
        }

        if (revive.getWhite()) {
            if (SIZE - revive.getTo() != dice.getValue()) {
                return false;
            }
        } else {
            if (revive.getTo() + 1 != dice.getValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean isReviveLegal (Revive revive, Dice dice1, Dice dice2, Dice dice3, Dice dice4) {

        if (revive.getTo() < 0 || revive.getTo() >= SIZE) {
            return false;
        }

        if (!(isReviveLegal(revive, dice1) || isReviveLegal(revive, dice2) || isReviveLegal(revive, dice3) || isReviveLegal(revive, dice4))) {
            return false;
        }

        if (revive.getWhite()) {
            if (whiteBar.empty()) {
                return false;
            } else if (board[revive.getTo()].size() > 1 && board[revive.getTo()].peek() instanceof BlackPiece) {
                return false;
            } else if (revive.getTo() < SIZE - 6) {
                return false;
            }
        } else {
            if (blackBar.empty()) {
                return false;
            } else if (board[revive.getTo()].size() > 1 && board[revive.getTo()].peek() instanceof WhitePiece) {
                return false;
            } else if (revive.getTo() > 5) {
                return false;
            }
        }
        return true;
    }

    public boolean revive (Revive revive, Dice dice1, Dice dice2, Dice dice3, Dice dice4) {
        if (!isReviveLegal(revive, dice1, dice2, dice3, dice4)) {
            return false;
        }

        if (revive.getWhite()) {
            if (!board[revive.getTo()].empty() && board[revive.getTo()].peek() instanceof WhitePiece) {
                board[revive.getTo()].push(whiteBar.pop());
            } else if (board[revive.getTo()].size() == 1 && board[revive.getTo()].peek() instanceof BlackPiece) {
                blackBar.push((BlackPiece)board[revive.getTo()].pop());
                board[revive.getTo()].push(whiteBar.pop());
            } else if (board[revive.getTo()].size() == 0){
                board[revive.getTo()].push(whiteBar.pop());
            } else {
                return false;
            }
        } else {
            if (!board[revive.getTo()].empty() && board[revive.getTo()].peek() instanceof BlackPiece) {
                board[revive.getTo()].push(whiteBar.pop());
            }
            if (board[revive.getTo()].size() == 1 && board[revive.getTo()].peek() instanceof WhitePiece) {
                whiteBar.push((WhitePiece) board[revive.getTo()].pop());
                board[revive.getTo()].push(blackBar.pop());
            } else {
                board[revive.getTo()].push(blackBar.pop());
            }
        }

        if (!revive.getWhite()) {
            if(revive.getTo() == dice1.getValue() - 1 && !dice1.used()) {
                dice1.setUsed(true);
            } else if(revive.getTo() == dice2.getValue() - 1 && !dice2.used()) {
                dice2.setUsed(true);
            } else if(revive.getTo() == dice3.getValue() - 1 && !dice3.used()) {
                dice3.setUsed(true);
            } else if(revive.getTo() == dice4.getValue() - 1 && !dice4.used()) {
                dice4.setUsed(true);
            }
        } else {
            if(revive.getTo() == SIZE - dice1.getValue() && !dice1.used()) {
                dice1.setUsed(true);
            } else if(revive.getTo() == SIZE - dice2.getValue() && !dice2.used()) {
                dice2.setUsed(true);
            } else if(revive.getTo() == SIZE - dice3.getValue() && !dice3.used()) {
                dice3.setUsed(true);
            } else if(revive.getTo() == SIZE - dice4.getValue() && !dice4.used()) {
                dice4.setUsed(true);
            }
        }

        return true;
    }

    public boolean isGameWon () {
        return (numberOfBlackPieces == 0) || (numberOfWhitePieces == 0);
    }
}
