package main.command;

import main.ConnectedHouse;
import main.RoomType;
import main.item.control.heating.HeatingController;

public class SetDesiredTemperature implements Command {
    final RoomType room;
    final int desiredTemperature;

    public SetDesiredTemperature(RoomType room, int desiredTemperature) {
        this.room = room;
        this.desiredTemperature = desiredTemperature;
    }

    @Override
    public void execute(ConnectedHouse house) {
        house.send(room, HeatingController.class, desiredTemperature);
    }
}
