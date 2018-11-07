package main.item.control.light;

import main.ConnectedHouse;
import main.item.Item;

public class LightController implements Item {
    boolean light = false;

    @Override
    public void onEvent(String message, ConnectedHouse house) {
        switch (message) {
            case "movement_detected":
                light = !light;
                String msg = light ? "on" : "off";
                System.out.println("[" + this.getClass() + "] " + "switched " + msg);
        }

    }
}
