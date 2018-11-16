package main;

import main.item.Item;
import main.sensor.Sensor;

import java.util.ArrayList;

public class Room {
    private final RoomType type;
    private final ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ConnectedHouse house;
    private int temperature = 0;
    private int lighting = 0; // TODO Replace by Lamp item ?

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

    /**
     * Should only be accessed by {@code TemperatureSensor} or commands
     *
     * @return temperature of the room
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * Edit the temperature of the room
     *
     * @param temperature The new temperature of the room
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Item getItem(Class<? extends Item> itemClass) {
        for (Item item : getItems()) {
            if (itemClass.isInstance(item)) {
                return item;
            }
        }
        return null;
    }

    public int getLighting() {
        return lighting;
    }

    public void setLighting(int lighting) {
        this.lighting = lighting;
    }
}
