package main.item;

import main.ConnectedHouse;
import main.ConnectedHouseSimulator;
import main.Room;

public abstract class Item {
    abstract public void onEvent(Object message);

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
        String info = "[" + this.getClass().getSimpleName() + " Room:" + getRoom().getType() + " House:" + getHouse() + "] " + value;
        System.out.println(info);
        ConnectedHouseSimulator.dataOUT.add(info);
    }
}
