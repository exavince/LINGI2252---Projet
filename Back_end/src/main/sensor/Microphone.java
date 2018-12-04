package main.sensor;

public class Microphone extends Sensor {

    @Override
    public void trigger(Object message) {
        if (message instanceof String) {
            getHouse().sendToItems(message);
        }
    }
}
