package main.routine;

import main.ConnectedHouse;

import static main.RoomType.BEDROOM;
import static main.RoomType.KITCHEN;

public class SoonWakeUpRoutine {
    public void call(ConnectedHouse house) {
        // TODO Configurable ?
        house.findRoom(KITCHEN).sendToItems("make_coffee");
        house.sendToItems("start_heating");
    }
}
