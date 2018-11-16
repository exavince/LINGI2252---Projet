package main.sensor;

import main.ConnectedHouse;
import main.Room;

public abstract class HouseObject {
    private Room room;

    protected ConnectedHouse getHouse() {
        return getRoom().getHouse();
    }

    protected Room getRoom() {
        if (room == null) {
            throw new RuntimeException("Room was not set");
        }
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    protected void println(String value) {
        System.out.println("[" + this.getClass().getSimpleName() + " Room:" + getRoom().getType() + " House:" + getHouse() + "] " + value);
    }
}
