package main.parametrization;

import main.ConnectedHouse;
import main.Room;

public class ConnectedHouseBuilder {
    private FeatureModel model = new FeatureModel();
    private ConnectedHouse house = new ConnectedHouse();

    /**
     * Option A: A config is a sum of features and an interpreter is used to check it
     * Option B: When adding a feature add all dependencies (the apt get way)
     * Feature model object or not ?
     *
     * @return a valid {@code ConnectedHouse} instance.
     */
    public ConnectedHouse getHouse() {
        if (!verify()) {
            System.err.println("Warning: Configuration invalid");
        }
        return house;
    }

    /**
     * Interpret the current configuration with respect to the current feature model
     *
     * @return whether the current house configuration is valid or not
     */
    private boolean verify() {
        for (Room room : house.getRooms()) {
            if (!model.interpret(room)) return false;
        }
        return true;
    }


    /**
     * Adds rooms to the house.
     */
    public void register(Room minimumRoom, Room... roomsIn) {
        house.attach(minimumRoom, roomsIn);
    }
}
