package main.parametrization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import framework.InvalidModelConfigurationException;
import main.ConnectedHouse;
import main.Room;
import main.RoomType;
import main.item.Item;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.item.sounds.ConnectedSpeakers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.exit;

public class ConnectedHouseJSONParser implements ConnectedHouseParser {
    private static final Logger LOGGER = Logger.getLogger(ConnectedHouseJSONParser.class.getName());
    private static ConnectedHouseParser INSTANCE = null;

    private ConnectedHouseJSONParser() {
    }

    public static ConnectedHouseParser getInstance() {
        if (INSTANCE == null) INSTANCE = new ConnectedHouseJSONParser();
        return INSTANCE;
    }

    @Override
    public ConnectedHouse parse(String inputConfigFile, String inputStateFile) throws FileNotFoundException {
        ConnectedHouseBuilder houseBuilder = new ConnectedHouseBuilder();
        JsonParser gson = new JsonParser();
        JsonObject json = (JsonObject) gson.parse(new FileReader(inputConfigFile));
        JsonObject stateJson = (JsonObject) gson.parse(new FileReader(inputStateFile));
        boolean valid = true;
        for (String roomName : json.keySet()) {
            JsonArray features = json.getAsJsonArray(roomName);
            List<String> featureNames = new ArrayList<>(features.size());
            for (int i = 0; i < features.size(); i++) {
                featureNames.add(features.get(i).getAsString());
            }
            try {
                houseBuilder.addRoom(RoomType.valueOf(roomName), featureNames);
            } catch (InvalidModelConfigurationException e) {
                // Game over, but we want to display ALL feature model config errors.
                LOGGER.log(Level.SEVERE, "=> " + roomName + " not valid.");
                valid = false;
            }
        }
        if (!valid) exit(1);
        ConnectedHouse house = houseBuilder.getHouse();
        house.getRooms().forEach(r -> setItemStates(r, stateJson));

        return house;
    }

    private void setItemStates(Room room, JsonObject json) {
        JsonObject temp = json.getAsJsonObject(room.getType().toString());
        if (temp != null) {
            if (temp.get("speaker") != null) {
                Item item = room.getItem(ConnectedSpeakers.class);
                if (item == null) {
                    LOGGER.log(Level.WARNING, "Speaker not in Room " + room);
                } else {
                    ConnectedSpeakers speakers = (ConnectedSpeakers) item;
                    speakers.setIntensity(temp.get("speaker").getAsInt());
                }
            }
            if (temp.get("light") != null) {
                Item item = room.getItem(LightController.class);
                if (item == null) {
                    LOGGER.log(Level.WARNING, "LightController not in Room " + room);
                } else {
                    LightController light = (LightController) item;
                    light.setIntensity(temp.get("light").getAsInt());
                }
            }
            if (temp.get("heating") != null) {
                Item item = room.getItem(HeatingController.class);
                if (item == null) {
                    LOGGER.log(Level.WARNING, "HeatingController not in Room " + room);
                } else {
                    HeatingController heating = (HeatingController) item;
                    heating.setDesiredTemperature(temp.get("heating").getAsInt());
                }
            }
        }
    }
}
