package main.message;

import main.WeatherStatus;

public class WeatherReport {
    private final WeatherStatus weatherStatus;

    public WeatherReport(WeatherStatus weatherStatus) {
        this.weatherStatus = weatherStatus;
    }

    public WeatherStatus getWeatherStatus() {
        return weatherStatus;
    }
}
