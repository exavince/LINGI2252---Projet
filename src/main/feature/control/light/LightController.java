package main.feature.control.light;

import main.event.EnterRoom;
import main.event.LeaveRoom;
import main.event.SimulationEvent;
import main.feature.Feature;

public class LightController implements Feature {

    @Override
    public void onEvent(SimulationEvent event) {
        if (event instanceof EnterRoom) {
            System.out.println("Light controller: Turning on the lights in the room \"" + ((EnterRoom) event).getRoom() + "\"!");
        } else if (event instanceof LeaveRoom) {
            System.out.println("Light controller: Turning off the lights in the room \"" + ((LeaveRoom) event).getRoom() + "\"!");
        }
    }
}
