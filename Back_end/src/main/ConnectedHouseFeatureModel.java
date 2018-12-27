package main;

import framework.AbstractFeature;
import framework.Feature;
import framework.FeatureModel;
import framework.constraint.Implication;
import framework.constraint.LogicalOr;
import framework.primitive.Mandatory;
import framework.primitive.Optional;
import framework.primitive.Or;
import main.item.control.ClockController;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.item.control.door.ShutterController;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.item.device.CoffeeMachine;
import main.item.device.RoboticVacuumCleaner;
import main.item.device.VoiceAssistant;
import main.item.sensor.Microphone;
import main.item.sensor.MovementsSensor;
import main.item.sensor.TemperatureSensor;
import main.item.sensor.WeatherSensor;
import main.item.sounds.ConnectedSpeakers;
import main.parametrization.ItemFeature;

public class ConnectedHouseFeatureModel extends FeatureModel {
    private static ConnectedHouseFeatureModel INSTANCE = null;

    private ConnectedHouseFeatureModel() {
        Feature root = new AbstractFeature("Central");
        Feature control = new AbstractFeature("Control");
        Feature comfortControllers = new AbstractFeature("Comfort_controllers");
        Feature lightController = new ItemFeature(LightController.class);
        Feature shutterController = new ItemFeature(ShutterController.class);
        Feature heatingController = new ItemFeature(HeatingController.class);
        Feature assistantControllers = new AbstractFeature("Assistant_controllers");
        Feature voiceAssistant = new ItemFeature(VoiceAssistant.class);
        Feature clockController = new ItemFeature(ClockController.class);
        Feature securityControllers = new AbstractFeature("Security_controllers");
        Feature doorController = new ItemFeature(DoorController.class);
        Feature garageDoorController = new ItemFeature(GarageDoorController.class);
        Feature devices = new AbstractFeature("Devices");
        Feature cleaningDevices = new AbstractFeature("Cleaning_devices");
        Feature roboticVacuumCleaner = new ItemFeature(RoboticVacuumCleaner.class);
        Feature mediaDevices = new AbstractFeature("Media_devices");
        Feature connectedSpeakers = new ItemFeature(ConnectedSpeakers.class);
        Feature others = new AbstractFeature("Others");
        Feature coffeeMachine = new ItemFeature(CoffeeMachine.class);
        Feature sensors = new AbstractFeature("Sensors");
        Feature movementsSensor = new ItemFeature(MovementsSensor.class);
        Feature weatherSensor = new ItemFeature(WeatherSensor.class);
        Feature temperatureSensor = new ItemFeature(TemperatureSensor.class);
        Feature microphone = new ItemFeature(Microphone.class);

        addFeatures(
                root,
                control,
                comfortControllers,
                lightController,
                shutterController,
                heatingController,
                assistantControllers,
                voiceAssistant,
                clockController,
                securityControllers,
                doorController,
                garageDoorController,
                devices,
                cleaningDevices,
                roboticVacuumCleaner,
                mediaDevices,
                connectedSpeakers,
                others,
                coffeeMachine,
                sensors,
                movementsSensor,
                weatherSensor,
                temperatureSensor,
                microphone
        );

        addFeatureDiagramPrimitives(
                new Optional(root, control, devices, sensors),
                new Or(control, comfortControllers, assistantControllers, securityControllers),
                new Or(comfortControllers, lightController, shutterController, heatingController),
                new Or(assistantControllers, voiceAssistant, clockController),
                new Or(securityControllers, doorController, garageDoorController),

                new Or(devices, cleaningDevices, mediaDevices, others),
                new Mandatory(cleaningDevices, roboticVacuumCleaner),
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

    public static ConnectedHouseFeatureModel getInstance() {
        if (INSTANCE == null) INSTANCE = new ConnectedHouseFeatureModel();
        return INSTANCE;
    }

}
