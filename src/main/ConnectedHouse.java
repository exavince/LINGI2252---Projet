package main;

import main.item.Item;
import main.sensor.Sensor;

import java.util.ArrayList;
import java.util.Arrays;

public class ConnectedHouse {
    private final ArrayList<Room> rooms = new ArrayList<>();

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Adds rooms to the house
     */
    void register(Room minimumRoom, Room... roomsIn) {
        rooms.add(minimumRoom);
        rooms.addAll(Arrays.asList(roomsIn));
    }

    /**
     * Sends a message to all items of type itemClass in all rooms of type roomType
     * TODO String is not a good idea for a command
     */
    public void send(RoomType roomType, Class<? extends Item> itemClass, String message) {
        for (Room room : rooms) {
            if (room.getType() == roomType) {
                for (Item item : room.getItems()) {
                    if (itemClass.isInstance(item)) {
                        item.onEvent(message, this);
                    }
                }
            }
        }
    }

    public void triggerSensor(RoomType roomType, Class<? extends Sensor> sensorClass) {
        for (Room room : this.getRooms()) {
            if (room.getType() == roomType) {
                for (Sensor sensor : room.getSensors()) {
                    if (sensorClass.isInstance(sensor)) {
                        for (Item item : room.getItems()) {
                            item.onEvent(sensor.askReport(), this);
                        }
                    }
                }
            }
        }


    }

    /**
     * Sends a message to all items of type itemClass in all rooms
     * TODO String is not a good idea for a command
     */
    public void sendAllRooms(Class<? extends Item> itemClass, String message) {
        for (Room room : rooms) {
            for (Item item : room.getItems()) {
                if (itemClass.isInstance(item)) {
                    item.onEvent(message, this);
                }
            }
        }
    }


    public void askSensorReport(Item askingItem, Class<? extends Sensor> sensorClass) {
        for (Room room : rooms) {
            for (Sensor sensor : room.getSensors()) {
                if (sensorClass.isInstance(sensor))
                    askingItem.onEvent(sensor.askReport(), this);
            }
        }
    }
}
