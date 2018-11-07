package main.sensor;

import main.message.MovementAlert;

public class MovementsSensor extends Sensor {

    private MovementsSensor() {
    }

    public static MovementsSensor createMovementsSensor() {
        return new MovementsSensor();
    }

    @Override
    public void trigger(Object message) {
        // Message is ignored, receiving anything is enough to trigger
        getHouse().send(new MovementAlert(getRoom()));
    }
}