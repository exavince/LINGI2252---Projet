package main.item.control.heating;

import main.ConnectedHouse;
import main.item.Item;
import main.event.SimulationEvent;
import main.event.WeatherReport;

public class HeatingController implements Item {

    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof WeatherReport) {
            System.out.println("Adjusting heating accordingly..");
        }
    }
}
