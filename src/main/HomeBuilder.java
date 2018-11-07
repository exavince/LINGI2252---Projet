package main;

import java.util.ArrayList;

import com.sun.deploy.panel.AndOrRadioPropertyGroup;
import main.item.control.ClockController;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.item.device.CoffeeMachine;
import main.item.device.VoiceAssistant;
import main.item.sounds.ConnectedSpeakers;
import main.sensor.Microphone;
import main.sensor.MovementsSensor;
import main.sensor.TemperatureSensor;

import static main.RoomType.*;

public class HomeBuilder {

    private ConnectedHouse house;

    public ConnectedHouse createHouse() {
        this.house = new ConnectedHouse();
        createRoom();
        createItem();
        createSensor();
        return this.house;
    }

    private void createRoom(){
        house.register(new Room(BEDROOM),
                new Room(KITCHEN),
                new Room(LIVING_ROOM),
                new Room(GARAGE)
        );
    }

    private void createItem(){
        ArrayList<Room> rooms = house.getRooms();

        rooms.get(0).register(
                ConnectedSpeakers.createConnectedSpeakers(),
                HeatingController.createHeatingController(),
                LightController.createLightController(),
                ClockController.createClockController()
        );

        rooms.get(1).register(
                CoffeeMachine.createCoffeeMachine(),
                HeatingController.createHeatingController(),
                ConnectedSpeakers.createConnectedSpeakers(),
                CoffeeMachine.createCoffeeMachine(),
                LightController.createLightController()
        );

        rooms.get(2).register(
                VoiceAssistant.createVoiceAssistant(),
                DoorController.createDoorController(),
                HeatingController.createHeatingController(),
                LightController.createLightController()
        );

        rooms.get(3).register(
                GarageDoorController.createGarageDoorController(),
                LightController.createLightController()
        );
    }

    private void createSensor(){
        ArrayList<Room> rooms = house.getRooms();

        rooms.get(0).register(
                TemperatureSensor.createTemperatureSensor()
        );

        rooms.get(1).register(
                MovementsSensor.createMovementsSensor(),
                Microphone.createMicrophone()
        );

        rooms.get(2).register(
                MovementsSensor.createMovementsSensor()
        );

        rooms.get(3).register(
                MovementsSensor.createMovementsSensor()
        );
    }

}
