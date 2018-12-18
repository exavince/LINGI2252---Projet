package main.command;

import framework.Feature;
import framework.FeatureModel;
import framework.InvalidModelConfigurationException;
import framework.editing.ActivateFeature;
import framework.editing.DeactivateFeature;
import framework.editing.FeatureAction;
import framework.editing.FeatureEditingStrategy;
import framework.editing.TryOnCopyStrategy;
import main.ConnectedHouse;
import main.ConnectedHouseFeatureModel;
import main.Room;
import main.RoomType;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class EditExpression implements Command {
    private final RoomType roomType;
    private final List<String> actionTokens;
    private final FeatureModel<Room> model = ConnectedHouseFeatureModel.getInstance();
    private final FeatureEditingStrategy strategy = new TryOnCopyStrategy(ConnectedHouseFeatureModel.getInstance());

    EditExpression(RoomType roomType, List<String> actionTokens) {
        this.roomType = roomType;
        this.actionTokens = actionTokens;
    }

    private FeatureAction<Room> tokenToAction(Room room, String token) {
        Feature<Room> feature = model.getFeature(token.substring(1));
        if (feature == null) {
            throw new UnsupportedOperationException("Unknown feature in " + token);
        }
        if (token.startsWith("+")) {
            return new ActivateFeature<>(feature, room);
        } else if (token.startsWith("-")) {
            return new DeactivateFeature<>(feature, room);
        } else {
            throw new UnsupportedOperationException("Unknown editing operation " + token);
        }
    }

    @Override
    public void interpret(ConnectedHouse house) {
        if (roomType == RoomType.GLOBAL) {
            // TODO Rooms should be features on ConnectedHouse so we could allow their adding/removal with action features
            actionTokens.forEach(token -> {
                final RoomType attributeRoomType = RoomType.valueOf(token.substring(1));
                final boolean add;
                if (token.startsWith("+")) {
                    add = true;
                } else if (token.startsWith("-")) {
                    add = false;
                } else {
                    throw new UnsupportedOperationException("Unknown editing operation " + token);
                }
                Room currentRoom = house.findRoom(attributeRoomType);
                if (add) {
                    if (currentRoom == Room.NONE) {
                        house.attach(new Room(attributeRoomType));
                    } else {
                        CommandParser.LOGGER.warning("Room " + attributeRoomType + " was already in the house. Change ignored");
                    }
                } else {
                    if (currentRoom == Room.NONE) {
                        CommandParser.LOGGER.warning("Room " + attributeRoomType + " was not in the house. Change ignored");
                    } else {
                        house.detach(currentRoom);
                    }
                }
            });
        } else if (roomType == RoomType.NOWHERE) {
            CommandParser.LOGGER.warning("Editing this room does not make sense");
            throw new UnsupportedOperationException("Cannot edit " + roomType);
        } else {
            Room room = house.findRoom(roomType);
            List<FeatureAction<Room>> actions = actionTokens.stream().map(token -> tokenToAction(room, token)).collect(Collectors.toList());
            try {
                strategy.apply(room.getModelState(), actions);
            } catch (InvalidModelConfigurationException e) {
                e.printStackTrace();
                CommandParser.LOGGER.warning("Invalid edit command");
                throw new UnsupportedOperationException("Invalid edit command");
            }
        }
        CommandParser.LOGGER.log(Level.INFO, "-- Applied feature configuration changes " + String.join(" ", actionTokens) + " of room " + roomType + " with success.");
    }
}
