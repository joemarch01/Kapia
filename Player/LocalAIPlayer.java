package Player;

import Board.Board;
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
}
