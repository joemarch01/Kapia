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

    public ArrayList<Event> ofWhichMoveIntoHome (ArrayList<Event> superSet) {
        ArrayList<Event> result = new ArrayList<>();

        for (Event event : superSet) {
            if (isWhite) {
                if (event instanceof Move) {
                    if (((Move) event).getFrom() >= 6 && ((Move) event).getTo() < 6) {
                        result.add(event);
                    }
                }
            } else {
                if (event instanceof Move) {
                    if (((Move) event).getFrom() < 18 && ((Move) event).getTo() >= 18) {
                        result.add(event);
                    }
                }
            }
        }

        return result;
    }

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
            if (rankings.get(a) > rankings.get(b)) {
                return 1;
            } else if (rankings.get(a) < rankings.get(b)) {
                return -1;
            }
            return 0;
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
                y = ((Move)b).getTo();
            } else if (b instanceof Revive) {
                y = ((Revive)b).getTo();
            }
            if (isWhite) {
                if (x == y) {
                    return 0;
                }
                return x > y ? -1 : 1;
            } else {
                if (x == y) {
                    return 0;
                }
                return x < y ? -1 : 1;
            }
        });
    }

    //Board is safe when all of the other player's pieces are in front of all
    //of the current player's pieces

    public boolean isBoardSafe () {
        if (isWhite) {
            boolean blackSeen = false;

            for (int i = 0; i < Board.SIZE; i ++) {
                if (!board.getColumn(i).empty()) {
                    if (board.getColumn(i).peek() instanceof WhitePiece && blackSeen) {
                        return false;
                    } else if (board.getColumn(i).peek() instanceof BlackPiece) {
                        blackSeen = true;
                    }
                }
            }
        } else {
            boolean whiteSeen = false;

            for (int i = Board.SIZE - 1; i >= 0; i --) {
                if (!board.getColumn(i).empty()) {
                    if (board.getColumn(i).peek() instanceof BlackPiece && whiteSeen) {
                        return false;
                    } else if (board.getColumn(i).peek() instanceof WhitePiece) {
                        whiteSeen = true;
                    }
                }
            }
        }
        return true;
    }

    public ArrayList<ArrayList<Event>> generateMoveTuples (Dice dice1, Dice dice2, Dice dice3, Dice dice4) {
        ArrayList<ArrayList<Event>> result = new ArrayList<>();

        ArrayList<Event> dice1Events = generateLegalMoves(dice1);

        Board boardRef = board;

        //Generate moves for dice 1 first

        for (Event d1Event : dice1Events) {
            Board boardRef1 = board;
            board = board.clone();
            board.updateEvent(d1Event, dice1, dice2, dice3, dice4);
            ArrayList<Event> dice2Events = generateLegalMoves(dice2);

            if (dice2Events.isEmpty()) {
                ArrayList<Event> move = new ArrayList<>();
                move.add(d1Event);
                result.add(move);
                dice1.setUsed(false);
            } else {

                for (Event d2Event : dice2Events) {
                    Board boardRef2 = board;
                    board = board.clone();
                    board.updateEvent(d2Event, dice1, dice2, dice3, dice4);
                    ArrayList<Event> dice3Events = generateLegalMoves(dice3);

                    if (dice3Events.isEmpty()) {
                        ArrayList<Event> move = new ArrayList<>();
                        move.add(d1Event);
                        move.add(d2Event);
                        dice1.setUsed(false);
                        dice2.setUsed(false);
                        result.add(move);
                    } else {

                        for (Event d3Event : dice3Events) {
                            Board boardRef3 = board;
                            board = board.clone();
                            board.updateEvent(d3Event, dice1, dice2, dice3, dice4);
                            ArrayList<Event> dice4Events = generateLegalMoves(dice4);

                            if (dice4Events.isEmpty()) {
                                ArrayList<Event> move = new ArrayList<>();
                                move.add(d1Event);
                                move.add(d2Event);
                                move.add(d3Event);
                                dice1.setUsed(false);
                                dice2.setUsed(false);
                                dice3.setUsed(false);
                                result.add(move);
                            } else {

                                for (Event d4Event : dice4Events) {
                                    ArrayList<Event> move = new ArrayList<>();
                                    move.add(d1Event);
                                    move.add(d2Event);
                                    move.add(d3Event);
                                    move.add(d4Event);
                                    dice1.setUsed(false);
                                    dice2.setUsed(false);
                                    dice3.setUsed(false);
                                    dice4.setUsed(false);
                                    result.add(move);
                                }
                            }
                            board = boardRef3;
                        }
                    }
                    board = boardRef2;
                }
            }

            board = boardRef1;

        }

        board = boardRef;

        //Generate moves for dice 2 first

        ArrayList<Event> dice2Events = generateLegalMoves(dice2);

        for (Event d2Event : dice2Events) {
            Board boardRef1 = board;
            board = board.clone();
            board.updateEvent(d2Event, dice1, dice2, dice3, dice4);
            ArrayList<Event> d1Events = generateLegalMoves(dice1);

            if (d1Events.isEmpty()) {
                ArrayList<Event> move = new ArrayList<>();
                move.add(d2Event);
                result.add(move);
                dice2.setUsed(false);
            } else {

                for (Event d1Event : d1Events) {
                    Board boardRef2 = board;
                    board = board.clone();
                    board.updateEvent(d1Event, dice1, dice2, dice3, dice4);

                    ArrayList<Event> move = new ArrayList<>();
                    move.add(d2Event);
                    move.add(d1Event);
                    dice1.setUsed(false);
                    dice2.setUsed(false);
                    result.add(move);
                    board = boardRef2;

                }

            }
            board = boardRef1;
        }

        board = boardRef;

        return result;
    }

    public boolean isCaptureMove (Event event) {
        if (event instanceof Move) {
            int to = ((Move) event).getTo();
            int from = ((Move) event).getFrom();

            if (board.getColumn(to).isEmpty()) {
                return false;
            } else if (isWhite && board.getColumn(to).peek() instanceof BlackPiece) {
                return true;
            } else if (!isWhite && board.getColumn(to).peek() instanceof WhitePiece) {
                return true;
            }
        } else if (event instanceof Revive) {
            int to = ((Revive) event).getTo();

            if (board.getColumn(to).isEmpty()) {
                return false;
            } else if (isWhite && board.getColumn(to).peek() instanceof BlackPiece) {
                return true;
            } else if (!isWhite && board.getColumn(to).peek() instanceof WhitePiece) {
                return true;
            }
        }

        return false;
    }

    public boolean doesMoveIntoHomeZone (Event event) {
        if (event instanceof Move) {
            if (isWhite && ((Move) event).getTo() < 6 && ((Move) event).getFrom() >= 6) {
                return true;
            } else if (!isWhite && ((Move) event).getTo() > 17 && ((Move) event).getFrom() <= 17) {
                return true;
            }
        }
        return false;
    }

    public boolean isKapiaMove (Event event) {
        if (event instanceof Move) {
            int from = ((Move) event).getFrom();
            int to = ((Move) event).getTo();

            if (board.getColumn(to).size() == 1 && !isCaptureMove(event) && board.getColumn(from).size() != 2) {
                return true;
            }
        } else if (event instanceof Revive) {
            int to = ((Revive) event).getTo();

            if (board.getColumn(to).size() == 1 && !isCaptureMove(event)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSafeMove (Event event) {
        if (isCaptureMove(event)) {
            return false;
        } else if (event instanceof Move) {
            int to = ((Move) event).getTo();
            int from = ((Move) event).getFrom();

            if (board.getColumn(to).isEmpty() || board.getColumn(from).size() == 2) {
                return false;
            }
        } else if (event instanceof Revive) {
            int to = ((Revive) event).getTo();

            if (board.getColumn(to).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public float averageDisplacement (ArrayList<Event> tuple) {
        int total = 0;
        float average = 0;

        for (Event event : tuple) {
            if (event instanceof Move) {
                if (isWhite) {
                    total += Board.SIZE - ((Move) event).getTo();
                } else {
                    total += ((Move) event).getTo();
                }
            } else if (event instanceof Revive) {
                if (isWhite) {
                    total += Board.SIZE - ((Revive) event).getTo();
                } else {
                    total += ((Revive) event).getTo();
                }
            }

            average = (float)total / tuple.size();
        }
        return average;
    }

    public int rankByHighestCaptures (ArrayList<ArrayList<Event>> list) {
        HashMap<ArrayList<Event>, Integer> rankings = new HashMap<>();
        int moveTotal = 0;

        for (ArrayList<Event> tuple : list) {
            int total = 0;

            for (Event event : tuple) {
                if (isCaptureMove(event)) {
                    total ++;
                }
            }

            rankings.put(tuple, total);
            moveTotal += total;
        }

        list.sort((ArrayList<Event> a, ArrayList<Event> b) -> {

            if (rankings.get(a) > rankings.get(b)) {
                return -1;
            } else if (rankings.get(a) < rankings.get(b)) {
                return 1;
            } else {
                return 0;
            }

        });

        return moveTotal;
    }

    public int rankByHomeZoneMoves (ArrayList<ArrayList<Event>> list) {
        HashMap<ArrayList<Event>, Integer> rankings = new HashMap<>();

        int moveTotal = 0;

        for (ArrayList<Event> tuple : list) {
            int total = 0;
            for (Event event : tuple) {
                if (doesMoveIntoHomeZone(event)) {
                    total ++;
                }
            }
            rankings.put(tuple, total);
            moveTotal += total;
        }

        list.sort((ArrayList<Event> a, ArrayList<Event> b) -> {

            if (rankings.get(a) > rankings.get(b)) {
                return -1;
            } else if (rankings.get(a) < rankings.get(b)) {
                return 1;
            } else {
                return 0;
            }

        });
        return moveTotal;
    }

    public int rankByMoveSafety (ArrayList<ArrayList<Event>> list) {
        HashMap<ArrayList<Event>, Integer> rankings = new HashMap<>();
        int moveTotal = 0;

        for (ArrayList<Event> tuple : list) {
            int total = 0;
            for (Event event : tuple) {
                if (isSafeMove(event)) {
                    total++;
                }
            }

            rankings.put(tuple, total);
            moveTotal += total;
        }

        list.sort((ArrayList<Event> a, ArrayList<Event> b) -> {
            if (rankings.get(a) > rankings.get(b)) {
                return -1;
            } else if (rankings.get(a) < rankings.get(b)) {
                return 1;
            }
            return 0;
        });

        return moveTotal;
    }

    public int rankByHighestKapias (ArrayList<ArrayList<Event>> list) {
        HashMap<ArrayList<Event>, Integer> rankings = new HashMap<>();
        int moveTotal = 0;

        for (ArrayList<Event> tuple : list) {
            int total = 0;
            for (Event event : tuple) {
                if (isKapiaMove(event)) {
                    total++;
                }
            }

            rankings.put(tuple, total);
            moveTotal += total;
        }

        list.sort((ArrayList<Event> a, ArrayList<Event> b) -> {
            if (rankings.get(a) > rankings.get(b)) {
                return -1;
            } else if (rankings.get(a) < rankings.get(b)) {
                return 1;
            }
            return 0;
        });

        return moveTotal;
    }

    public int rankByHighestClears (ArrayList<ArrayList<Event>> list) {
        HashMap<ArrayList<Event>, Integer> rankings = new HashMap<>();
        int moveTotal = 0;

        for (ArrayList<Event> tuple : list) {
            int total = 0;
            for (Event event : tuple) {
                if (event instanceof Clear) {
                    total++;
                }
            }

            rankings.put(tuple, total);
            moveTotal += total;
        }

        list.sort((ArrayList<Event> a, ArrayList<Event> b) -> {
            if (rankings.get(a) > rankings.get(b)) {
                return -1;
            } else if (rankings.get(a) < rankings.get(b)) {
                return 1;
            }
            return 0;
        });

        return moveTotal;
    }

    public void rankByFutureSafety (ArrayList<ArrayList<Event>> list) {
        HashMap<ArrayList<Event>, Integer> rankings = new HashMap<>();

        for (ArrayList<Event> tuple : list) {
            int total = 0;
            LocalAggressiveAIPlayer player2 = new LocalAggressiveAIPlayer(!isWhite);
            Board board2 = board.clone();
            Dice dice11 = dice1.clone();
            Dice dice21 = dice2.clone();
            Dice dice31 = dice3.clone();
            Dice dice41 = dice4.clone();

            player2.setBoard(board2);

            for (Event event : tuple) {
                board2.updateEvent(event, dice11, dice21, dice31, dice41);
            }

            for (int i = 1; i <= 6; i ++) {
                for (int j = 1; j <= 6; j ++) {
                    Dice dice12 = new Dice();
                    Dice dice22 = new Dice();
                    Dice dice32 = new Dice();
                    Dice dice42 = new Dice();

                    dice12.setValue(i);
                    dice12.setUsed(false);
                    dice22.setValue(j);
                    dice22.setUsed(false);

                    if (i == j) {
                        dice32.setValue(i);
                        dice32.setUsed(false);
                        dice42.setValue(i);
                        dice42.setUsed(false);
                    } else {
                        dice32.setUsed(true);
                        dice42.setUsed(true);
                    }

                    player2.setDice(dice12, dice22, dice32, dice42);
                    total += player2.rankByHighestCaptures(player2.generateMoveTuples(dice12, dice22, dice32, dice42));
                }
                rankings.put(tuple, total);
            }
        }

        list.sort((ArrayList<Event> a, ArrayList<Event> b) -> {
            if (rankings.get(a) > rankings.get(b)) {
                return 1;
            } else if (rankings.get(a) < rankings.get(b)) {
                return -1;
            }
            return 0;
        });

        return;
    }

    public void rankByAverageDisplacement (ArrayList<ArrayList<Event>> list) {
        HashMap<ArrayList<Event>, Float> rankings = new HashMap<>();

        for (ArrayList<Event> tuple : list) {
            rankings.put(tuple, averageDisplacement(tuple));
        }

        list.sort((ArrayList<Event> a, ArrayList<Event> b) -> {
            if (rankings.get(a) > rankings.get(b)) {
                return -1;
            } else if (rankings.get(a) < rankings.get(b)) {
                return 1;
            }
            return 0;
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
