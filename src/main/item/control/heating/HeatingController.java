package main.item.control.heating;

import main.ConnectedHouse;
import main.item.Item;
import main.sensor.TemperatureSensor;

public class HeatingController implements Item {
    @Override
    public void onEvent(String message, ConnectedHouse house) {
        switch (message) {
            case "start_heating":
                house.askSensorReport(this, TemperatureSensor.class);
                break;
            case "temperature_report":
                adjustHeat();
        }
    }

    private void adjustHeat() {
        System.out.println("Adjusting heating accordingly..");
    }

}
