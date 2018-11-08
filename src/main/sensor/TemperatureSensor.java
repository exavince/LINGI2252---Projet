package main.sensor;

import main.message.TemperatureReport;

public class TemperatureSensor extends Sensor {

    @Override
    public void trigger(Object message) {
        getHouse().send(new TemperatureReport(this.getRoom()));
    }
}
