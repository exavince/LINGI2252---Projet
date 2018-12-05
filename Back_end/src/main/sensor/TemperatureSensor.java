package main.sensor;

import main.item.Item;
import main.message.TemperatureReport;

public class TemperatureSensor extends Item {

    @Override
    public void onEvent(Object message) {
        if (message != "check_temperature") return;
        println("Was asked a temperature report");
        getHouse().sendToItems(new TemperatureReport(this.getRoom(), this.getRoom().getTemperature()));
    }
}
