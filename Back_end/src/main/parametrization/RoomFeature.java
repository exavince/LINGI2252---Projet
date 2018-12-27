package main.parametrization;

import framework.ConcreteFeature;
import main.ConnectedHouse;
import main.Room;
import main.RoomType;
import main.command.CommandParser;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomFeature implements ConcreteFeature<ConnectedHouse> {
    private static final List<RoomFeature> roomFeatures = Arrays.stream(RoomType.values()).map(RoomFeature::new).collect(Collectors.toList());
    private final RoomType roomType;

    private RoomFeature(RoomType roomType) {
        this.roomType = roomType;
    }

    public static RoomFeature get(RoomType roomType) {
        Optional<RoomFeature> feature = roomFeatures.stream().filter(e -> e.roomType == roomType).findFirst();
        if (feature.isPresent()) {
            return feature.get();
        } else {
            throw new NoSuchElementException("This room type does not exist");
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return roomType.toString();
    }

    @Override
    public void activate(ConnectedHouse target) {
        Room currentRoom = target.findRoom(roomType);
        if (currentRoom == Room.NONE) {
            target.attach(new Room(roomType));
        } else {
            CommandParser.LOGGER.warning("Room " + roomType + " was already in the house. Change ignored");
        }
    }

    @Override
    public void deactivate(ConnectedHouse target) {
        Room currentRoom = target.findRoom(roomType);
        if (currentRoom == Room.NONE) {
            CommandParser.LOGGER.warning("Room " + roomType + " was not in the house. Change ignored");
        } else {
            target.detach(currentRoom);
        }
    }
}
