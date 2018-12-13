package main.parametrization;

import main.Room;
import main.constraint.Constraint;
import main.constraint.Implication;
import main.constraint.LogicalOr;
import main.item.control.ClockController;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.item.control.door.ShutterController;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.item.device.CoffeeMachine;
import main.item.device.VoiceAssistant;
import main.item.sounds.ConnectedSpeakers;
import main.primitive.Optional;
import main.primitive.Or;
import main.primitive.Primitive;
import main.sensor.Microphone;
import main.sensor.MovementsSensor;
import main.sensor.TemperatureSensor;
import main.sensor.WeatherSensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeatureModel {
    /* TODO Generify */
    private final static FeatureModel INSTANCE = new FeatureModel();
    private final List<Primitive> featureDiagram = new ArrayList<>();
    private final List<Constraint> crossTreeConstraints = new ArrayList<>();
    // TODO Based on a string, one should be able to get the feature for command line
    public Feature root = new AbstractFeature("Central");
    public Feature control = new AbstractFeature("Control");
    public Feature comfortControllers = new AbstractFeature("Comfort_controllers");
    public Feature lightController = new ItemFeature(LightController.class);
    public Feature shutterController = new ItemFeature(ShutterController.class);
    public Feature heatingController = new ItemFeature(HeatingController.class);
    public Feature assistantControllers = new AbstractFeature("Assistant_controllers");
    public Feature voiceAssistant = new ItemFeature(VoiceAssistant.class);
    public Feature clockController = new ItemFeature(ClockController.class);
    public Feature securityControllers = new AbstractFeature("Security_controllers");
    public Feature doorController = new ItemFeature(DoorController.class);
    public Feature garageDoorController = new ItemFeature(GarageDoorController.class);
    public Feature devices = new AbstractFeature("Devices");
    public Feature mediaDevices = new AbstractFeature("Media_devices");
    public Feature connectedSpeakers = new ItemFeature(ConnectedSpeakers.class);
    public Feature others = new AbstractFeature("Others");
    public Feature coffeeMachine = new ItemFeature(CoffeeMachine.class);
    public Feature sensors = new AbstractFeature("Sensors");
    public Feature movementsSensor = new ItemFeature(MovementsSensor.class);
    public Feature weatherSensor = new ItemFeature(WeatherSensor.class);
    public Feature temperatureSensor = new ItemFeature(TemperatureSensor.class);
    public Feature microphone = new ItemFeature(Microphone.class);

    private FeatureModel() {
        addFeatureDiagramPrimitives(
                new Optional(root, control, devices, sensors),
                new Or(control, comfortControllers, assistantControllers, securityControllers),
                new Or(comfortControllers, lightController, shutterController, heatingController),
                new Or(assistantControllers, voiceAssistant, clockController),
                new Or(securityControllers, doorController, garageDoorController),

                new Or(devices, mediaDevices, others),
                new Optional(mediaDevices, connectedSpeakers),
                new Optional(others, coffeeMachine),

                new Or(sensors, temperatureSensor, weatherSensor, movementsSensor, microphone)
        );

        addCrossTreeConstraints(
                new LogicalOr(lightController, movementsSensor),
                new Implication(shutterController, weatherSensor),
                new Implication(heatingController, temperatureSensor),
                new Implication(voiceAssistant, microphone),
                new Implication(voiceAssistant, connectedSpeakers)
        );
    }

    public static FeatureModel getInstance() {
        return INSTANCE;
    }

    private void addFeatureDiagramPrimitives(Primitive... primitivesIn) {
        featureDiagram.addAll(Arrays.asList(primitivesIn));
    }

    private void addCrossTreeConstraints(Constraint... constraintsIn) {
        crossTreeConstraints.addAll(Arrays.asList(constraintsIn));
    }

    /**
     * @param context the configuration
     * @return Whether the configuration is valid or not
     */
    public boolean interpret(Room context) {
        boolean valid = true;
        System.out.println("Room: " + context);
        System.out.println("Feature diagram:");
        for (Constraint rule : featureDiagram) {
            String output = "\t" + rule + ": ";
            if (!rule.interpret(context)) {
                System.err.println(output + "NOT VALID");
                valid = false;
            } else {
                System.out.println(output + "VALID");
            }
        }
        System.out.println("Cross-tree constraints:");
        for (Constraint rule : crossTreeConstraints) {
            String output = "\t" + rule + ": ";
            if (!rule.interpret(context)) {
                System.err.println(output + "NOT VALID");
                valid = false;
            } else {
                System.out.println(output + "VALID");
            }
        }
        return valid;
    }
}
