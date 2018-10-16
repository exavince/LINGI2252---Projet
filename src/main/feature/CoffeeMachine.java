package feature;

import event.SimulationEvent;
import event.SoonWakeUpTime;

public class CoffeeMachine implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof SoonWakeUpTime) {
            System.out.println("Coffee machine : Starting to make coffee..");
        }
    }
}
