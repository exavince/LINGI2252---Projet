package main.feature.control.doors;

import main.event.SimulationEvent;
import main.event.SmartphoneCommand;
import main.feature.Feature;

public class GarageDoorController implements Feature {

    @Override
    public void onEvent(SimulationEvent event) {
        if (event == SmartphoneCommand.OPEN_GARAGE_DOOR) {
            System.out.println("Opening the garage door..");
        } else if (event == SmartphoneCommand.LOCK_HOUSE) {
            System.out.println("Locking the garage door..");
        }
    }
}
