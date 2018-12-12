package main.command;

import main.ConnectedHouse;
import main.Room;
import main.RoomType;
import main.parametrization.Feature;

public class EditExpression implements Command {
    private final RoomType roomType;
    private final Feature feature;
    private final boolean mode;

    public EditExpression(RoomType roomType, Feature feature, boolean mode) {
        this.roomType = roomType;
        this.feature = feature;
        this.mode = mode;
    }

    @Override
    public void interpret(ConnectedHouse house) {
        if (roomType == RoomType.GLOBAL) {
            throw new UnsupportedOperationException("Cannot edit globally");
        }
        for (Room room : house.getRooms()) {
            if (room.getType() == roomType) {
                if (mode) {
                    feature.enable(room);
                } else {
                    feature.disable(room);
                }
                // TODO Check if valid afterwards
            }
        }
    }
}
