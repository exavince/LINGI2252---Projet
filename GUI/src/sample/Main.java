package sample;


import framework.FeatureModel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import main.ConnectedHouse;
import main.HouseObserver;
import main.Room;
import main.command.CommandParser;
import main.item.Item;
import main.item.control.door.DoorController;
import main.item.control.door.GarageDoorController;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.parametrization.ConnectedHouseJSONParser;
import main.parametrization.ConnectedHouseParser;
import main.routine.SoonWakeUpRoutine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static main.RoomType.BEDROOM;
import static main.RoomType.GARAGE;
import static main.RoomType.NOWHERE;

public class Main extends Application implements HouseObserver {
    private static int scenarioChosen = 0;
    private static ConnectedHouse house;
    private static List<RoomGUI> roomGUIs = new ArrayList<>();
    private static TextFlow area = new TextFlow();
    private static ScrollPane scrollPane = new ScrollPane();
    private static TextArea infoArea = new TextArea();
    private final Handler loggingHandler = new Handler() {
        @Override
        public void publish(LogRecord record) {
            Level level = record.getLevel();
            if (level == Level.WARNING) println(Color.DARKORANGE, record.getMessage());
            else if (level == Level.SEVERE) println(Color.RED, record.getMessage());
            else println(record.getMessage());
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    private static void firstScenario() {
        println("# Scenario 1 : Waking up in the bed");
        house.moveTo(BEDROOM);
        println("## Some time before the user wakes up..");
        new SoonWakeUpRoutine().call(house);
        //house.broadcast(new SoonWakeUpTime());
        println("## Now the user must wake up.");
        // TODO Put in a configurable WakeUpRoutine ?
        house.findRoom(BEDROOM).sendToItems("trigger_alarm");
        println("-- You wake up in your bedroom. What do you do ?");
    }

    private static void endFirstScenario() {
        if (house.getPosition() != GARAGE) {
            println("## The user moves to the garage to go to work");
            house.moveTo(GARAGE);
        }
        println("## User enters his car");
        println("## Using his smartphone from his car, he opens the garage door.");
        house.findRoom(GARAGE).sendToItems("open");
        house.moveTo(NOWHERE);
        println("## His application allows him to completely lock the house from his car, as he drives away.");
        house.findRoom(GARAGE).sendToItems("lock");
        house.sendToItems("lock");
        System.exit(0);
    }

    private static void secondScenario() {
        println("# Scenario 2 : Arriving home from work");
        house.findRoom(GARAGE).sendToItems("open");
        house.moveTo(GARAGE);
        println("-- You are in your garage after having returned from work. What do you do ?");
    }

    private static void endSecondScenario() {
        if (house.getPosition() != BEDROOM) {
            println("## The user needs to sleep and goes to the bedroom to do so.");
            house.moveTo(BEDROOM);
        }
        println("## He uses his smartphone application to completely lock the house from his bed.");
        house.sendToItems("lock");
        System.exit(0);
    }

    private static void println(String x) {
        println(Color.BLACK, x);
    }

    private static void println(Color color, String x) {
        Text text = new Text();
        text.setFont(Font.font(15));
        text.setFill(color);
        text.setText(x + "\n");
        area.getChildren().add(text);
    }

    @Override
    public void start(Stage stage) {
        startHouse();

        Group root = new Group();
        Scene scene = new Scene(root, 1003, 903, Color.BLACK);
        BorderPane border = new BorderPane();
        root.getChildren().add(border);

        // Input bar

        TextField notification = new TextField();
        notification.setText("");
        notification.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                onDataSend(notification);
            }
        });

        Button send = new Button("Send");
        send.setPrefWidth(100);
        send.setOnAction(e -> {
            onDataSend(notification);
        });

        notification.prefWidthProperty().bind(scene.widthProperty().subtract(send.getPrefWidth() + 10));

        HBox inputBar = new HBox();
        inputBar.setSpacing(5);
        inputBar.getChildren().addAll(notification, send);

        border.setTop(inputBar);

        // Log
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setContent(area);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200);

        border.setBottom(scrollPane);

        GridPane roomsGrid = new GridPane();
        roomsGrid.setHgap(5);

        for (int a = 0; a < house.getRooms().size(); a++) {
            int row = a / 4;
            int column = a % 4;

            RoomGUI roomGUI = new RoomGUI(house.getRooms().get(a));
            roomGUI.addToGrid(roomsGrid, column, row);
            roomGUIs.add(roomGUI);
        }
        border.setCenter(roomsGrid);

        StackPane informations = new StackPane();

        infoArea.setEditable(false);
        infoArea.setText(getHouseInformation());
        infoArea.setMinHeight(600);
        infoArea.setMaxWidth(400);
        infoArea.setWrapText(true);

        informations.getChildren().add(infoArea);

        border.setRight(informations);

        stage.setTitle("Connected House");
        stage.setScene(scene);
        stage.show();
    }

    private void startHouse() {
        println("# Welcome to ConnectedHouseSimulator");
        Logger.getLogger(ConnectedHouseJSONParser.class.getName()).addHandler(loggingHandler);
        Logger.getLogger(FeatureModel.class.getName()).addHandler(loggingHandler);
        Logger.getLogger(Item.class.getName()).addHandler(loggingHandler);
        CommandParser.LOGGER.addHandler(loggingHandler);
        final ConnectedHouseParser parser = ConnectedHouseJSONParser.getInstance();
        try {
            house = parser.parse("./Back_end/config.json", "./Back_end/state.json");
        } catch (IOException e) {
            System.err.println(e);
        }
        house.registerObserver(this);
        println("Choose a scenario (1 or 2)");
    }

    private void onDataSend(TextField notification) {
        String command = notification.getText();
        notification.clear();
        if (scenarioChosen == 0) {
            switch (command.toUpperCase()) {
                case "1":
                    scenarioChosen = 1;
                    firstScenario();
                    break;
                case "2":
                    scenarioChosen = 2;
                    secondScenario();
                    break;
                default:
                    break;
            }
        } else {
            if (command.toUpperCase().equals("EXIT")) {
                if (scenarioChosen == 1) {
                    house.sendCommand(command);
                    endFirstScenario();
                } else {
                    house.sendCommand(command);
                    endSecondScenario();
                }
            }
            println("");
            house.sendCommand(command);
        }
    }

    public void update() {
        roomGUIs.forEach(RoomGUI::update);
        infoArea.setText(getHouseInformation());
    }

    private String getHouseInformation() {
        StringBuilder information = new StringBuilder();
        information.append("Weather: ").append(house.getWeatherStatus()).append("\n");
        information.append("Mood setting: ").append(house.getMood()).append("\n");
        information.append("\n");
        for (Room room : house.getRooms()) {
            information.append(room.toString()).append("\n");
            information.append("Lighting: ").append(room.getLighting()).append("\n");
            information.append("Temperature: ").append(room.getTemperature()).append("\n");
            appendItemAttributeIfInRoom(information, room, HeatingController.class, "Desired temperature", HeatingController::getDesiredTemperature);
            appendItemAttributeIfInRoom(information, room, LightController.class, "Light intensity", LightController::getIntensity);
            appendItemAttributeIfInRoom(information, room, DoorController.class, "Locked", DoorController::isLocked);
            appendItemAttributeIfInRoom(information, room, GarageDoorController.class, "Locked", GarageDoorController::isLocked);
            information.append("\n");
        }
        return information.toString();
    }

    private <T extends Item> void appendItemAttributeIfInRoom(StringBuilder information, Room room, Class<T> itemClass, String attributeName, Function<T, Object> getter) {
        T roomItem = room.getItem(itemClass);
        if (roomItem != null) {
            information.append("[").append(itemClass.getSimpleName()).append("] ").append(attributeName).append(": ")
                    .append(getter.apply(roomItem)).append("\n");
        }
    }
}
