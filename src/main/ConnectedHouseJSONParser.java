package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.item.Item;
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
import main.sensor.Sensor;
import main.sensor.TemperatureSensor;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static main.RoomType.*;

public class ConnectedHouseJSONParser implements ConnectedHouseParser {
    private ConnectedHouseBuilder house;

    @Override
    public ConnectedHouse parse(String inputConfigFile, String inputStateFile) throws FileNotFoundException {
        this.house = new ConnectedHouseBuilder();
        JsonParser gson = new JsonParser();
        JsonObject json = (JsonObject) gson.parse(new FileReader(inputConfigFile));
        JsonObject stateJson = (JsonObject) gson.parse(new FileReader(inputStateFile));
        createRooms(json, stateJson);

        return this.house.getHouse();
    }

    private void createRooms(JsonObject json, JsonObject stateJson) {
        JsonArray rooms = json.getAsJsonArray("ROOM");
        JsonObject items = json.getAsJsonObject("ITEM");
        JsonObject sensors = json.getAsJsonObject("SENSOR");

        for (int i = 0; i < rooms.size(); i++) {
            String roomName =
                    rooms.get(i).getAsString();
            Room room = roomFromString(roomName);
            JsonArray itemTMP = items.getAsJsonArray(room.getType().toString());
            for (int j = 0; j < itemTMP.size(); j++) {
                String itemName = itemTMP.get(j).getAsString();
                room.attach(itemFromString(itemName));
            }
            JsonArray sensorTMP = sensors.getAsJsonArray(room.getType().toString());
            for (int j = 0; j < sensorTMP.size(); j++) {
                String sensorName = sensorTMP.get(j).getAsString();
                room.attach(sensorFromString(sensorName));
            }
            setValue(room, stateJson);


            house.register(room);
        }
    }

    private Room roomFromString(String roomName) {
        switch (roomName) {
            case "BEDROOM":
                return new Room(BEDROOM);
            case "KITCHEN":
                return new Room(KITCHEN);
            case "BATHROOM":
                return new Room(BATHROOM);
            case "GARAGE":
                return new Room(GARAGE);
            case "LIVING_ROOM":
                return new Room(LIVING_ROOM);
            default:
                throw new RuntimeException("Unknown room type : " + roomName);
        }
    }

    private Item itemFromString(String itemName) {
        switch (itemName) {
            case "speaker":
                return new ConnectedSpeakers();
            case "heating":
                return new HeatingController();
            case "light":
                return new LightController();
            case "clock":
                return new ClockController();
            case "coffee":
                return new CoffeeMachine();
            case "voice":
                return new VoiceAssistant();
            case "door":
                return new DoorController();
            case "garage":
                return new GarageDoorController();
            default:
                throw new RuntimeException("Unknown item type : " + itemName);
        }

    }

    private Sensor sensorFromString(String sensorName) {
        switch (sensorName) {
            case "movement":
                return new MovementsSensor();
            case "microphone":
                return new Microphone();
            case "temperature":
                return new TemperatureSensor();
            default:
                throw new RuntimeException("Unknown sensor type : " + sensorName);
        }
    }


    private void setValue(Room room, JsonObject json) {
        JsonObject temp = json.getAsJsonObject(room.getType().toString());
        if (temp != null) {
            if (temp.get("speaker") != null) {
                Item item = room.getItem(ConnectedSpeakers.class);
                ConnectedSpeakers speakers = (ConnectedSpeakers) item;
                speakers.setIntensity(temp.get("speaker").getAsInt());
            }
            if (temp.get("light") != null) {
                Item item = room.getItem(LightController.class);
                LightController light = (LightController) item;
                light.setIntensity(temp.get("light").getAsInt());
            }
            if (temp.get("heating") != null) {
                Item item = room.getItem(HeatingController.class);
                HeatingController heating = (HeatingController) item;
                heating.setDesiredTemperature(temp.get("heating").getAsInt());
            }
        }
    }
}
