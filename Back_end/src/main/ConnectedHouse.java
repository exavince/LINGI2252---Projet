package main;

import main.command.Command;
import main.command.CommandParser;
import main.item.ItemSubject;
import main.item.control.light.LightController;
import main.message.SoundMessage;
import main.routine.HomeMood;

import java.util.ArrayList;

public class ConnectedHouse implements ItemSubject {
    private final ArrayList<HouseObserver> observers = new ArrayList<>();
    private final ArrayList<Room> rooms = new ArrayList<>();
    private WeatherStatus weatherStatus = WeatherStatus.SUNNY;
    private RoomType position = RoomType.NOWHERE;
    private HomeMood mood = HomeMood.NORMAL;

    public RoomType getPosition() {
        return position;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Command sendCommand(String commandIn) {
        CommandParser commandParser = new CommandParser(commandIn);
        Command command = commandParser.parse();
        if (command == Command.EXIT || command == Command.DONE) {
            return command;
        } else {
            command.interpret(this);
            return command;
        }
    }

    public void registerObserver(HouseObserver o) {
        observers.add(o);
    }

    void notifyObservers() {
        observers.forEach(HouseObserver::update);
    }

    /**
     * Adds rooms to the house
     */
    public void attach(Room minimumRoom, Room... roomsIn) {
        minimumRoom.setHouse(this);
        rooms.add(minimumRoom);
        for (Room room : roomsIn) {
            room.setHouse(this);
            rooms.add(room);
        }
    }

    public void moveTo(RoomType room) {
        RoomType oldRoom = this.position;
        this.position = room;
        findRoom(oldRoom).sendToItems("movement_detected");
        findRoom(this.position).sendToItems("movement_detected");
    }

    public WeatherStatus getWeatherStatus() {
        return weatherStatus;
    }

    public void setWeatherStatus(WeatherStatus weatherStatus) {
        this.weatherStatus = weatherStatus;
        sendToItems("check_weather");
        notifyObservers();
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
        notifyObservers();
    }
}
