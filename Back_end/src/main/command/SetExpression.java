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

public class SetExpression<T> implements Command {
    private static final Map<String, BiConsumer<ConnectedHouse, ?>> globalAttributes = new HashMap<>();
    private static final Map<String, BiConsumer<Room, ?>> roomAttributes = new HashMap<>();

    static {
        globalAttributes.put("WEATHER", (connectedHouse, weatherStatus) -> connectedHouse.setWeatherStatus((WeatherStatus) weatherStatus));
        globalAttributes.put("MOOD", (connectedHouse, mood) -> connectedHouse.setMood((HomeMood) mood));
        //TODO Add position which takes a room argument. Be careful because ValueExpression cannot be room at the moment.

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
            if (!found) {
                CommandParser.LOGGER.warning("Could not find any heating controller in the room " + room);
                throw new RuntimeException("Could not find any heating controller in the room " + room);
            }
        });
    }

    private final RoomType roomType;
    private final String attribute;
    private final ValueExpression<T> value;

    SetExpression(RoomType roomType, String attribute, ValueExpression<T> value) {
        this.roomType = roomType;
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        if (roomType == RoomType.GLOBAL) {
            try {
                BiConsumer<ConnectedHouse, T> setter = (BiConsumer<ConnectedHouse, T>) globalAttributes.get(attribute);
                setter.accept(house, value.evaluate(house));

                CommandParser.LOGGER.log(Level.INFO, "-- Set global attribute " + attribute + " with success.");
            } catch (ClassCastException e) {
                CommandParser.LOGGER.log(Level.WARNING, e.getMessage());
                throw e;
            } catch (NullPointerException e) {
                CommandParser.LOGGER.log(Level.WARNING, "Unknown global attribute " + attribute);
                CommandParser.LOGGER.log(Level.INFO, "Available attributes: " + String.join(", ", globalAttributes.keySet()));
                throw new UnsupportedOperationException("Unknown global attribute " + attribute, e);
            }
            return;
        }
        for (Room room : house.getRooms()) {
            if (room.getType() == roomType) {
                try {
                    BiConsumer<Room, T> setter = (BiConsumer<Room, T>) roomAttributes.get(attribute);
                    setter.accept(room, value.evaluate(house));

                    CommandParser.LOGGER.log(Level.INFO, "-- Set room attribute " + attribute + " with success.");
                } catch (NullPointerException e) {
                    CommandParser.LOGGER.log(Level.WARNING, "Unknown attribute " + attribute + " for room " + roomType);
                    CommandParser.LOGGER.log(Level.INFO, "Available attributes: " + String.join(", ", roomAttributes.keySet()));
                    throw new UnsupportedOperationException("Unknown attribute " + attribute + " for room " + roomType, e);
                }
            }
        }
        CommandParser.LOGGER.log(Level.INFO, "-- Set attribute " + attribute + " of room " + roomType + " with success.");
    }
}
