import Board.Board;
import Game.Game;
import Graphics.GameWindow;
import Networking.ServerEventsManager;
import Player.LocalAggressiveAIPlayer;
import Player.LocalHumanPlayer;

public class Client {
    public static void main (String[] args) throws Exception {
        Game g = new Game(new LocalHumanPlayer("Jim", true), new LocalAggressiveAIPlayer(false));
       g.setToReviveState();
        GameWindow gameWindow = new GameWindow(g);
       g.play();
        //ServerEventsManager s = new ServerEventsManager(0);
    }
}