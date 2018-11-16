package main.sensor;

import main.message.TemperatureReport;

public class TemperatureSensor extends Sensor {

    @Override
    public void trigger(Object message) {
        getHouse().sendToItems(new TemperatureReport(this.getRoom(), this.getRoom().getTemperature()));
    }
}
