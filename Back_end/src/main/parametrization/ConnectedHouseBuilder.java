package main.parametrization;

import main.ConnectedHouse;
import main.Room;

/**
 * TODO Check if really necessary or state as the one who knows the model
 */
public class ConnectedHouseBuilder {
    private final ConnectedHouse house = new ConnectedHouse();

    /**
     * @return a valid {@code ConnectedHouse} instance.
     */
    public ConnectedHouse getHouse() {
        return house;
    }


    /**
     * Adds rooms to the house.
     */
    public void register(Room minimumRoom, Room... roomsIn) {
        house.attach(minimumRoom, roomsIn);
    }
}
