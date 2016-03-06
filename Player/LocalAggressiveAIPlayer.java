package Player;


import Board.*;
import Event.*;
import Game.Dice;

import java.util.ArrayList;

public class LocalAggressiveAIPlayer extends LocalAIPlayer {

    public LocalAggressiveAIPlayer (boolean isWhite) {
        super("AggressiveAI", isWhite);
    }

    private Event bestMoveForDice (Dice dice) {
        if (dice.used()) {
            return null;
        }

        int d = dice.getValue();

        if (isWhite && !board.getWhiteBar().empty()) {
            //Revive white piece
            Revive revive = new Revive(Board.SIZE - d, true);
            if (board.isReviveLegal(revive, dice)) {
                return revive;
            } else {
                return null;
            }
        } else if (!isWhite && !board.getBlackBar().empty()) {
            //Revive black piece
            Revive revive = new Revive(d - 1, false);
            if (board.isReviveLegal(revive, dice)) {
                return revive;
            } else {
                return null;
            }
        }

        Move currentBestMove = null;
        boolean isCurrentBestTake = false;
        int currentBestDisplacement = (isWhite) ? 23 : 0;

        for (int i = 0; i < Board.SIZE; i++) {
            if (board.getColumn(i).empty()) {
                //Do nothing
            } else if (board.getColumn(i).peek() instanceof WhitePiece && isWhite) {
                //Compute white move from column i
                if (i - d < 0) {
                    //Do nothing
                } else if (board.getColumn(i - d).empty()) {
                    if (!isCurrentBestTake && i - d < currentBestDisplacement) {
                        currentBestMove = new Move(i, i - d, true);
                        currentBestDisplacement = i - d;
                    }
                } else if (board.getColumn(i - d).peek() instanceof BlackPiece && board.getColumn(i - d).size() == 1) {
                    if (!isCurrentBestTake) {
                        currentBestMove = new Move(i, i - d, true);
                        currentBestDisplacement = i - d;
                        isCurrentBestTake = true;
                    } else if (i - d < currentBestDisplacement) {
                        currentBestMove = new Move(i, i - d, true);
                        currentBestDisplacement = i - d;
                    }
                }
            } else if (board.getColumn(i).peek() instanceof BlackPiece && !isWhite) {
                //Compute black move from column i
                if (i + d >= Board.SIZE) {
                    //Do nothing
                } else if (board.getColumn(i + d).empty()) {
                    if (!isCurrentBestTake && i + d > currentBestDisplacement) {
                        currentBestMove = new Move(i, i + d, false);
                        currentBestDisplacement = i + d;
                    }
                } else if (board.getColumn(i + d).peek() instanceof WhitePiece && board.getColumn(i + d).size() == 1) {
                    if (!isCurrentBestTake) {
                        currentBestMove = new Move(i, i + d, false);
                        currentBestDisplacement = i + d;
                        isCurrentBestTake = true;
                    } else if (i + d > currentBestDisplacement) {
                        currentBestMove = new Move(i, i + d, false);
                        currentBestDisplacement = i + d;
                    }
                }
            }
        }

        return currentBestMove;
    }

    public ArrayList<Event> fetchNextEvent () {

        ArrayList<Event> result = new ArrayList<Event>();

        Event move1 = bestMoveForDice(dice1);
        Event move2 = bestMoveForDice(dice2);
        Event move3 = bestMoveForDice(dice3);
        Event move4 = bestMoveForDice(dice4);

        if (move1 instanceof Revive) {
            result.add(move1);
        } else if (move2 instanceof Revive) {
            result.add(move2);
        } else if (move3 instanceof Revive) {
            result.add(move3);
        } else if (move4 instanceof Revive) {
            result.add(move4);
        }

        if (move1 != null) {
            result.add(move1);
        } else if (move2 != null) {
            result.add(move2);
        } else if (move3 != null) {
            result.add(move3);
        } else if (move4 != null) {
            result.add(move4);
        }

        result.add(new Skip());

        return result;
    }
}
