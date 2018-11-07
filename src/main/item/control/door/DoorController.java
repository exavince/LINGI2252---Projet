package main.item.control.door;

import main.ConnectedHouse;
import main.item.Item;

public class DoorController implements Item {

    @Override
    public void onEvent(String message, ConnectedHouse house) {
        if (message.equals("lock")) {
            System.out.println("Locking the door..");
        }
    }
}
