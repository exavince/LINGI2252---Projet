package main.item.control.door;

import main.item.Item;

public class DoorController extends Item {
    private boolean locked = true;

    private void lock() {
        if (!isLocked()) {
            locked = true;
            getHouse().notifyObservers();
        }
    }

    private void unlock() {
        if (isLocked()) {
            locked = false;
            getHouse().notifyObservers();
        }
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void onEvent(Object message) {
        if (message.equals("open")) {
            println("Opening the door..");
            unlock();
        } else if (message.equals("lock")) {
            println("Locking the door..");
            lock();
        }
    }
}
