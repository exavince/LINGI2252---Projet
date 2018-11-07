package main.item.sounds;

import main.ConnectedHouse;
import main.item.Item;

public class ConnectedSpeakers implements Item {

    @Override
    public void onEvent(String message, ConnectedHouse house) {
        if (message.startsWith("sound:")) {
            System.out.println("Connected speakers play this sound: " + message);
        }
    }
}
