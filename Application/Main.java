package Application;


import Game.Game;
import Graphics.GameWindow;
import Graphics.ResourceManager;
import Networking.Network;
import Player.LocalAggressiveAIPlayer;
import Player.LocalHumanPlayer;
import Player.NetworkHumanPlayer;
import Player.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {


    public static Socket call (String host) {
        Socket socket = null;

        try {
            socket = new Socket(host, 8000);
        } catch (Exception e) {

        }
        return socket;
    }

    public static Socket accept () {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(8000);
            serverSocket.setSoTimeout(100000);
            socket = serverSocket.accept();
        } catch (Exception e) {

        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (Exception e) {}
        }

        return socket;
    }

    public static Game getGameFromConsole () {
        Game game = null;
        Scanner scanner = new Scanner(System.in);
        String player1Name;
        String player2Name;
        Player player1;
        Player player2 = null;

        System.out.println("Enter your name");
        player1Name = scanner.nextLine();
        player1 = new LocalHumanPlayer(player1Name, true);

        System.out.println("Enter your opponent's name");
        player2Name = scanner.nextLine();

        System.out.println("Enter your opponent's class (AI, Network, Local)");
        String player2Class = scanner.nextLine();

        if (player2Class.equals("AI")) {
            player2 = new LocalAggressiveAIPlayer(false);
        } else if (player2Class.equals("Local")) {
            player2 = new LocalHumanPlayer(player2Name, false);
        } else if (player2Class.equals("Network")) {
            String input = "";

            while (!input.equals("quit")) {
                input = scanner.nextLine();

                if (input.equals("call")) {
                    System.out.println("Enter host name");
                    String hostName = scanner.nextLine();
                    player2 = Network.makeCall(hostName);
                } else if (input.equals("host")) {
                    player2 = Network.acceptCall();
                }

                if (player2 != null) {
                    player2.setTag(player2Name);
                    break;
                }
            }

        } else {
            player2 = new LocalHumanPlayer(player2Name, false);
        }

        game = new Game(player1, player2);
        return game;
    }

    public static void main (String[] args) throws Exception {

        boolean quit = false;
        Scanner scanner = new Scanner(System.in);

        while (!quit) {
            String input = scanner.nextLine();

            if (input.equals("play")) {
                ResourceManager.load();
                Game game = getGameFromConsole();
                if (game != null) {
                    GameWindow gameWindow = new GameWindow(game);
                    game.play();
                }
            } else if (input.equals("quit")) {
                quit = true;
            }

        }
    }
}
