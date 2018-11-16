package main.item.control;

import main.item.Item;
import main.message.SoundMessage;

import static main.RoomType.BEDROOM;

public class ClockController extends Item {

    @Override
    public void onEvent(Object message) {
        if (message.equals("trigger_alarm")) {
            getHouse().findRoom(BEDROOM).sendToItems(new SoundMessage("Alarm ! Wake Up !"));
        }
    }
}
