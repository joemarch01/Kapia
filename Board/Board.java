package Board;

import java.util.Stack;
import Event.*;

/**
 * Created by jm360 on 22/02/16.
 */
public class Board {
    
    public static final int SIZE = 24;
    
    Stack<Piece> [] board;

    public Board () {
        board =  (Stack<Piece>[]) new Stack [SIZE];

        for (int i = 0; i < board.length; i ++) {
            board[i] = new Stack<Piece>();
        }

        //Initialise the Black Pieces
        board[0].add(new BlackPiece());
        board[0].add(new BlackPiece());
        board[11].add(new BlackPiece());
        board[11].add(new BlackPiece());
        board[11].add(new BlackPiece());
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

    public Stack<Piece> getColumn (int column) {
        if (column >= SIZE) {
            return null;
        } else {
            return board[column];
        }
    }

    public boolean isMoveLegal (Move move, int dice1, int dice2) {
        return
    }

    public boolean move (Move move) {
        board[move.getTo()].add(board[move.getFrom()].pop());
        return true;
    }
}
