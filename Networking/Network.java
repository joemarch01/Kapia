package Networking;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Network {

    public static Socket makeCall (String host) {
        Socket call = null;

        try {
            call = new Socket(host, 8000);
            call.setSoTimeout(100000);
            call.setTcpNoDelay(true);

        } catch (IOException e) {

        }

        return call;
    }

    public static Socket acceptCall () {
        ServerSocket listen;
        Socket connection = null;

        try {
            listen = new ServerSocket(8000);
            listen.setSoTimeout(100000);
            connection = listen.accept();
            connection.setTcpNoDelay(true);
        } catch (IOException e) {

        }

        return connection;
    }
}
