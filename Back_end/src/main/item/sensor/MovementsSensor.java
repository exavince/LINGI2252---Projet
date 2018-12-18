package main.item.sensor;

import main.item.Item;
import main.message.MovementAlert;

public class MovementsSensor extends Item {

    @Override
    public void onEvent(Object message) {
        if (message != "movement_detected") return;
        getHouse().sendToItems(new MovementAlert(getRoom()));
    }
}