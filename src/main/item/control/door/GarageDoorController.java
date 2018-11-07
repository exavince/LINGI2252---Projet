package main.item.control.door;

import main.ConnectedHouse;
import main.item.Item;

public class GarageDoorController implements Item {

    @Override
    public void onEvent(String message, ConnectedHouse house) {
        if (message.equals("open")) {
            System.out.println("Opening the garage door..");
        } else if (message.equals("lock")) {
            System.out.println("Locking the garage door..");
        }
    }
}
