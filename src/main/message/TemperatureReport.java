package main.message;

import main.Room;

public class TemperatureReport {
    final int temperature;
    final Room room;

    public TemperatureReport(Room room, int temperature) {
        this.temperature = temperature;
        this.room = room;
    }

    public int getTemperature() {
        return temperature;
    }

    public Room getRoom() {
        return room;
    }
}
