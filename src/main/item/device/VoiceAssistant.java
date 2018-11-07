package main.item.device;

import main.item.Item;
import main.item.sounds.ConnectedSpeakers;

public class VoiceAssistant extends Item {
    private VoiceAssistant() {
    }

    public static VoiceAssistant createVoiceAssistant() {
        return new VoiceAssistant();
    }

    @Override
    public void onEvent(Object message) {
        if (message.equals("play_morning_playlist")) {
            getHouse().sendAllRooms(ConnectedSpeakers.class, "The Morning Playlist");
        }
    }
}
