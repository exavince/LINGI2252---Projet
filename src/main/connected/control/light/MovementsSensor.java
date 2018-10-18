package main.connected.control.light;

import main.ConnectedHouse;
import main.Sensor;
import main.event.RoomEvent;
import main.event.SimulationEvent;

public class MovementsSensor implements Sensor {
    @Override
    public void onTrigger(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof RoomEvent) {
            house.broadcast(event);
        }
    }
}
