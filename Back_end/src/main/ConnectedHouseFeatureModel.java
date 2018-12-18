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

public class ConnectedHouseFeatureModel extends FeatureModel<Room> {
    private static ConnectedHouseFeatureModel INSTANCE = null;

    private ConnectedHouseFeatureModel() {
        Feature<Room> root = new AbstractFeature<>("Central");
        Feature<Room> control = new AbstractFeature<>("Control");
        Feature<Room> comfortControllers = new AbstractFeature<>("Comfort_controllers");
        Feature<Room> lightController = new ItemFeature(LightController.class);
        Feature<Room> shutterController = new ItemFeature(ShutterController.class);
        Feature<Room> heatingController = new ItemFeature(HeatingController.class);
        Feature<Room> assistantControllers = new AbstractFeature<>("Assistant_controllers");
        Feature<Room> voiceAssistant = new ItemFeature(VoiceAssistant.class);
        Feature<Room> clockController = new ItemFeature(ClockController.class);
        Feature<Room> securityControllers = new AbstractFeature<>("Security_controllers");
        Feature<Room> doorController = new ItemFeature(DoorController.class);
        Feature<Room> garageDoorController = new ItemFeature(GarageDoorController.class);
        Feature<Room> devices = new AbstractFeature<>("Devices");
        Feature<Room> cleaningDevices = new AbstractFeature<>("Cleaning_devices");
        Feature<Room> roboticVacuumCleaner = new ItemFeature(RoboticVacuumCleaner.class);
        Feature<Room> mediaDevices = new AbstractFeature<>("Media_devices");
        Feature<Room> connectedSpeakers = new ItemFeature(ConnectedSpeakers.class);
        Feature<Room> others = new AbstractFeature<>("Others");
        Feature<Room> coffeeMachine = new ItemFeature(CoffeeMachine.class);
        Feature<Room> sensors = new AbstractFeature<>("Sensors");
        Feature<Room> movementsSensor = new ItemFeature(MovementsSensor.class);
        Feature<Room> weatherSensor = new ItemFeature(WeatherSensor.class);
        Feature<Room> temperatureSensor = new ItemFeature(TemperatureSensor.class);
        Feature<Room> microphone = new ItemFeature(Microphone.class);

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
