package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.item.control.ClockController;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.item.device.CoffeeMachine;
import main.item.device.VoiceAssistant;
import main.item.sounds.ConnectedSpeakers;
import main.sensor.Microphone;
import main.sensor.MovementsSensor;
import main.sensor.TemperatureSensor;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static main.RoomType.*;


public class HomeBuilder {

    private ConnectedHouse house;


    public ConnectedHouse createHouse(String config) {
        this.house = new ConnectedHouse();

        try {
            JsonParser gson = new JsonParser();
            Object obj = gson.parse(new FileReader(config));
            JsonObject json = (JsonObject) obj;
            createRoom(json);
            createItem(json);
            createSensor(json);
        } catch (IOException e) {
            System.err.println(e);
        }

        return this.house;
    }


    private void createRoom(JsonObject json) {
        JsonArray rooms = json.getAsJsonArray("ROOM");

        for (int i = 0; i < rooms.size(); i++) {
            switch (rooms.get(i).getAsString()) {
                case "BEDROOM":
                    house.register(new Room(BEDROOM));
                    break;
                case "KITCHEN":
                    house.register(new Room(KITCHEN));
                    break;
                case "BATHROOM":
                    house.register(new Room(BATHROOM));
                    break;
                case "GARAGE":
                    house.register(new Room(GARAGE));
                    break;
                case "LIVING_ROOM":
                    house.register(new Room(LIVING_ROOM));
                    break;
            }
        }
    }


    private void createItem(JsonObject json) {
        ArrayList<Room> rooms = house.getRooms();
        JsonObject item = json.getAsJsonObject("ITEM");

        for (Room room : rooms) {
            JsonArray itemTMP = item.getAsJsonArray(room.getType().toString());
            for (int j = 0; j < itemTMP.size(); j++) {
                switch (itemTMP.get(j).getAsString()) {
                    case "speaker":
                        room.register(new ConnectedSpeakers());
                        break;
                    case "heating":
                        room.register(new HeatingController());
                        break;
                    case "light":
                        room.register(new LightController());
                        break;
                    case "clock":
                        room.register(new ClockController());
                        break;
                    case "coffee":
                        room.register(new CoffeeMachine());
                        break;
                    case "voice":
                        room.register(new VoiceAssistant());
                        break;
                    case "door":
                        room.register(new DoorController());
                        break;
                    case "garage":
                        room.register(new GarageDoorController());
                        break;
                    default:
                        break;
                }
            }
        }

    }


    private void createSensor(JsonObject json) {
        ArrayList<Room> rooms = house.getRooms();
        JsonObject sensor = json.getAsJsonObject("SENSOR");

        for (Room room : rooms) {
            JsonArray sensorTMP = sensor.getAsJsonArray(room.getType().toString());
            for (int j = 0; j < sensorTMP.size(); j++) {
                switch (sensorTMP.get(j).getAsString()) {
                    case "movement":
                        room.register(new MovementsSensor());
                        break;
                    case "microphone":
                        room.register(new Microphone());
                        break;
                    case "temperature":
                        room.register(new TemperatureSensor());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
