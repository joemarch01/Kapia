import Board.Board;
import Game.Game;
import Player.LocalHumanPlayer;

public class Client {
    public static void main (String[] args) {
        Game g = new Game(new LocalHumanPlayer("Jim", true), new LocalHumanPlayer("Robert", false));
        g.play();
    }
}