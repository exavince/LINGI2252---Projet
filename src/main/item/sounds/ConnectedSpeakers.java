package main.item.sounds;

import main.ConnectedHouse;
import main.item.Item;
import main.event.SimulationEvent;
import main.event.SoundEvent;

public class ConnectedSpeakers implements Item {
    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof SoundEvent) {
            System.out.println("Connected speakers play this music: " + ((SoundEvent) event).getContent());
        }
    }
}
