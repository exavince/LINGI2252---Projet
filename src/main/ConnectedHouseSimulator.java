package main;

import main.item.control.ClockController;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.item.control.heating.HeatingController;
import main.item.control.heating.WeatherSensor;
import main.item.control.light.LightController;
import main.item.control.light.MovementsSensor;
import main.item.device.CoffeeMachine;
import main.item.device.VoiceAssistant;
import main.item.sounds.ConnectedSpeakers;
import main.event.*;

public class ConnectedHouseSimulator {
    // TODO scenario with rain, weather detector and close the shutter ?

    public static void main(String[] args) {
        //TODO Launch scenarios based on command-line arguments ?
        testScenario();
    }

    private static void testScenario() {
        ConnectedHouse house = new ConnectedHouse();
        house.register(
                new CoffeeMachine(),
                new HeatingController(),
                new ConnectedSpeakers(),
                new ClockController(),
                new VoiceAssistant(),
                new LightController(),
                new GarageDoorController(),
                new DoorController()
        );
        house.register(
                new MovementsSensor(),
                new WeatherSensor()
        );

        println("# Scenario 1");
        println("## Some time before the user wakes up..");
        house.broadcast(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        house.broadcast(new WakeUpTime());
        println("## The user is waking up.. ");
        println("## Entering the kitchen..");
        house.triggerSensors(new LeaveRoom(Room.BEDROOM));
        house.triggerSensors(new EnterRoom(Room.KITCHEN));
        println("## He asks to play his morning playlist and goes to the garage.");
        house.broadcast(VoiceCommand.PLAY_MORNING_PLAYLIST);
        house.triggerSensors(new LeaveRoom(Room.KITCHEN));
        house.triggerSensors(new EnterRoom(Room.GARAGE));
        println("## Using his smartphone from his, he opens the garage door.");
        house.broadcast(SmartphoneCommand.OPEN_GARAGE_DOOR);
        println("## His application allows him to completely lock the house from his car, as he drives away.");
        house.broadcast(SmartphoneCommand.LOCK_HOUSE);
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
