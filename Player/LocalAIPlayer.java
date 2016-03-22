package Player;

import Board.*;
import Event.*;
import Game.Dice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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

    public ArrayList<Event> generateLegalMoves (Dice dice) {
        ArrayList<Event> result = new ArrayList<Event>();

        //Check revives
        if (!board.getWhiteBar().empty() && isWhite()) {
            Revive revive = new Revive(Board.SIZE - dice.getValue(), isWhite);
            if (board.isReviveLegal(revive, dice)) {
                result.add(revive);
            }
        } else if (!board.getBlackBar().empty() && !isWhite) {
            Revive revive = new Revive(dice.getValue() - 1, isWhite);
            if (board.isReviveLegal(revive, dice)) {
                result.add(revive);
            }
        }

        //Check clears
        if (isWhite) {
            for (int i = 0; i < 6; i ++) {
                Clear clear = new Clear(i, isWhite);
                if (board.isClearLegal(clear, dice)) {
                    result.add(clear);
                }
            }
        } else {
            for (int i = Board.SIZE - 1; i >= Board.SIZE - 6; i --) {
                Clear clear = new Clear(i, isWhite);
                if (board.isClearLegal(clear, dice)) {
                    result.add(clear);
                }
            }
        }

        //Check moves
        for (int i = 0; i < Board.SIZE; i ++) {
            Move move = null;
            if (isWhite) {
                move = new Move(i, i - dice.getValue(), isWhite);
            } else {
                move = new Move(i, i + dice.getValue(), isWhite);
            }
            if (board.isMoveLegal(move, dice)) {
                result.add(move);
            }
        }

        return result;
    }

    public ArrayList<Event> generateLegalMoves (Dice dice1, Dice dice2, Dice dice3, Dice dice4) {
        ArrayList<Event> result = new ArrayList<>();
        result.addAll(generateLegalMoves(dice1));
        result.addAll(generateLegalMoves(dice2));
        result.addAll(generateLegalMoves(dice3));
        result.addAll(generateLegalMoves(dice4));

        return result;
    }

    //Function assumes a domain of legal moves

    public ArrayList<Event> ofWhichCapture (ArrayList<Event> superSet) {
        ArrayList<Event> result = new ArrayList<>();

        for (Event event : superSet) {
            if (event instanceof Move && board.getColumn(((Move) event).getTo()).size() == 1) {
                if (isWhite && board.getColumn(((Move) event).getTo()).peek() instanceof BlackPiece) {
                    result.add(event);
                } else if (!isWhite && board.getColumn(((Move) event).getTo()).peek() instanceof WhitePiece) {
                    result.add(event);
                }
            } else if (event instanceof Revive && board.getColumn(((Revive) event).getTo()).size() == 1) {
                if (isWhite && board.getColumn(((Revive) event).getTo()).peek() instanceof BlackPiece) {
                    result.add(event);
                } else if (!isWhite && board.getColumn(((Revive) event).getTo()).peek() instanceof WhitePiece) {
                    result.add(event);
                }
            }
        }

        return result;
    }

    //Function assumes a domain of legal moves

    public ArrayList<Event> ofWhichKapia (ArrayList<Event> superSet) {
        ArrayList<Event> result = new ArrayList<>();

        for (Event event : superSet) {
            if (event instanceof Move && board.getColumn(((Move) event).getTo()).size() == 1
                    && board.getColumn(((Move) event).getFrom()).size() != 2) {
                if (isWhite && board.getColumn(((Move) event).getTo()).peek() instanceof WhitePiece) {
                    result.add(event);
                } else if (board.getColumn(((Move) event).getTo()).peek() instanceof BlackPiece) {
                    result.add(event);
                }
            } else if (event instanceof Revive && board.getColumn(((Revive) event).getTo()).size() == 1) {
                if (isWhite && board.getColumn(((Revive) event).getTo()).peek() instanceof WhitePiece) {
                    result.add(event);
                } else if (board.getColumn(((Revive) event).getTo()).peek() instanceof BlackPiece) {
                    result.add(event);
                }
            }
        }

        return result;
    }

    public void orderBySafety (ArrayList<Event> list) {
        LocalAIPlayer player2 = new LocalAggressiveAIPlayer(!isWhite);
        HashMap<Event, Integer> rankings = new HashMap<>();
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
        Dice dice3 = new Dice();
        Dice dice4 = new Dice();
        for (Event e : list) {
            int safetyRank = 0;
            for (int i = 1; i <= 6; i ++) {
                for (int j = 1; j <= 6; j ++) {
                    Board tempBoard = board.clone();
                    dice1.setValue(i);
                    dice1.setUsed(false);
                    dice2.setValue(j);
                    dice2.setUsed(false);
                    if (j == i) {
                        dice3.setValue(i);
                        dice3.setUsed(false);
                        dice4.setValue(i);
                        dice4.setUsed(false);
                    } else {
                        dice3.setUsed(true);
                        dice4.setUsed(true);
                    }

                    player2.setBoard(tempBoard);

                    if (e instanceof Move) {
                        tempBoard.move(((Move) e), dice1, dice2, dice3, dice4);
                    } else if (e instanceof Revive) {
                        tempBoard.revive(((Revive) e), dice1, dice2, dice3, dice4);
                    } else if (e instanceof Clear) {
                        tempBoard.clear(((Clear) e), dice1, dice2, dice3, dice4);
                    }

                    ArrayList<Event> legalMoves = player2.generateLegalMoves(dice1, dice2, dice3, dice4);
                    ArrayList<Event> captureMoves = player2.ofWhichCapture(legalMoves);

                    safetyRank += captureMoves.size();

                    dice1.setUsed(true);
                    dice2.setUsed(true);
                    dice3.setUsed(true);
                    dice4.setUsed(true);
                }
            }
            rankings.put(e, safetyRank);
        }
        list.sort((Event a, Event b) -> {
            return rankings.get(a) > rankings.get(b) ? 1 : -1;
        });
        return;
    }

    public void orderByDisplacement (ArrayList<Event> list) {
        list.sort((Event a, Event b) ->
        {
            int x = 0, y = 0;
            if (a instanceof Move) {
                x = ((Move)a).getTo();
            } else if (a instanceof Revive) {
                x = ((Revive)a).getTo();
            }
            if (b instanceof Move) {
                y = ((Move)a).getTo();
            } else if (b instanceof Revive) {
                y = ((Revive)a).getTo();
            }
            if (isWhite) {
                return x > y ? -1 : 1;
            } else {
                return x < y ? -1 : 1;
            }
        });
    }

    public Event containsEventType (ArrayList<Event> list, Class type) {
        for (Event e: list) {
            if (e.getClass() == type) {
                return e;
            }
        }
        return null;
    }

}
