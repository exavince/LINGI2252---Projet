package main.routine;

import main.ConnectedHouse;
import main.item.control.heating.HeatingController;
import main.item.device.CoffeeMachine;

import static main.RoomType.BEDROOM;
import static main.RoomType.KITCHEN;

public class SoonWakeUpRoutine {
    public void call(ConnectedHouse house) {
        house.send(KITCHEN, CoffeeMachine.class, "make_coffee");
        // TODO ask for heating the
        house.send(BEDROOM, HeatingController.class, "start_heating");
    }
}
