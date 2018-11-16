package main.item.device;

import main.item.Item;
import main.message.SoundMessage;

public class VoiceAssistant extends Item {

    @Override
    public void onEvent(Object message) {
        if (message.equals("play_morning_playlist")) {
            getHouse().sendToItems(new SoundMessage("The Morning Playlist"));
        }
    }
}
