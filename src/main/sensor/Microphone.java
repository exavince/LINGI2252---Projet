package main.sensor;

import main.item.device.VoiceAssistant;

public class Microphone extends Sensor {

    private Microphone() {
    }

    public static Microphone createMicrophone() {
        return new Microphone();
    }

    @Override
    public void trigger(Object message) {
        if (message instanceof String) {
            getHouse().sendAllRooms(VoiceAssistant.class, message);
        }
    }
}
