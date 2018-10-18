package main.feature.control.devices;

import main.event.SimulationEvent;
import main.event.SoonWakeUpTime;

public class CoffeeMachine implements main.feature.Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if (event instanceof SoonWakeUpTime) {
            System.out.println("Coffee machine : Starting to make coffee..");
        }
    }
}
