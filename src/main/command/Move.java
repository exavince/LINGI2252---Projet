package main.command;

import main.ConnectedHouse;
import main.RoomType;

public class Move implements Command {
    private final RoomType room;

    public Move(RoomType room) {
        this.room = room;
    }

    @Override
    public void execute(ConnectedHouse house) {
        house.moveTo(room);
    }

    @Override
    public String toString() {
        return "## Moving to room " + room;
    }
}
