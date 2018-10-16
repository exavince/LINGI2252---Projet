package feature;

import event.SimulationEvent;
import event.SoundEvent;
import event.WakeUpTime;

public class ClockController implements Feature {
    @Override
    public void onEvent(SimulationEvent event) {
        if(event instanceof WakeUpTime) {
            ConnectedHouseSimulator.EVENT_BUS.post(new SoundEvent("WAKE UP USER, WAKE UP !"));
        }

    }
}
