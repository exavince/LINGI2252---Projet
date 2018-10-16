package main.feature;

import main.event.EnterTheRoom;
import main.event.SimulationEvent;
import main.event.SoonWakeUpTime;

public class LightController implements Feature {

    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof EnterTheRoom) {
            System.out.println("Light controller: Turning on the lights in the room !");
        }
    }
}
