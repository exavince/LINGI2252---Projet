package main.item.sensor;

import main.item.Item;
import main.message.VoiceCommand;

public class Microphone extends Item {

    @Override
    public void onEvent(Object message) {
        if (message.equals("play_morning_playlist")) {
            getHouse().sendToItems(VoiceCommand.PLAY_MORNING_PLAYLIST);
        }
    }
}
