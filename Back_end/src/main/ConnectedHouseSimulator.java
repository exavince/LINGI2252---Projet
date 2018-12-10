package main;

import main.command.Command;
import main.command.CommandParser;
import main.parametrization.ConnectedHouseJSONParser;
import main.parametrization.ConnectedHouseParser;
import main.routine.SoonWakeUpRoutine;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static main.RoomType.BEDROOM;
import static main.RoomType.GARAGE;

public class ConnectedHouseSimulator implements Runnable{
    // TODO scenario with rain, weather detector and close the shutter
    public static ConnectedHouse house = null;
    public static BlockingQueue<String> dataIN = new LinkedBlockingQueue<>();
    public static BlockingQueue<String> dataOUT = new LinkedBlockingQueue<>();

    public void run() {
        System.out.println("# Welcome to ConnectedHouseSimulator");
        final ConnectedHouseParser parser = new ConnectedHouseJSONParser();
        try {
            house = parser.parse("../Back_end/config.json", "../Back_end/state.json", dataOUT);
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Choose a scenario (1 or 2)");
        int scenarioNumber = getScenarioNumber(dataIN);
        if (scenarioNumber == 1) firstScenario(house);
        else secondScenario(house);
    }

    private static int getScenarioNumber(Queue<String> dataIN) {
        int scenarioNumber = 1;
        boolean valid = false;
        while (!valid) {
            if (!dataIN.isEmpty()) {
                String data = dataIN.poll();
                scenarioNumber = Integer.parseInt(data);
                if (scenarioNumber >= 1 && scenarioNumber <= 2) {
                    valid = true;
                } else {
                    println("Invalid scenario number");
                }
            }
        }
        return scenarioNumber;
    }

    private static Command giveTerminalControl(Queue<String> dataIN, Queue<String> dataOUT, ConnectedHouse house) {
        CommandParser commandParser = new CommandParser(dataIN, dataOUT);
        Command lastCommand = Command.DONE;
        boolean shouldStopAskingUser = false;

        while (!shouldStopAskingUser) {
            if (!dataIN.isEmpty()) {
                try {
                    Command command = commandParser.parse();
                    lastCommand = command;
                    if (command == Command.EXIT) {
                        dataOUT.add("EXIT");
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
                    println("Please enter another command or \"EXIT\" to exit.");
                }
            }
        }
        return lastCommand;
    }

    private static void firstScenario(ConnectedHouse house) {
        println("# Scenario 1 : Waking up in the bed");
        house.moveTo(BEDROOM);
        println("## Some time before the user wakes up..");
        new SoonWakeUpRoutine().call(house);
        //house.broadcast(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        // TODO Put in a configurable WakeUpRoutine ?
        house.findRoom(BEDROOM).sendToItems("trigger_alarm");
        println("-- You wake up in your bedroom. What do you do ?");
        final Command lastCommand = giveTerminalControl(dataIN, dataOUT, house);
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

    private static void secondScenario(ConnectedHouse house) {
        println("# Scenario 2 : Arriving home from work");
        house.moveTo(GARAGE);
        println("-- You are in your garage after having returned from work. What do you do ?");
        final Command lastCommand = giveTerminalControl(dataIN, dataOUT, house);
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
        dataOUT.add(x);
    }
}
