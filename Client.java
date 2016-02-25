import Board.Board;
import Game.Game;
import Player.LocalAggressiveAIPlayer;
import Player.LocalHumanPlayer;

public class Client {
    public static void main (String[] args) {
        Game g = new Game(new LocalHumanPlayer("Jim", true), new LocalAggressiveAIPlayer(false));
      //  g.setToReviveState();
        g.play();
    }
}