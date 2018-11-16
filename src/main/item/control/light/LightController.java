package main.item.control.light;

import main.item.Item;
import main.message.MovementAlert;

public class LightController extends Item {
    private boolean light = false;
    private int intensity = 0;

    @Override
    public void onEvent(Object message) {
        if (message instanceof MovementAlert) {
            if (((MovementAlert) message).getRoom() == this.getRoom()) {
                light = !light;
                String msg = light ? "on" : "off";
                int lighting = light ? intensity : 0;
                println("Switched " + msg + " which set lighting to " + lighting);
                this.getRoom().setLighting(lighting);
            }
        }
    }

    public void setIntensity(int value) {
        intensity = value;
    }
}
