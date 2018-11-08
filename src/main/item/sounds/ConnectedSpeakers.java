package main.item.sounds;

import main.item.Item;
import main.message.SoundMessage;

public class ConnectedSpeakers extends Item {

    @Override
    public void onEvent(Object message) {
        if (message instanceof SoundMessage) {
            println("play this sound " + ((SoundMessage) message).getContent());
        }
    }
}
