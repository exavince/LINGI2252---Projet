package main.command;

import main.ConnectedHouse;
import main.Room;
import main.RoomType;
import main.item.Item;
import main.item.control.heating.HeatingController;

public class GetExpression implements ValueExpression, Command {
    private final RoomType roomType;
    private final String attribute;

    GetExpression(RoomType roomType, String attribute) {
        this.roomType = roomType;
        this.attribute = attribute;
    }

    /**
     * @param house context
     * @return value of the get expression. Type depends on attribute.
     */
    @Override
    public Object evaluate(ConnectedHouse house) {
        if (roomType == RoomType.GLOBAL) {
            switch (attribute) {
                case "MOOD":
                    return house.getMood();
                case "WEATHER":
                    return house.getWeatherStatus();
                default:
                    throw new UnsupportedOperationException("Unknown attribute: " + attribute);
            }
        }
        for (Room room : house.getRooms()) {
            if (room.getType() == roomType) {
                switch (attribute) {
                    case "TEMPERATURE":
                        return Integer.toString(room.getTemperature());
                    case "DESIRED_TEMPERATURE":
                        for (Item item : room.getItems()) {
                            if (item instanceof HeatingController) {
                                return ((HeatingController) item).getDesiredTemperature();
                            }
                        }
                        throw new RuntimeException("Could not find any heating controller in the room " + roomType);
                    default:
                        throw new UnsupportedOperationException("Unknown attribute: " + attribute);
                }
            }
        }
        throw new RuntimeException("Could not find room " + roomType);
    }

    @Override
    public void interpret(ConnectedHouse house) {
        System.out.println(this.evaluate(house).toString());
    }
}
