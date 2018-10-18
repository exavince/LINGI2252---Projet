package main.feature.control.doors;

import main.event.SimulationEvent;
import main.event.SmartphoneCommand;
import main.feature.Feature;

public class DoorController implements Feature {

    @Override
    public void onEvent(SimulationEvent event) {
        if (event == SmartphoneCommand.LOCK_HOUSE) {
            System.out.println("Locking the door..");
        }
    }
}
