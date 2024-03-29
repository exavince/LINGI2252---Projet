package main.item.control.heating;

import main.item.Item;
import main.message.TemperatureReport;

public class HeatingController extends Item {
    private int desiredTemperature = 0;

    @Override
    public void onEvent(Object message) {
        if (message instanceof TemperatureReport && ((TemperatureReport) message).getRoom() == this.getRoom()) {
            println("Received a temperature report : " + ((TemperatureReport) message).getTemperature() + "°C in my room");
            adjustHeat(((TemperatureReport) message).getTemperature());
        } else if (message == "start_heating") {
            println("Asking temperature report");
            getRoom().sendToItems("check_temperature");
        }
    }

    private void adjustHeat(int receivedTemperature) {
        if (receivedTemperature < desiredTemperature) {
            println("Adjusting heating accordingly from " + receivedTemperature + "°C to " + desiredTemperature + "°C");
            getRoom().setTemperature(desiredTemperature);
        }
    }

    public int getDesiredTemperature() {
        return desiredTemperature;
    }

    public void setDesiredTemperature(int desiredTemperature) {
        this.desiredTemperature = desiredTemperature;
    }
}
