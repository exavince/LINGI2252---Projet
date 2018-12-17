package main.item.control.door;

import main.WeatherStatus;
import main.item.Item;
import main.message.WeatherReport;

public class ShutterController extends Item {
    private boolean locked = false;

    private void lock() {
        if (!isLocked()) {
            locked = true;
            getHouse().notifyObservers();
        }
    }

    private void unlock() {
        if (isLocked()) {
            locked = false;
            getHouse().notifyObservers();
        }
    }

    public boolean isLocked() {
        return locked;
    }

    @Override
    public void onEvent(Object message) {
        if (message.equals("open")) {
            println("Opening the shutter..");
            unlock();
        } else if (message.equals("lock")) {
            println("Locking the shutter..");
            lock();
        } else if (message instanceof WeatherReport && ((WeatherReport) message).getWeatherStatus() == WeatherStatus.RAINY) {
            println("It's raining => Closing the shutter");
            lock();
        }
    }
}
