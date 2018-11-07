package main.sensor;

import main.item.device.VoiceAssistant;

public class Microphone extends Sensor {

    @Override
    public void trigger(Object message) {
        if (message instanceof String) {
            getHouse().sendAllRooms(VoiceAssistant.class, message);
        }
    }
}
