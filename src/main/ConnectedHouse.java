package main;

import main.item.Item;
import main.sensor.Sensor;

import java.util.ArrayList;

public class ConnectedHouse {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private WeatherStatus weather = WeatherStatus.SUNNY;
    private RoomType position = RoomType.NOWHERE;

    ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Adds rooms to the house
     */
    void register(Room minimumRoom, Room... roomsIn) {
        minimumRoom.setHouse(this);
        rooms.add(minimumRoom);
        for (Room room : roomsIn) {
            room.setHouse(this);
            rooms.add(room);
        }
    }

    /**
     * Sends a message to all items of type itemClass in all rooms of type roomType
     */
    public void send(RoomType roomType, Class<? extends Item> itemClass, Object message) {
        for (Room room : rooms) {
            if (room.getType() == roomType) {
                for (Item item : room.getItems()) {
                    if (itemClass.isInstance(item)) {
                        item.onEvent(message);
                    }
                }
            }
        }
    }

    public void send(Object message) {
        rooms.forEach(room -> room.getItems().forEach(item -> item.onEvent(message)));
    }

    public void triggerSensor(RoomType roomType, Class<? extends Sensor> sensorClass, Object message) {
        for (Room room : this.getRooms()) {
            if (room.getType() == roomType) {
                for (Sensor sensor : room.getSensors()) {
                    if (sensorClass.isInstance(sensor)) {
                        sensor.trigger(message);
                    }
                }
            }
        }


    }

    /**
     * Sends a message to all items of type itemClass in all rooms
     */
    public void sendAllRooms(Class<? extends Item> itemClass, Object message) {
        for (Room room : rooms) {
            for (Item item : room.getItems()) {
                if (itemClass.isInstance(item)) {
                    item.onEvent(message);
                }
            }
        }
    }
}
