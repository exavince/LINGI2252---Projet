package main.item.control.door;

import main.item.Item;

public class DoorController extends Item {

    private DoorController() {
    }

    public static DoorController createDoorController() {
        return new DoorController();
    }

    @Override
    public void onEvent(Object message) {
        if (message.equals("lock")) {
            println("Locking the door..");
        }
    }
}
