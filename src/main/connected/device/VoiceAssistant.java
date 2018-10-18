package main.connected.device;

import main.ConnectedHouse;
import main.connected.Item;
import main.event.SimulationEvent;
import main.event.SoundEvent;
import main.event.VoiceCommand;

public class VoiceAssistant implements Item {
    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof VoiceCommand) {
            switch ((VoiceCommand) event) {
                case PLAY_MORNING_PLAYLIST:
                    house.broadcast(new SoundEvent("<Morning PlayList>"));
            }
        }
    }
}
