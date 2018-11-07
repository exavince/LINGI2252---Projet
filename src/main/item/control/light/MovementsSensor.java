package main.item.control.light;

import main.sensor.Sensor;

public class MovementsSensor implements Sensor {
    @Override
    public String askReport() {
        return "movement_detected";
    }
}
