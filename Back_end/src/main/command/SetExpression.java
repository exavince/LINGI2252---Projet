package main.command;

import main.ConnectedHouse;
import main.Room;
import main.RoomType;
import main.WeatherStatus;
import main.item.Item;
import main.item.control.heating.HeatingController;
import main.routine.HomeMood;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;

public class SetExpression implements Command {
    private static Map<String, BiConsumer<ConnectedHouse, Object>> globalAttributes = new HashMap<>();
    private static Map<String, BiConsumer<Room, Object>> roomAttributes = new HashMap<>();

    static {
        globalAttributes.put("WEATHER", (connectedHouse, weatherStatus) -> connectedHouse.setWeatherStatus((WeatherStatus) weatherStatus));
        globalAttributes.put("MOOD", (connectedHouse, mood) -> connectedHouse.setMood((HomeMood) mood));

        roomAttributes.put("TEMPERATURE", (room, temperature) -> {
            room.setTemperature((Integer) temperature);
            room.sendToItems("start_heating");
        });
        roomAttributes.put("DESIRED_TEMPERATURE", (room, desiredTemperature) -> {
            boolean found = false;

            for (Item item : room.getItems()) {
                if (item instanceof HeatingController) {
                    found = true;
                    ((HeatingController) item).setDesiredTemperature((Integer) desiredTemperature);
                    item.onEvent("start_heating");
                }
            }
            if (!found)
                throw new RuntimeException("Could not find any heating controller in the room " + room);
        });
    }

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
            try {
                globalAttributes.get(attribute).accept(house, value.evaluate(house));
                CommandParser.LOGGER.log(Level.INFO, "-- Set global attribute " + attribute + " with success.");
            } catch (NullPointerException e) {
                CommandParser.LOGGER.log(Level.WARNING, "Unknown global attribute " + attribute);
                CommandParser.LOGGER.log(Level.INFO, "Available attributes: " + String.join(", ", globalAttributes.keySet()));
                throw new UnsupportedOperationException("Unknown global attribute " + attribute);
            }
            return;
        }
        for (Room room : house.getRooms()) {
            if (room.getType() == roomType) {
                try {
                    roomAttributes.get(attribute).accept(room, value.evaluate(house));
                    CommandParser.LOGGER.log(Level.INFO, "-- Set room attribute " + attribute + " with success.");
                } catch (NullPointerException e) {
                    CommandParser.LOGGER.log(Level.WARNING, "Unknown attribute " + attribute + " for room " + roomType);
                    CommandParser.LOGGER.log(Level.INFO, "Available attributes: " + String.join(", ", roomAttributes.keySet()));
                    throw new UnsupportedOperationException("Unknown attribute " + attribute + " for room " + roomType);
                }
            }
        }
        CommandParser.LOGGER.log(Level.INFO, "-- Set attribute " + attribute + " of room " + roomType + " with success.");
    }
}
