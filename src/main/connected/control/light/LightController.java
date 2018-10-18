package main.connected.control.light;

import main.ConnectedHouse;
import main.connected.Item;
import main.event.EnterRoom;
import main.event.LeaveRoom;
import main.event.SimulationEvent;

public class LightController implements Item {

    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof EnterRoom) {
            System.out.println("Light controller: Turning on the lights in the room \"" + ((EnterRoom) event).getRoom() + "\"!");
        } else if (event instanceof LeaveRoom) {
            System.out.println("Light controller: Turning off the lights in the room \"" + ((LeaveRoom) event).getRoom() + "\"!");
        }
    }
}
