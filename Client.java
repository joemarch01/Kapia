import Game.Game;
import Graphics.GameWindow;
import Graphics.ResourceManager;
import Networking.ServerEventsManager;
import Player.LocalAggressiveAIPlayer;
import Player.LocalHumanPlayer;

public class Client {
    public static void main (String[] args) throws Exception {
        Game g = new Game(new LocalHumanPlayer("Jim", true), new LocalAggressiveAIPlayer(false));
        ResourceManager.load();
       //g.setClearState();
        GameWindow gameWindow = new GameWindow(g);
       g.play();
        //ServerEventsManager s = new ServerEventsManager(0);
    }
}