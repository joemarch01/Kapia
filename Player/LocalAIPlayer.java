package Player;

import Board.Board;
import Event.*;
import Game.Dice;

public abstract class LocalAIPlayer extends Player {
    protected Board board;
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

    public void setBoard (Board board) {
        this.board = board;
    }

    //Checks if AI can clear a piece; if so it returns that clear, returns null if none can be made
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

    //Checks if AI can revive a piece; if so it returns that revive, returns null if none can be made
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
}
