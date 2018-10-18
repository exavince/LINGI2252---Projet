package main.feature.sounds;

import main.event.SimulationEvent;
import main.event.SoundEvent;
import main.feature.Feature;

public class ConnectedSpeakers implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if (event instanceof SoundEvent) {
            System.out.println("Connected speakers play this music: " + ((SoundEvent) event).getContent());
        }
    }
}
