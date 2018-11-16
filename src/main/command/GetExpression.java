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

    @Override
    public String evaluate(ConnectedHouse house) {
        for (Room room : house.getRooms()) {
            if (room.getType() == roomType) {
                switch (attribute) {
                    case "TEMPERATURE":
                        return Integer.toString(room.getTemperature());
                    case "DESIRED_TEMPERATURE":
                        for (Item item : room.getItems()) {
                            if (item instanceof HeatingController) {
                                return Integer.toString(((HeatingController) item).getDesiredTemperature());
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
        System.out.println(this.evaluate(house));
    }
}
