package main;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.internal.parser.JSONParser;
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

import javax.tools.Diagnostic;

import static main.RoomType.*;

public class HomeBuilder {

    private ConnectedHouse house;

    public ConnectedHouse createHouse() {
        this.house = new ConnectedHouse();
        createRoom();
        createItem();
        createSensor();
        return this.house;
    }

    private void createRoom(){
        JsonParser gson = new JsonParser();
        try {
            Object obj = gson.parse(new FileReader("package.json"));
            JsonObject json = (JsonObject) obj;

            JsonArray rooms = json.getAsJsonArray("ROOM");
            for (int i=0; i < rooms.size(); i++) {
                System.out.println(rooms.size());
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


        } catch (IOException e) {}
    }

    private void createItem(){
        ArrayList<Room> rooms = house.getRooms();

        JsonParser gson = new JsonParser();
        try {
            Object obj = gson.parse(new FileReader("package.json"));
            JsonObject json = (JsonObject) obj;

            JsonObject item = json.getAsJsonObject("ITEM");

            for (int i=0; i < rooms.size(); i++) {
                JsonArray itemTMP = item.getAsJsonArray(rooms.get(i).getType().toString());
                for (int j=0; j < itemTMP.size(); j++) {
                    switch (itemTMP.get(j).getAsString()) {
                        case "speaker":
                            rooms.get(i).register(new ConnectedSpeakers());
                            break;
                        case "heating":
                            rooms.get(i).register(new HeatingController());
                            break;
                        case "light":
                            rooms.get(i).register(new LightController());
                            break;
                        case "clock":
                            rooms.get(i).register(new ClockController());
                            break;
                        case "coffee":
                            rooms.get(i).register(new CoffeeMachine());
                            break;
                        case "voice":
                            rooms.get(i).register(new VoiceAssistant());
                            break;
                        case "door":
                            rooms.get(i).register(new DoorController());
                            break;
                        case "garage":
                            rooms.get(i).register(new GarageDoorController());
                            break;
                        default:
                            break;
                    }
                }
            }

        } catch (IOException e) {}
    }

    private void createSensor(){
        ArrayList<Room> rooms = house.getRooms();

        JsonParser gson = new JsonParser();
        try {
            Object obj = gson.parse(new FileReader("package.json"));
            JsonObject json = (JsonObject) obj;

            JsonObject sensor = json.getAsJsonObject("SENSOR");

            for (int i=0; i < rooms.size(); i++) {
                JsonArray sensorTMP = sensor.getAsJsonArray(rooms.get(i).getType().toString());
                System.out.println(rooms.get(i).getType().toString());
                for (int j=0; j < sensorTMP.size(); j++) {
                    switch (sensorTMP.get(j).getAsString()) {
                        case "movement":
                            rooms.get(i).register(new MovementsSensor());
                            break;
                        case "microphone":
                            rooms.get(i).register(new Microphone());
                            break;
                        case "temperature":
                            rooms.get(i).register(new TemperatureSensor());
                            break;
                        default:
                            break;
                    }
                }
            }

        } catch (IOException e) {}
    }
}
