package main.parametrization;

import main.Room;
import main.item.Item;

public class ItemFeature implements Feature {
    private final Class<? extends Item> objectClass;

    /**
     * @param objectClass Related object class
     */
    public ItemFeature(Class<? extends Item> objectClass) {
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
        return false;
    }

    @Override
    public String toString() {
        return objectClass.getSimpleName();
    }

    @Override
    public void enable(Room context) {

    }

    @Override
    public void disable(Room context) {

    }
}
