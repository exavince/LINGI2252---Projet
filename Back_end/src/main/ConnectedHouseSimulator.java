package main;

import main.command.Command;
import main.parametrization.ConnectedHouseJSONParser;
import main.parametrization.ConnectedHouseParser;
import main.routine.SoonWakeUpRoutine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static main.RoomType.BEDROOM;
import static main.RoomType.GARAGE;

public class ConnectedHouseSimulator {
    // TODO scenario with rain, weather detector and close the shutter
    public static ConnectedHouse house = null;

    public static void main(String[] args) throws IOException {
        println("# Welcome to ConnectedHouseSimulator");
        final ConnectedHouseParser parser = ConnectedHouseJSONParser.getInstance();
        house = parser.parse("./Back_end/config.json", "./Back_end/state.json");
        println("Choose a scenario (1 or 2)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int scenarioNumber = getScenarioNumber(br);
        if (scenarioNumber == 1) firstScenario(br, house);
        else secondScenario(br, house);
        br.close();
    }

    private static int getScenarioNumber(BufferedReader br) throws IOException {
        int scenarioNumber = 1;
        boolean valid = false;
        while (!valid) {
            String data = br.readLine();
            scenarioNumber = Integer.parseInt(data);
            if (scenarioNumber >= 1 && scenarioNumber <= 2) {
                valid = true;
            } else {
                println("Invalid scenario number");
            }
        }
        return scenarioNumber;
    }

    private static Command giveTerminalControl(BufferedReader br, ConnectedHouse house) {
        Command lastCommand = Command.DONE;
        boolean shouldStopAskingUser = false;

        while (!shouldStopAskingUser) {
            try {
                lastCommand = house.sendCommand(br.readLine());
                if (lastCommand == Command.EXIT) {
                    println("Exiting simulator..");
                    shouldStopAskingUser = true;
                } else if (lastCommand == Command.DONE) {
                    println("Continuing the scenario..");
                    shouldStopAskingUser = true;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                println("Please enter another command or \"EXIT\" to exit.");
            }
        }
        return lastCommand;
    }

    private static void firstScenario(BufferedReader br, ConnectedHouse house) throws IOException {
        println("# Scenario 1 : Waking up in the bed");
        house.moveTo(BEDROOM);
        println("## Some time before the user wakes up..");
        new SoonWakeUpRoutine().call(house);
        //house.broadcast(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        // TODO Put in a configurable WakeUpRoutine ?
        house.findRoom(BEDROOM).sendToItems("trigger_alarm");
        println("-- You wake up in your bedroom. What do you do ?");
        final Command lastCommand = giveTerminalControl(br, house);
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

    private static void secondScenario(BufferedReader br, ConnectedHouse house) throws IOException {
        println("# Scenario 2 : Arriving home from work");
        house.moveTo(GARAGE);
        println("-- You are in your garage after having returned from work. What do you do ?");
        final Command lastCommand = giveTerminalControl(br, house);
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
