package main;

import main.command.Command;
import main.command.CommandParser;
import main.routine.SoonWakeUpRoutine;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static main.RoomType.BEDROOM;
import static main.RoomType.GARAGE;

public class ConnectedHouseSimulator {
    // TODO scenario with rain, weather detector and close the shutter ?


    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("# Welcome to ConnectedHouseSimulator");

        final ConnectedHouseParser parser = new ConnectedHouseJSONParser();
        final ConnectedHouse house = parser.parse("config.json", "state.json");
        System.out.println("Choose a scenario (1 or 2)");
        final Scanner userInput = new Scanner(System.in);
        int scenarioNumber = getScenarioNumber(userInput);
        if (scenarioNumber == 1) firstScenario(userInput, house);
        else secondScenario(userInput, house);
        userInput.close();
    }

    private static int getScenarioNumber(Scanner userInput) {
        int scenarioNumber = 1;
        boolean valid = false;
        while (!valid) {
            scenarioNumber = userInput.nextInt();
            if (scenarioNumber >= 1 && scenarioNumber <= 2) {
                valid = true;
            } else {
                System.err.println("Invalid scenario number");
            }
        }
        return scenarioNumber;
    }

    private static Command giveTerminalControl(Scanner userInput, ConnectedHouse house) {
        CommandParser commandParser = new CommandParser(userInput);
        Command lastCommand = Command.DONE;
        boolean shouldStopAskingUser = false;

        while (!shouldStopAskingUser) {
            try {
                Command command = commandParser.parse();
                lastCommand = command;
                if (command == Command.EXIT) {
                    System.out.println("Exiting simulator..");
                    shouldStopAskingUser = true;
                } else if (command == Command.DONE) {
                    System.out.println("Continuing the scenario..");
                    shouldStopAskingUser = true;
                } else {
                    command.interpret(house);
                }
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                System.err.println("Please enter another command or \"EXIT\" to exit.");
            }
        }
        return lastCommand;
    }

    private static void firstScenario(Scanner userInput, ConnectedHouse house) {
        println("# Scenario 1 : Waking up in the bed");
        house.moveTo(BEDROOM);
        println("## Some time before the user wakes up..");
        new SoonWakeUpRoutine().call(house);
        //house.broadcast(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        // TODO Put in a configurable WakeUpRoutine ?
        house.findRoom(BEDROOM).sendToItems("trigger_alarm");
        System.out.println("-- You wake up in your bedroom. What do you do ?");
        final Command lastCommand = giveTerminalControl(userInput, house);
        if (lastCommand == Command.EXIT) {
            return;
        }

        if (house.getPosition() != GARAGE) {
            println("## The user moves to the garage to go to work");
            house.moveTo(GARAGE);
        }
        println("## User enters his car");
        println("## Using his smartphone from his car, he opens the garage door.");
        house.findRoom(GARAGE).sendToItems("open");
        println("## His application allows him to completely lock the house from his car, as he drives away.");
        house.findRoom(GARAGE).sendToItems("lock");
        house.sendToItems("lock");
    }

    private static void secondScenario(Scanner userInput, ConnectedHouse house) {
        println("# Scenario 2 : Arriving home from work");
        house.moveTo(GARAGE);
        System.out.println("-- You are in your garage after having returned from work. What do you do ?");
        final Command lastCommand = giveTerminalControl(userInput, house);
        if (lastCommand == Command.EXIT) {
            return;
        }

        if (house.getPosition() != BEDROOM) {
            println("## The user needs to sleep and goes to the bedroom to do so.");
            house.moveTo(BEDROOM);
        }
        println("## He uses his smartphone application to completely lock the house from his bed.");
        house.sendToItems("lock");
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
