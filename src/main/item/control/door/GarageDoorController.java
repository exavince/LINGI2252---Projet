package main.item.control.door;

import main.item.Item;

public class GarageDoorController extends Item {

    @Override
    public void onEvent(Object message) {
        if (message.equals("open")) {
            println("Opening the garage door..");
        } else if (message.equals("lock")) {
            println("Locking the garage door..");
        }
    }
}
