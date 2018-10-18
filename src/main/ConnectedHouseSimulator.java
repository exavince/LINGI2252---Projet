package main;

import main.event.*;
import main.feature.ClockController;
import main.feature.control.devices.CoffeeMachine;
import main.feature.control.heating.HeatingController;
import main.feature.control.heating.WeatherSensor;
import main.feature.control.light.LightController;
import main.feature.control.light.MovementsSensor;
import main.feature.sounds.ConnectedSpeakers;
import main.feature.sounds.VoiceAssistant;

public class ConnectedHouseSimulator {
    /**
     * All the events that symbolize something happening in reality. Usually capted by sensors.
     */
    public static final EventBus PHYSICAL_BUS = new EventBus();

    /**
     * Messages broadcasted by the central unit. Usually posted by sensors or devices.
     */
    public static final EventBus VIRTUAL_BUS = new EventBus();
    // TODO scenario with rain, weather detector and close the shutter ?

    public static void main(String[] args) {
        //TODO Launch scenarios based on command-line arguments ?
        firstScenario();
    }

    private static void firstScenario() {
        // Register features of the house
        VIRTUAL_BUS.register(
                new CoffeeMachine(),
                new HeatingController(),
                new ConnectedSpeakers(),
                new ClockController(),
                new VoiceAssistant(),
                new LightController()
        );
        PHYSICAL_BUS.register(
                new MovementsSensor(),
                new WeatherSensor()
        );

        println("# Scenario 1");
        println("## Some time before the user wakes up..");
        VIRTUAL_BUS.post(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        VIRTUAL_BUS.post(new WakeUpTime());
        println("## The user is waking up.. ");
        println("## Entering the kitchen..");
        PHYSICAL_BUS.post(new LeaveRoom(Room.BEDROOM));
        PHYSICAL_BUS.post(new EnterRoom(Room.KITCHEN));
        println("## He asks to play his morning playlist and goes to the garage.");
        VIRTUAL_BUS.post(VoiceCommand.PLAY_MORNING_PLAYLIST);
        PHYSICAL_BUS.post(new LeaveRoom(Room.KITCHEN));
        PHYSICAL_BUS.post(new EnterRoom(Room.GARAGE));
    }

    /**
     * Proxy method to shorten code in scenarios
     *
     * @param x The <code>String</code> to be printed.
     */
    private static void println(String x) {
        System.out.println(x);
    }
}
