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

        Event clear = super.canClear(dice);
        Event revive = super.canRevive(dice);

        if (revive != null) {
            return revive;
        } else if (clear != null) {
            return clear;
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

        ArrayList<Event> captureMoves = new ArrayList<>();
        captureMoves.addAll(generateCaptureMoves(dice));

        ArrayList<Event> kapiaMoves = new ArrayList<>();
        kapiaMoves.addAll(generateKapiaMoves(dice));

        if (captureMoves.size() != 0) {
            return captureMoves.get(0);
        } else if (kapiaMoves.size() != 0) {
            return kapiaMoves.get(0);
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
        } else if (move1 != null) {
            result.add(move1);
        } else if (move2 != null) {
            result.add(move2);
        } else if (move3 != null) {
            result.add(move3);
        } else if (move4 != null) {
            result.add(move4);
        } else {
            result.add(new Skip());
        }

        return result;
    }
}
