package main.item;

import main.ConnectedHouse;
import main.Room;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Item {
    private static final Logger LOGGER = Logger.getLogger(Item.class.getName());
    private Room room;

    abstract public void onEvent(Object message);

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
        LOGGER.log(Level.INFO, info);
    }
}
