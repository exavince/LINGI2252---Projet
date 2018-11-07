package main.message;

import main.Room;

public class TemperatureReport {
    final Room room;

    public TemperatureReport(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
