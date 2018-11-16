package main.command;

import main.ConnectedHouse;
import main.Room;
import main.RoomType;
import main.WeatherStatus;
import main.item.Item;
import main.item.control.heating.HeatingController;

public class SetExpression implements Command {
    private final RoomType roomType;
    private final String attribute;
    private final ValueExpression value;

    SetExpression(RoomType roomType, String attribute, ValueExpression value) {
        this.roomType = roomType;
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        if (roomType == RoomType.GLOBAL) {
            switch (attribute) {
                case "WEATHER":
                    house.setWeatherStatus((WeatherStatus) value.evaluate(house));
                    System.out.println("-- Set global attribute " + attribute + " with success.");
                    return;
                default:
                    throw new UnsupportedOperationException("Unknown global attribute " + attribute);
            }
        }
        for (Room room : house.getRooms()) {
            if (room.getType() == roomType) {
                switch (attribute) {
                    case "TEMPERATURE":
                        room.setTemperature((Integer) value.evaluate(house));
                        house.findRoom(roomType).sendToItems("start_heating");
                        break;
                    case "DESIRED_TEMPERATURE":
                        boolean found = false;

                        for (Item item : room.getItems()) {
                            if (item instanceof HeatingController) {
                                found = true;
                                ((HeatingController) item).setDesiredTemperature((Integer) value.evaluate(house));
                                item.onEvent("start_heating");
                            }
                        }
                        if (!found)
                            throw new RuntimeException("Could not find any heating controller in the room " + roomType);
                        break;
                    default:
                        throw new UnsupportedOperationException("Unknown attribute " + attribute + " for room " + roomType);
                }
            }
        }
        System.out.println("-- Set attribute " + attribute + " of room " + roomType + " with success.");
    }
}
