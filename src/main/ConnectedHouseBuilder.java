package main;

class ConnectedHouseBuilder {

    private ConnectedHouse house = new ConnectedHouse();

    /**
     * TODO Check if the house is actually valid in its current configuration
     *
     * @return a valid {@code ConnectedHouse} instance.
     */
    ConnectedHouse getHouse() {
        return house;
    }

    /**
     * Adds rooms to the house.
     */
    void register(Room minimumRoom, Room... roomsIn) {
        house.register(minimumRoom, roomsIn);
    }
}
