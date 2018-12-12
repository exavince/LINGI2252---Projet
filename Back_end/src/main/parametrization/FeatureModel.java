package main.parametrization;

import main.Room;
import main.item.control.ClockController;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.item.control.door.ShutterController;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.item.device.CoffeeMachine;
import main.item.device.VoiceAssistant;
import main.item.sounds.ConnectedSpeakers;
import main.sensor.Microphone;
import main.sensor.MovementsSensor;
import main.sensor.TemperatureSensor;
import main.sensor.WeatherSensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureModel {
    /* TODO Generify */
    private final List<Expression> rules = new ArrayList<>();

    // TODO Based on a string, one should be able to get the feature for command line
    private Feature control = new AbstractFeature("Control");
    private Feature comfortControllers = new AbstractFeature("Comfort_controllers");
    private Feature lightController = new ItemFeature(LightController.class);
    private Feature shutterController = new ItemFeature(ShutterController.class);
    private Feature heatingController = new ItemFeature(HeatingController.class);

    private Feature assistantControllers = new AbstractFeature("Assistant_controllers");
    private Feature voiceAssistant = new ItemFeature(VoiceAssistant.class);
    private Feature clockController = new ItemFeature(ClockController.class);
    private Feature securityControllers = new AbstractFeature("Security_controllers");
    private Feature doorController = new ItemFeature(DoorController.class);
    private Feature garageDoorController = new ItemFeature(GarageDoorController.class);


    private Feature devices = new AbstractFeature("Devices");
    private Feature mediaDevices = new AbstractFeature("Media_devices");
    private Feature connectedSpeakers = new ItemFeature(ConnectedSpeakers.class);
    private Feature others = new AbstractFeature("Others");
    private Feature coffeeMachine = new ItemFeature(CoffeeMachine.class);

    private Feature sensors = new AbstractFeature("Sensors");
    private Feature movementsSensor = new ItemFeature(MovementsSensor.class);
    private Feature weatherSensor = new ItemFeature(WeatherSensor.class);
    private Feature temperatureSensor = new ItemFeature(TemperatureSensor.class);
    private Feature microphone = new ItemFeature(Microphone.class);


    FeatureModel() {
        // Feature diagram
        addRules(
                new DoubleImplication(new OrExpression(comfortControllers, assistantControllers, securityControllers), control),
                new DoubleImplication(new OrExpression(lightController, shutterController, heatingController), comfortControllers),
                new DoubleImplication(new OrExpression(voiceAssistant, clockController), assistantControllers),
                new DoubleImplication(new OrExpression(doorController, garageDoorController), securityControllers),

                new DoubleImplication(new OrExpression(mediaDevices, others), devices),
                new Implication(connectedSpeakers, mediaDevices),
                new Implication(coffeeMachine, others),

                new DoubleImplication(new OrExpression(temperatureSensor, weatherSensor, movementsSensor, microphone), sensors),

                // Cross-tree constraints
                new OrExpression(lightController, movementsSensor),
                new Implication(shutterController, weatherSensor),
                new Implication(heatingController, temperatureSensor),
                new Implication(voiceAssistant, microphone),
                new Implication(voiceAssistant, connectedSpeakers)
        );
    }

    private void addRules(Expression minimumRule, Expression... rulesIn) {
        rules.add(minimumRule);
        rules.addAll(Arrays.asList(rulesIn));
    }

    /**
     * @param context the configuration
     * @return Whether the configuration is valid or not
     */
    public boolean interpret(Room context) {
        for (Expression rule : rules) {
            if (!rule.interpret(context)) {
                System.err.println("Rule " + rule + " is not valid in Room " + context);
                return false;
            } else {
                System.out.println("Rule " + rule + " is valid in Room " + context);
            }
        }
        return true;
    }
}
