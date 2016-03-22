package Networking;

import Player.*;
import Application.MainMenu;

public class NetworkListener implements Runnable {
    @Override
    public void run() {
        while (true) {
            Player player = Network.acceptCall();
            if (player != null) {
                MainMenu.registerCall(player);
            }
        }
    }

}
