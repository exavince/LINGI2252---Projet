package main.sensor;

import main.message.MovementAlert;

public class MovementsSensor extends Sensor {

    @Override
    public void trigger(Object message) {
        if (message != "movement_detected") return;
        getHouse().sendToItems(new MovementAlert(getRoom()));
    }
}