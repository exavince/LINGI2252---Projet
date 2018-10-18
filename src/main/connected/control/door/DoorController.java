package main.connected.control.door;

import main.ConnectedHouse;
import main.connected.Feature;
import main.event.SimulationEvent;
import main.event.SmartphoneCommand;

public class DoorController implements Feature {

    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event == SmartphoneCommand.LOCK_HOUSE) {
            System.out.println("Locking the door..");
        }
    }
}
