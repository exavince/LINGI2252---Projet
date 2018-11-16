package main;

import main.item.ItemSubject;
import main.sensor.MovementsSensor;
import main.sensor.Sensor;
import main.sensor.SensorSubject;

import java.util.ArrayList;

public class ConnectedHouse implements ItemSubject, SensorSubject {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private WeatherStatus weatherStatus = WeatherStatus.SUNNY;
    private RoomType position = RoomType.NOWHERE;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Adds rooms to the house
     */
    void attach(Room minimumRoom, Room... roomsIn) {
        minimumRoom.setHouse(this);
        rooms.add(minimumRoom);
        for (Room room : roomsIn) {
            room.setHouse(this);
            rooms.add(room);
        }
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

    public void moveTo(RoomType room) {
        if (this.position == room) System.err.println("User was already in the room " + room);
        else {
            System.out.println("## Moving to room " + room);
            RoomType oldRoom = this.position;
            this.position = room;
            triggerSensor(oldRoom, MovementsSensor.class, null);
            triggerSensor(this.position, MovementsSensor.class, null);
        }
    }

    public WeatherStatus getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(WeatherStatus weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    @Override
    public void sendToItems(Object message) {
        for (Room room : getRooms()) {
            room.sendToItems(message);
        }
    }

    public Room findRoom(RoomType type) {
        for (Room room : getRooms()) {
            if (room.getType() == type) return room;
        }
        return Room.NONE;
    }

    @Override
    public void sendToSensors(Object message) {
        for (Room room : getRooms()) {
            room.sendToSensors(message);
        }
    }
}
