package Player;

import Event.*;
import Game.Dice;

import java.util.ArrayList;

public class LocalConservativeAIPlayer extends LocalAIPlayer {

    public  LocalConservativeAIPlayer (boolean isWhite) {
        super("Conservative AI", isWhite);
    }

    private Event bestMoveForDice (Dice dice1, Dice dice2, Dice dice3, Dice dice4) {

        ArrayList<Event> legalMoves = generateLegalMoves(dice1, dice2, dice3, dice4);

        orderBySafety(legalMoves);

        ArrayList<Event> captureMoves = ofWhichCapture(legalMoves);

        ArrayList<Event> kapiaMoves = ofWhichKapia(legalMoves);

        Event legalClear = containsEventType(legalMoves, Clear.class);

        if (legalClear != null) {
            return legalClear;
        }

        setDice(dice1, dice2, dice3, dice4);

        if (isBoardSafe() && legalMoves.size() > 0) {

            ArrayList<Event> homeZoneMoves = ofWhichMoveIntoHome(legalMoves);
            if (homeZoneMoves.size() > 0) {
                return homeZoneMoves.get(0);
            }

            //orderByDisplacement(legalMoves);
            return legalMoves.get(0);
        }

        if (kapiaMoves.size() != 0) {
            return kapiaMoves.get(0);
        } else if (captureMoves.size() != 0) {
            return captureMoves.get(0);
        } else if (legalMoves.size() != 0) {
            return legalMoves.get(0);
        }

        return new Skip();
    }

    public ArrayList<Event> fetchNextEvent () {

        ArrayList<Event> result = new ArrayList<Event>();

        Event move = bestMoveForDice(dice1, dice2, dice3, dice4);

        result.add(move);

        return result;
    }
}
