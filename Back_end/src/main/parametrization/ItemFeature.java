package main.parametrization;

import framework.Feature;
import main.Room;
import main.item.Item;

public class ItemFeature implements Feature<Room> {
    private final Class<? extends Item> objectClass;

    /**
     * @param objectClass Related object class
     */
    public ItemFeature(Class<? extends Item> objectClass) {
        this.objectClass = objectClass;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return objectClass.getSimpleName();
    }

    @Override
    public void activate(Room target) {
        try {
            Item item = objectClass.newInstance();
            target.attach(item);
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println("Could not instantiate item " + objectClass.getSimpleName());
            e.printStackTrace();
        }
    }

    @Override
    public void deactivate(Room target) {
        target.getItems().removeIf(objectClass::isInstance);
    }
}
