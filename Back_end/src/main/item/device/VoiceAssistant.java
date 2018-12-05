package main.item.device;

import main.item.Item;
import main.message.SoundMessage;
import main.message.VoiceCommand;

public class VoiceAssistant extends Item {

    @Override
    public void onEvent(Object message) {
        if (message.equals(VoiceCommand.PLAY_MORNING_PLAYLIST)) {
            println(" detected play command and music");
            getHouse().sendToItems(new SoundMessage("The Morning Playlist"));
        }
    }
}
