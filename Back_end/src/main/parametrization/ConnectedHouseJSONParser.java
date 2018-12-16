package main.parametrization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import framework.Feature;
import framework.FeatureModel;
import framework.InvalidModelConfigurationException;
import framework.editing.ActivateFeature;
import framework.editing.FeatureAction;
import framework.editing.FeatureEditingStrategy;
import framework.editing.TryOnCopyStrategy;
import main.ConnectedHouse;
import main.ConnectedHouseFeatureModel;
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

import static java.lang.System.exit;

public class ConnectedHouseJSONParser implements ConnectedHouseParser {
    private final FeatureEditingStrategy strategy = new TryOnCopyStrategy(ConnectedHouseFeatureModel.getInstance());
    private final FeatureModel<Room> model = ConnectedHouseFeatureModel.getInstance();
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
        boolean valid = true;
        for (String roomName : json.keySet()) {
            Room room = roomFromString(roomName);
            JsonArray features = json.getAsJsonArray(roomName);
            List<FeatureAction<Room>> actions = new ArrayList<>();
            actions.add(new ActivateFeature<>(model.getFeature("Central"), room));
            for (int i = 0; i < features.size(); i++) {
                String featureName = features.get(i).getAsString();
                Feature<Room> feature = model.getFeature(featureName);
                if (feature == null)
                    throw new RuntimeException("Feature " + featureName + " not present in feature model.");
                actions.add(new ActivateFeature<>(feature, room));
            }
            try {
                strategy.apply(room.getModelState(), actions);
            } catch (InvalidModelConfigurationException e) {
                System.err.println("=> " + room + " not valid.");
                valid = false;
            }
            if (valid) {
                house.register(room);
                setItemStates(room, stateJson);
            }
        }
        if (!valid) exit(1);
    }

    private Room roomFromString(String roomName) {
        return new Room(RoomType.valueOf(roomName));
    }


    private void setItemStates(Room room, JsonObject json) {
        JsonObject temp = json.getAsJsonObject(room.getType().toString());
        if (temp != null) {
            if (temp.get("speaker") != null) {
                Item item = room.getItem(ConnectedSpeakers.class);
                if (item == null) {
                    System.err.println("Speaker not in Room " + room);
                }
                ConnectedSpeakers speakers = (ConnectedSpeakers) item;
                speakers.setIntensity(temp.get("speaker").getAsInt());
            }
            if (temp.get("light") != null) {
                Item item = room.getItem(LightController.class);
                if (item == null) {
                    System.err.println("LightController not in Room " + room);
                }
                LightController light = (LightController) item;
                light.setIntensity(temp.get("light").getAsInt());
            }
            if (temp.get("heating") != null) {
                Item item = room.getItem(HeatingController.class);
                if (item == null) {
                    System.err.println("HeatingController not in Room " + room);
                }
                HeatingController heating = (HeatingController) item;
                heating.setDesiredTemperature(temp.get("heating").getAsInt());
            }
        }
    }
}
