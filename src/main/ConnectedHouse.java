package main;

import main.item.ItemSubject;
import main.item.control.light.LightController;
import main.message.SoundMessage;
import main.routine.HomeMood;
import main.sensor.SensorSubject;

import java.util.ArrayList;

public class ConnectedHouse implements ItemSubject, SensorSubject {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private WeatherStatus weatherStatus = WeatherStatus.SUNNY;

    public RoomType getPosition() {
        return position;
    }

    private RoomType position = RoomType.NOWHERE;
    private HomeMood mood = HomeMood.NORMAL;

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

    public void moveTo(RoomType room) {
        if (this.position == room) System.err.println("User was already in the room " + room);
        else {
            System.out.println("## Moving to room " + room);
            RoomType oldRoom = this.position;
            this.position = room;
            findRoom(oldRoom).sendToSensors("movement_detected");
            findRoom(this.position).sendToSensors("movement_detected");
        }
    }

    public WeatherStatus getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(WeatherStatus weatherStatus) {
        this.weatherStatus = weatherStatus;
        sendToSensors("check_weather");
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

    public HomeMood getMood() {
        return mood;
    }

    public void setMood(HomeMood mood) {
        this.mood = mood;
        switch (mood) {
            case NORMAL:
                getRooms().forEach((room -> room.getItems().forEach((item -> {
                    if (item instanceof LightController) {
                        ((LightController) item).setIntensity(100);
                    }
                }))));
                break;
            case ROMANTIC:
                getRooms().forEach((room -> room.getItems().forEach((item -> {
                    if (item instanceof LightController) {
                        ((LightController) item).setIntensity(50);
                    }
                }))));
                sendToItems(new SoundMessage("Jazz Music starts playing"));
                break;
        }
    }
}
