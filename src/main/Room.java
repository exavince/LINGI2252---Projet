package main;

import main.item.Item;
import main.sensor.Sensor;

import java.util.ArrayList;

public class Room {
    private final RoomType type;
    private final ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ConnectedHouse house;

    public Room(RoomType typeIn) {
        type = typeIn;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Adds items to the Event bus. At least one item is required to call the method.
     */
    Room register(Item minimumItem, Item... itemsIn) {
        minimumItem.setRoom(this);
        items.add(minimumItem);

        for (Item item : itemsIn) {
            item.setRoom(this);
            items.add(item);
        }
        return this;
    }

    /**
     * Adds sensors. At least one sensor is required to call the method.
     */
    Room register(Sensor minimumSensor, Sensor... sensorsIn) {
        minimumSensor.setRoom(this);
        sensors.add(minimumSensor);

        for (Sensor sensor : sensorsIn) {
            sensor.setRoom(this);
            sensors.add(sensor);
        }
        return this;
    }

    public ConnectedHouse getHouse() {
        if (house == null) {
            throw new RuntimeException("House was not set");
        }
        return house;
    }

    public void setHouse(ConnectedHouse house) {
        this.house = house;
    }

    public RoomType getType() {
        return type;
    }
}
