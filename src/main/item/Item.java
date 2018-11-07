package main.item;

import main.sensor.HouseObject;

public abstract class Item extends HouseObject {
    abstract public void onEvent(Object message);
}
