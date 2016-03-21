package Networking;

import Player.NetworkPlayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Network {

    private static int currentSocket = 8000;

    public static NetworkPlayer makeCall (String host) {
        Socket call = null;
        NetworkPlayer result = null;

        try {
            call = new Socket(host, currentSocket ++);
            call.setSoTimeout(100000);
            call.setTcpNoDelay(true);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //'Flip coin' to go first
        boolean goFirst = Math.random() > 0.5;
        DataOutputStream output;
        try {
            output = new DataOutputStream(call.getOutputStream());
            if (goFirst) {
                //Make an illegal move to start, then get legal move from player in game play loop
                //If go first, then the network player must go second
                result = new NetworkPlayer(host, false, call);
                output.writeBytes("0-0(-1|-1),(-1|-1);\n");
            } else {
                //As per the protocol
                result = new NetworkPlayer(host, true, call);
                output.writeBytes("Pass\n");
            }
        } catch (Exception e) {
            System.out.println("Error confirming network connection");
            System.out.println(e.getMessage());
            return null;
        }

        return result;
    }

    public static NetworkPlayer acceptCall () {
        ServerSocket listen;
        Socket connection = null;
        NetworkPlayer result = null;

        try {
            listen = new ServerSocket(currentSocket ++);
            listen.setSoTimeout(100000);
            connection = listen.accept();
            connection.setTcpNoDelay(true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String networkText = input.readLine();

            if (networkText == null) {
                //Error with network communication
                throw new Exception();
            } else if (networkText.equals("Pass")){
                result = new NetworkPlayer(connection.getLocalAddress().getHostName(), false, connection);
            } else {
                result = new NetworkPlayer(connection.getLocalAddress().getHostName(), true, connection);
            }
        } catch (Exception e) {
            System.out.println("Error confirming the network connection");
        }

        return result;
    }

    public static int handshakeWithPlayer (NetworkPlayer player) {
        String feedback = player.readFromNetwork();
        if (feedback == null) {
            //Error with connection
            return -1;
        } else if (feedback.equals("")) {
        }
        return 0;
    }
}
