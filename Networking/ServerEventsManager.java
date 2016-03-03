package Networking;

import Event.*;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class ServerEventsManager {

    static int portNumber;

    public ServerEventsManager (int portNumber) throws Exception{
        this.portNumber = portNumber;

        ServerSocket s = new ServerSocket(50236);
        Socket test;
        test = s.accept();


        BufferedReader in = new BufferedReader(new InputStreamReader(test.getInputStream()));
        DataOutputStream out = new DataOutputStream(test.getOutputStream());

        out.writeBytes("Testing");
        System.out.println(in.readLine());

    }

    public void postEvent (Event event) {

    }

    public Event parseEventString (String eventString) {
        return new Event();
    }

    public Event fetchEvent (long timeOut) {
        return new Event();
    }
}
