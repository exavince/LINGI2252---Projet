package sample;


import framework.FeatureModel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.ConnectedHouse;
import main.HouseObserver;
import main.Room;
import main.command.CommandParser;
import main.item.Item;
import main.item.control.heating.HeatingController;
import main.item.control.light.LightController;
import main.parametrization.ConnectedHouseJSONParser;
import main.parametrization.ConnectedHouseParser;
import main.routine.SoonWakeUpRoutine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static main.RoomType.BEDROOM;
import static main.RoomType.GARAGE;


public class Main extends Application implements HouseObserver {

    private static int scenarioChosen = 0;
    private static ConnectedHouse house;
    private static ArrayList<Rectangle> rectangleArrayList = new ArrayList<>();
    private static ArrayList<Rectangle> lightArrayList = new ArrayList<>();
    private static List<String> rooms = new ArrayList<>();
    private static TextArea area = new TextArea();
    private static TextArea infoArea = new TextArea();

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
        println("## His application allows him to completely lock the house from his car, as he drives away.");
        house.findRoom(GARAGE).sendToItems("lock");
        house.sendToItems("lock");
        System.exit(0);
    }

    private static void secondScenario() {
        println("# Scenario 2 : Arriving home from work");
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
        area.appendText(x + "\n");
    }

    @Override
    public void start(Stage stage) {
        startHouse();

        Group root = new Group();
        Scene scene = new Scene(root, 903, 903, Color.BLACK);
        GridPane grid = new GridPane();
        grid.setHgap(5);
        StackPane informations = new StackPane();
        informations.setLayoutY(240);
        informations.setLayoutX(605);

        TextField notification = new TextField();
        notification.setPrefSize(847, 20);
        notification.setText("");
        notification.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                onDataSend(notification);
            }
        });

        Button send = new Button("Send");
        send.autosize();
        send.setOnAction(e -> {
            onDataSend(notification);
        });

        area.setPrefSize(901, 200);
        area.setLayoutY(30);
        area.setEditable(false);

        rooms = house.getRooms().stream().map(Room::toString).collect(Collectors.toList());
        for (int a = 0; a < house.getRooms().size(); a++) {
            int i = a / 4;
            int j = a % 4;
            String name = rooms.get(a);
            StackPane stack = new StackPane();
            stack.setPrefSize(150, 150);
            stack.setLayoutX(151 * j);
            stack.setLayoutY(240 + 151 * i);

            Rectangle rectangle = new Rectangle(151 * j, 240 + 151 * i, 150, 150);
            rectangle.setFill(Color.WHITE);

            FlowPane flow = new FlowPane();
            flow.setAlignment(Pos.CENTER);
            flow.setHgap(100);

            Rectangle light = new Rectangle(151 * j + 110, 240 + 151 * i + 80, 40, 40);
            light.setFill(Color.YELLOW);
            light.setVisible(false);

            Text text = new Text();
            text.setText(name + "\n");

            flow.getChildren().add(text);
            flow.getChildren().add(light);
            stack.getChildren().addAll(rectangle, flow);
            root.getChildren().add(stack);
            lightArrayList.add(light);
            rectangleArrayList.add(rectangle);
            rooms.add(name);
        }


        Rectangle rect = new Rectangle(50, 50, 295, 660);
        rect.setFill(Color.WHITE);
        infoArea.setEditable(false);
        infoArea.setText(getHouseInformation());
        informations.getChildren().addAll(rect, infoArea);

        grid.add(notification, 0, 0);
        grid.add(send, 1, 0);

        root.getChildren().add(informations);
        root.getChildren().add(grid);
        root.getChildren().add(area);

        stage.setTitle("Connected House");
        stage.setScene(scene);
        stage.show();
    }

    private final Handler loggingHandler = new Handler() {
        @Override
        public void publish(LogRecord record) {
            // TODO Errors in red ?
            println(record.getMessage());
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }
    };

    private void startHouse() {
        println("# Welcome to ConnectedHouseSimulator");
        Logger.getLogger(ConnectedHouseJSONParser.class.getName()).addHandler(loggingHandler);
        Logger.getLogger(FeatureModel.class.getName()).addHandler(loggingHandler);
        Logger.getLogger(Item.class.getName()).addHandler(loggingHandler);
        Logger.getLogger(CommandParser.class.getName()).addHandler(loggingHandler);
        final ConnectedHouseParser parser = new ConnectedHouseJSONParser();
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
        String name = house.getPosition().toString();
        Rectangle rectangle = null;
        for (int i = 0; i < house.getRooms().size(); i++) {
            if (rooms.get(i).equals(name)) {
                rectangle = rectangleArrayList.get(i);
            }
            if (house.getRooms().get(i).getLighting() != 0) {
                lightArrayList.get(i).setVisible(true);
            } else {
                lightArrayList.get(i).setVisible(false);
            }
        }
        if (rectangle != null) {
            for (Rectangle r : rectangleArrayList) {
                r.setFill(Color.WHITE);
            }
            rectangle.setFill(Color.BLUE);
        }
        infoArea.setText(getHouseInformation());
    }

    public void log(String input) {
        println(input);
    }

    private String getHouseInformation() {
        StringBuilder information = new StringBuilder();
        for (Room room : house.getRooms()) {
            information.append(room.toString()).append("\n");
            information.append("Temperature : ").append(room.getTemperature()).append("\n");
            HeatingController heatingController = (HeatingController) room.getItem(HeatingController.class);
            if (heatingController != null) {
                information.append("[Heating controller] Desired temperature : ").append(heatingController.getDesiredTemperature()).append("\n");
            }
            LightController lightController = (LightController) room.getItem(LightController.class);
            if (lightController != null) {
                information.append("[Light controller] Light intensity : ").append(lightController.getIntensity()).append("\n");
            }
            information.append("\n");
        }
        return information.toString();
    }
}
