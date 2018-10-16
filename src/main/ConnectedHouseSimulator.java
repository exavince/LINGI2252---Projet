package main;

import main.event.EnterTheRoom;
import main.event.SoonWakeUpTime;
import main.event.VoiceCommand;
import main.event.WakeUpTime;
import main.feature.*;

public class ConnectedHouseSimulator {
    public static final EventBus EVENT_BUS = new EventBus();

    public static void main(String[] args) {
        //TODO Launch scenarios based on command-line arguments ?
        firstScenario();
    }

    private static void firstScenario() {
        // Register features of the house
        EVENT_BUS.register(new CoffeeMachine());
        EVENT_BUS.register(new HeatingController());
        EVENT_BUS.register(new ConnectedSpeakers());
        EVENT_BUS.register(new ClockController());
        EVENT_BUS.register(new VoiceAssistant());
        EVENT_BUS.register(new LightController());

        println("# Scenario 1");
        println("## Some time before the user wakes up..");
        EVENT_BUS.post(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        EVENT_BUS.post(new WakeUpTime());
        println("## The user is waking up.. ");
        println("## Entering the kitchen..");
        EVENT_BUS.post(new EnterTheRoom(Room.KITCHEN));
        println("## He asks to play his morning playlist.");
        EVENT_BUS.post(VoiceCommand.PLAY_MORNING_PLAYLIST);
        // TODO trigger events on EVENT_BUS
        // TODO add lights that go on when user enter a room ?
    }

    /**
     * Proxy method to shorten code in scenarios
     * @param x  The <code>String</code> to be printed.
     */
    private static void println(String x) {
        System.out.println(x);
    }
}
