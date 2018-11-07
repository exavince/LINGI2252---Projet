package main.sensor;

import main.message.TemperatureReport;

public class TemperatureSensor extends Sensor {

    private TemperatureSensor() {
    }

    public static TemperatureSensor createTemperatureSensor() {
        return new TemperatureSensor();
    }

    @Override
    public void trigger(Object message) {
        getHouse().send(new TemperatureReport(this.getRoom()));
    }
}
