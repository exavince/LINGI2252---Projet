package main;

import main.item.Item;
import main.sensor.Sensor;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    private final RoomType type;
    private final ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

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
        items.add(minimumItem);
        items.addAll(Arrays.asList(itemsIn));
        return this;
    }

    /**
     * Adds sensors. At least one sensor is required to call the method.
     */
    Room register(Sensor minimumSensor, Sensor... sensorsIn) {
        sensors.add(minimumSensor);
        sensors.addAll(Arrays.asList(sensorsIn));
        return this;
    }

    public RoomType getType() {
        return type;
    }
}
