package main.item.control.heating;

import main.item.Item;
import main.message.TemperatureReport;
import main.sensor.TemperatureSensor;

public class HeatingController extends Item {

    @Override
    public void onEvent(Object message) {
        if (message instanceof TemperatureReport) {
            adjustHeat();
        } else if (message == "start_heating") {
            getHouse().triggerSensor(getRoom().getType(), TemperatureSensor.class, null);
        }
    }

    private void adjustHeat() {
        println("Adjusting heating accordingly..");
    }
}
