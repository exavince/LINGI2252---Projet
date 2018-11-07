package main.item.control;

import main.ConnectedHouse;
import main.RoomType;
import main.item.Item;
import main.item.sounds.ConnectedSpeakers;

public class ClockController implements Item {
    @Override
    public void onEvent(String message, ConnectedHouse house) {
        if (message == "trigger_alarm") {
            house.send(RoomType.BEDROOM, ConnectedSpeakers.class, "music:ALARRRRM");
        }
    }
}
