package main.item.control;

import main.RoomType;
import main.item.Item;
import main.item.sounds.ConnectedSpeakers;
import main.message.SoundMessage;

public class ClockController extends Item {
    @Override
    public void onEvent(Object message) {
        if (message.equals("trigger_alarm")) {
            getHouse().send(RoomType.BEDROOM, ConnectedSpeakers.class, new SoundMessage("Alarm ! Wake Up !"));
        }
    }
}
