package main.feature.sounds;

import main.event.SimulationEvent;
import main.event.SoundEvent;
import main.event.VoiceCommand;
import main.feature.Feature;

import static main.ConnectedHouseSimulator.VIRTUAL_BUS;

public class VoiceAssistant implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if (event instanceof VoiceCommand) {
            switch ((VoiceCommand) event) {
                case PLAY_MORNING_PLAYLIST:
                    VIRTUAL_BUS.post(new SoundEvent("<Morning PlayList>"));
            }
        }
    }
}
