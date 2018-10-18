package main.connected.control.door;

import main.ConnectedHouse;
import main.connected.Feature;
import main.event.SimulationEvent;
import main.event.SmartphoneCommand;

public class GarageDoorController implements Feature {

    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event == SmartphoneCommand.OPEN_GARAGE_DOOR) {
            System.out.println("Opening the garage door..");
        } else if (event == SmartphoneCommand.LOCK_HOUSE) {
            System.out.println("Locking the garage door..");
        }
    }
}
