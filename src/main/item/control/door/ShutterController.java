package main.item.control.door;

import main.WeatherStatus;
import main.item.Item;
import main.message.WeatherReport;

public class ShutterController extends Item {

    @Override
    public void onEvent(Object message) {
        if (message.equals("lock")) {
            println("Locking the shutter..");
        } else if(message instanceof WeatherReport && ((WeatherReport) message).getWeatherStatus() == WeatherStatus.RAINY) {
            println("It's raining => Closing the shutter");
        }
    }
}
