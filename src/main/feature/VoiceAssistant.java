package main.feature;

import main.event.SimulationEvent;
import main.event.SoundEvent;
import main.event.VoiceCommand;

import static main.ConnectedHouseSimulator.EVENT_BUS;

public class VoiceAssistant implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof VoiceCommand) {
            switch((VoiceCommand) event){
                case PLAY_MORNING_PLAYLIST:
                    EVENT_BUS.post(new SoundEvent("<Morning PlayList>"));
            }
        }

    }
}
