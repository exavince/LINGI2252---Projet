package feature;

import event.SimulationEvent;
import event.SoundEvent;

public class ConnectedSpeakers implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof SoundEvent) {
            System.out.println("Connected speakers play this message: "+((SoundEvent) event).getContent());
        }
    }
}
