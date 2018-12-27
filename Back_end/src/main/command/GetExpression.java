package main.command;

import main.ConnectedHouse;
import main.Room;
import main.RoomType;
import main.item.Item;
import main.item.control.heating.HeatingController;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class GetExpression<T> implements ValueExpression<T>, Command {
    private static final Map<String, Function<ConnectedHouse, ?>> globalAttributes = new HashMap<>();
    private static final Map<String, Function<Room, ?>> roomAttributes = new HashMap<>();

    static {
        globalAttributes.put("MOOD", ConnectedHouse::getMood);
        globalAttributes.put("WEATHER", ConnectedHouse::getWeatherStatus);
        globalAttributes.put("POSITION", ConnectedHouse::getPosition);
        globalAttributes.put("ROOMS", (house) -> house.getRooms().stream().map(Object::toString).collect(Collectors.joining(" ")));

        roomAttributes.put("TEMPERATURE", Room::getTemperature);
        roomAttributes.put("DESIRED_TEMPERATURE", (r) -> {
            for (Item item : r.getItems()) {
                if (item instanceof HeatingController) {
                    return ((HeatingController) item).getDesiredTemperature();
                }
            }
            CommandParser.LOGGER.warning("Could not find any heating controller in the room " + r);
            throw new NoSuchElementException("Could not find any heating controller in the room " + r);
        });
        roomAttributes.put("FEATURES", Room::getModelState);
    }

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
    public T evaluate(ConnectedHouse house) {
        if (roomType == RoomType.GLOBAL) {
            try {
                return (T) globalAttributes.get(attribute).apply(house);
            } catch (NullPointerException e) {
                CommandParser.LOGGER.log(Level.WARNING, "Unknown global attribute " + attribute);
                CommandParser.LOGGER.log(Level.INFO, "Available attributes: " + String.join(", ", globalAttributes.keySet()));
                throw new UnsupportedOperationException("Unknown global attribute " + attribute);
            }
        } else {
            for (Room room : house.getRooms()) {
                if (room.getType() == roomType) {
                    try {
                        return (T) roomAttributes.get(attribute).apply(room);
                    } catch (NullPointerException e) {
                        CommandParser.LOGGER.log(Level.WARNING, "Unknown attribute " + attribute + " for room " + roomType);
                        CommandParser.LOGGER.log(Level.INFO, "Available attributes: " + String.join(", ", roomAttributes.keySet()));
                        throw new UnsupportedOperationException("Unknown attribute " + attribute + " for room " + roomType);
                    }
                }
            }
            throw new RuntimeException("Could not find room " + roomType);
        }
    }

    @Override
    public void interpret(ConnectedHouse house) {
        CommandParser.LOGGER.log(Level.INFO, this.evaluate(house).toString());
    }
}
