package main.item.device;

import main.ConnectedHouse;
import main.item.Item;
import main.item.sounds.ConnectedSpeakers;

public class VoiceAssistant implements Item {
    @Override
    public void onEvent(String message, ConnectedHouse house) {
        if (message.equals("play_morning_playlist")) {
            house.sendAllRooms(ConnectedSpeakers.class, "music:Morning Playlist");
        }
    }
}
