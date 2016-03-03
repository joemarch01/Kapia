package Application;


import Game.Game;
import Graphics.GameWindow;
import Graphics.ResourceManager;
import Player.LocalAggressiveAIPlayer;
import Player.LocalHumanPlayer;

public class Main {

    static boolean isClient;

    public static void main (String[] args) {
        Game g = new Game(new LocalHumanPlayer("Jim", true), new LocalAggressiveAIPlayer(false));
        ResourceManager.load();
        g.setClearState();
        GameWindow gameWindow = new GameWindow(g);
        g.play();
    }

    public static boolean isClient () {
        return isClient;
    }
}
