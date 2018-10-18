package main.connected.sounds;

import main.ConnectedHouse;
import main.connected.Feature;
import main.event.SimulationEvent;
import main.event.SoundEvent;

public class ConnectedSpeakers implements Feature {
    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof SoundEvent) {
            System.out.println("Connected speakers play this music: " + ((SoundEvent) event).getContent());
        }
    }
}
