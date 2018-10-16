package main.feature;

import main.event.SimulationEvent;
import main.event.SoundEvent;

public class ConnectedSpeakers implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof SoundEvent) {
            System.out.println("Connected speakers play this music: "+((SoundEvent) event).getContent());
        }
    }
}
