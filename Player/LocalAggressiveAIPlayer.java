package Player;


import Board.*;
import Event.*;
import Game.Dice;

import java.util.ArrayList;

public class LocalAggressiveAIPlayer extends LocalAIPlayer {

    public LocalAggressiveAIPlayer (boolean isWhite) {
        super("AggressiveAI", isWhite);
    }

    private Event bestMoveForDice (Dice dice1, Dice dice2, Dice dice3, Dice dice4) {

        ArrayList<Event> legalMoves = generateLegalMoves(dice1, dice2, dice3, dice4);

        orderByDisplacement(legalMoves);

        ArrayList<Event> captureMoves = ofWhichCapture(legalMoves);

        ArrayList<Event> kapiaMoves = ofWhichKapia(legalMoves);

        Event legalClear = containsEventType(legalMoves, Clear.class);

        if (legalClear != null) {
            return legalClear;
        }

        setDice(dice1, dice2, dice3, dice4);



        if (captureMoves.size() != 0) {
            return captureMoves.get(0);
        } else if (kapiaMoves.size() != 0) {
            return kapiaMoves.get(0);
        } else if (legalMoves.size() != 0) {
            return legalMoves.get(0);
        }

        return new Skip();
    }

    public ArrayList<Event> fetchNextEvent () {

        ArrayList<Event> result = new ArrayList<Event>();

        ArrayList<ArrayList<Event>> moves = generateMoveTuples(dice1, dice2, dice3, dice4);

        if (moves.isEmpty()) {
            result.add(new Skip());
            return result;
        }

        int totalClears = rankByHighestClears(moves);
        if (totalClears != 0) {
            return  moves.get(0);
        }

        int totalCaptures = rankByHighestCaptures(moves);
        if (totalCaptures != 0) {
            return  moves.get(0);
        }

        int totalHomeZoneMoves = rankByHomeZoneMoves(moves);
        if (totalHomeZoneMoves != 0) {
            return moves.get(0);
        }

        int totalKapias = rankByHighestKapias(moves);
        if (totalKapias != 0) {
            return moves.get(0);
        }

        rankByAverageDisplacement(moves);
        return moves.get(0);
    }
}
