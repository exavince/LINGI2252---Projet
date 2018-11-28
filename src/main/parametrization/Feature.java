package main.parametrization;

import main.Room;
import main.item.Item;
import main.sensor.HouseObject;
import main.sensor.Sensor;

public class Feature implements Expression {
    private final Class<? extends HouseObject> objectClass;

    /**
     * @param objectClass Related object class
     */
    public Feature(Class<? extends HouseObject> objectClass) {
        this.objectClass = objectClass;
    }

    /**
     * @return Whether the feature is activated or not
     */
    @Override
    public boolean interpret(Room context) {
        for (Item item : context.getItems()) {
            if (objectClass.isInstance(item)) return true;
        }
        for (Sensor sensor : context.getSensors()) {
            if (objectClass.isInstance(sensor)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return objectClass.getSimpleName();
    }
}
