package main.command;

import main.ConnectedHouse;
import main.Room;
import main.RoomType;
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
        for (Room room : house.getRooms()) {
            if (room.getType() == roomType) {
                switch (attribute) {
                    case "TEMPERATURE":
                        room.setTemperature(Integer.parseInt(value.evaluate(house)));
                        house.send(roomType, HeatingController.class, "start_heating");
                        break;
                    case "DESIRED_TEMPERATURE":
                        boolean found = false;

                        for (Item item : room.getItems()) {
                            if (item instanceof HeatingController) {
                                found = true;
                                ((HeatingController) item).setDesiredTemperature(Integer.parseInt(value.evaluate(house)));
                                item.onEvent("start_heating");
                            }
                        }
                        if (!found)
                            throw new RuntimeException("Could not find any heating controller in the room " + roomType);
                        break;
                    default:
                        throw new UnsupportedOperationException("Unknown attribute: " + attribute);
                }
            }
        }
        System.out.println("-- Set attribute " + attribute + " of room " + roomType + " with success.");
    }
}
