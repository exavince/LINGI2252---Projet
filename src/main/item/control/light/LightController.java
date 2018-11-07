package main.item.control.light;

import main.item.Item;
import main.message.MovementAlert;

public class LightController extends Item {
    boolean light = false;

    private LightController() {
    }

    public static LightController createLightController() {
        return new LightController();
    }

    @Override
    public void onEvent(Object message) {
        if (message instanceof MovementAlert) {
            if (((MovementAlert) message).getRoom() == this.getRoom()) {
                light = !light;
                String msg = light ? "on" : "off";
                println("switched " + msg);
            }
        }
    }
}
