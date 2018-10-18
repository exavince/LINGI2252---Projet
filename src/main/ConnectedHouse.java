package main;

import main.connected.Item;
import main.event.SimulationEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ConnectedHouse {
    private ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Adds items to the Event bus. At least one item is required to call the method.
     */
    void register(Item minimumItem, Item... itemsIn) {
        items.add(minimumItem);
        items.addAll(Arrays.asList(itemsIn));
    }

    /**
     * Broadcast an event to all items.
     *
     * @param event The main.event to be broadcasted
     */
    public void broadcast(SimulationEvent event) {
        items.forEach(item -> item.onEvent(event, this));
    }

    /**
     * Adds sensors. At least one sensor is required to call the method.
     */
    void register(Sensor minimumSensor, Sensor... sensorsIn) {
        sensors.add(minimumSensor);
        sensors.addAll(Arrays.asList(sensorsIn));
    }

    void triggerSensors(SimulationEvent event) {
        sensors.forEach(sensor -> sensor.onTrigger(event, this));
    }
}
