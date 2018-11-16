package main;

import main.command.Command;
import main.command.CommandParser;
import main.item.control.ClockController;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.routine.SoonWakeUpRoutine;
import main.sensor.Microphone;
import main.sensor.MovementsSensor;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static main.RoomType.*;

public class ConnectedHouseSimulator {
    // TODO scenario with rain, weather detector and close the shutter ?


    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("# Welcome to ConnectedHouseSimulator");

        final ConnectedHouseParser parser = new ConnectedHouseJSONParser();
        final ConnectedHouse house = parser.parse("config.json", "state.json");

        final Scanner userInput = new Scanner(System.in);
        // TODO Choose a starting scenario
        house.moveTo(BEDROOM);
        println("# Scenario 1");
        println("## Some time before the user wakes up..");
        new SoonWakeUpRoutine().call(house);
        //house.broadcast(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        // TODO Put in a configurable WakeUpRoutine ?
        house.send(BEDROOM, ClockController.class, "trigger_alarm");
        System.out.println("-- You wake up in your bedroom. What do you do ?");
        CommandParser commandParser = new CommandParser(userInput);

        while (userInput.hasNext()) {
            try {
                Command command = commandParser.parse();
                if (command == Command.EXIT) {
                    System.out.println("Exiting simulator..");
                    break;
                } else {
                    command.interpret(house);
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                System.err.println("Please enter another command or \"EXIT\" to exit.");
            }
        }
        userInput.close();
    }

    private static void testScenario(ConnectedHouse house) {
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
