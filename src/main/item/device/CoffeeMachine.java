package main.item.device;

import main.ConnectedHouse;
import main.item.Item;
import main.event.SimulationEvent;
import main.event.SoonWakeUpTime;

public class CoffeeMachine implements Item {
    @Override
    public void onEvent(SimulationEvent event, ConnectedHouse house) {
        if (event instanceof SoonWakeUpTime) {
            System.out.println("Coffee machine : Starting to make coffee..");
        }
    }
}
