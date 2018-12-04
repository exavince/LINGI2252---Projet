package main.parametrization;

import main.ConnectedHouse;
import main.Room;
import main.item.control.heating.HeatingController;
import main.sensor.TemperatureSensor;

public class ConnectedHouseBuilder {

    private FeatureModel model = new FeatureModel(new Implication(new Feature(HeatingController.class), new Feature(TemperatureSensor.class)));
    private ConnectedHouse house = new ConnectedHouse();

    /**
     * TODO Check if the house is actually valid in its current configuration ?
     * Option A: A config is a sum of features and an interpreter is used to check it
     * Option B: When adding a feature add all dependencies (the apt get way)
     * Feature model object or not ?
     *
     * @return a valid {@code ConnectedHouse} instance.
     */
    public ConnectedHouse getHouse() {
        if (verify()) {
            return house;
        } else throw new RuntimeException("Configuration invalid");
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

    /*
        Context is the config. The config is the Room. FIXME Each Room is independent, in practice not true ?
        featureModel.interpret(config) = interpret rules on config. Means it's a bit A on all rules ?
        terminalFeature.interpret(config) = am I in this config or not ?
        andExpr(A, B).interpret(config) = is A and B in this config or not ?
     */

    /**
     * Adds rooms to the house.
     */
    public void register(Room minimumRoom, Room... roomsIn) {
        house.attach(minimumRoom, roomsIn);
    }
}
