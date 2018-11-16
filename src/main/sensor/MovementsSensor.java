package main.sensor;

import main.message.MovementAlert;

public class MovementsSensor extends Sensor {

    @Override
    public void trigger(Object message) {
        // Message is ignored, receiving anything is enough to trigger
        getHouse().sendToItems(new MovementAlert(getRoom()));
    }
}