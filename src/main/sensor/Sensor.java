package main.sensor;

public abstract class Sensor extends HouseObject {
    abstract public void trigger(Object message);
}
