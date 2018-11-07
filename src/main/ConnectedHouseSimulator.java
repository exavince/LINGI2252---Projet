package main;

import main.item.control.ClockController;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.item.device.CoffeeMachine;
import main.item.device.VoiceAssistant;
import main.item.sounds.ConnectedSpeakers;
import main.routine.SoonWakeUpRoutine;
import main.sensor.Microphone;
import main.sensor.MovementsSensor;
import main.sensor.TemperatureSensor;

import static main.RoomType.*;

public class ConnectedHouseSimulator {
    // TODO scenario with rain, weather detector and close the shutter ?

    public static void main(String[] args) {
        //TODO Launch scenarios based on command-line arguments ?
        testScenario();
    }

    private static void testScenario() {
        ConnectedHouse house = new ConnectedHouse();

        house.register(
                new Room(BEDROOM).register(
                        new ConnectedSpeakers(),
                        new HeatingController(),
                        new LightController(),
                        new ClockController()
                ).register(new TemperatureSensor()),
                new Room(KITCHEN).register(
                        new CoffeeMachine(),
                        new HeatingController(),
                        new ConnectedSpeakers(),
                        new CoffeeMachine(),
                        new LightController()
                ).register(
                        new MovementsSensor(),
                        new Microphone()
                ),
                new Room(LIVING_ROOM).register(
                        new VoiceAssistant(),
                        new DoorController(),
                        new HeatingController(),
                        new LightController()
                ).register(
                        new MovementsSensor()
                ),
                new Room(GARAGE).register(
                        new GarageDoorController(),
                        new LightController()
                ).register(
                        new MovementsSensor()
                )
        );


        println("# Scenario 1");
        println("## Some time before the user wakes up..");
        new SoonWakeUpRoutine().call(house);
        //house.broadcast(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        // TODO Put in a configurable WakeUpRoutine ?
        house.send(BEDROOM, ClockController.class, "trigger_alarm");
        println("## The user is waking up.. ");
        println("## Entering the kitchen..");
        house.triggerSensor(KITCHEN, MovementsSensor.class, null);
        println("## He asks to play his morning playlist");
        house.triggerSensor(KITCHEN, Microphone.class, "play_morning_playlist");
        println("## He goes to the garage.");
        house.triggerSensor(KITCHEN, MovementsSensor.class, null);
        house.triggerSensor(GARAGE, MovementsSensor.class, null);
        println("## Using his smartphone from his, he opens the garage door.");
        house.send(GARAGE, GarageDoorController.class, "open");
        println("## His application allows him to completely lock the house from his car, as he drives away.");
        house.send(GARAGE, GarageDoorController.class, "lock");
        house.sendAllRooms(DoorController.class, "lock");
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
