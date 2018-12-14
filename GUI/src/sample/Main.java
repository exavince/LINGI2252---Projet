package sample;


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
import main.Logger;
import main.Room;
import main.item.control.heating.HeatingController;
import main.parametrization.ConnectedHouseJSONParser;
import main.parametrization.ConnectedHouseParser;
import main.routine.SoonWakeUpRoutine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static main.RoomType.BEDROOM;
import static main.RoomType.GARAGE;


public class Main extends Application implements HouseObserver, Logger {

    private static String command = "";
    private static int scenarioChoosen = 0;
    private static ConnectedHouse house;
    private static ArrayList<Rectangle> rectangleArrayList = new ArrayList<>();
    private static ArrayList<Rectangle> lightArrayList = new ArrayList<>();
    private static List<String> rooms = new ArrayList<>();
    private static TextArea area = new TextArea();
    private static TextArea infoArea = new TextArea();

    public static void main(String[] args) {
        launch(args);
    }

    public static void firstScenario() {
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

    public static void endFirstScenario() {
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

    public static void secondScenario() {
        println("# Scenario 2 : Arriving home from work");
        house.moveTo(GARAGE);
        println("-- You are in your garage after having returned from work. What do you do ?");
    }

    private static void endSecondScenarion() {
        if (house.getPosition() != BEDROOM) {
            println("## The user needs to sleep and goes to the bedroom to do so.");
            house.moveTo(BEDROOM);
        }
        println("## He uses his smartphone application to completely lock the house from his bed.");
        house.sendToItems("lock");
        System.exit(0);
    }

    public static void println(String x) {
        area.appendText(x + "\n");
    }

    @Override
    public void start(Stage stage) throws Exception {

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
        infoArea.setText(getHouseInformations());
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

    public void startHouse() {
        println("# Welcome to ConnectedHouseSimulator");
        final ConnectedHouseParser parser = new ConnectedHouseJSONParser();
        try {
            house = parser.parse("./Back_end/config.json", "./Back_end/state.json");
        } catch (IOException e) {
            System.out.println(e);
        }
        house.registerObserver(this);
        house.registerLogger(this);
        println("Choose a scenario (1 or 2)");
    }

    public void onDataSend(TextField notification) {
        command = notification.getText();
        notification.clear();
        System.out.println(command);
        if (scenarioChoosen == 0) {
            switch (command.toUpperCase()) {
                case "1":
                    scenarioChoosen = 1;
                    firstScenario();
                    break;
                case "2":
                    scenarioChoosen = 2;
                    secondScenario();
                    break;
                default:
                    break;
            }
        } else {
            try {
                if (command.toUpperCase().equals("EXIT")) {
                    if (scenarioChoosen == 1) {
                        house.sendCommand(command.toUpperCase());
                        endFirstScenario();
                    } else {
                        house.sendCommand(command.toUpperCase());
                        endSecondScenarion();
                    }
                }
                println("");
                house.sendCommand(command.toUpperCase());
            } catch (Exception e) {
                println("The command " + command + " doesn't exist");
            }
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
            }
            else {
                lightArrayList.get(i).setVisible(false);
            }
        }
        if (rectangle != null) {
            for (Rectangle r : rectangleArrayList) {
                r.setFill(Color.WHITE);
            }
            rectangle.setFill(Color.BLUE);
        }
        infoArea.setText(getHouseInformations());
    }

    public void log(String input) {
        println(input);
    }

    public String getHouseInformations() {
        StringBuilder informations = new StringBuilder();
        for (Room room : house.getRooms()) {
            informations.append(room.toString()).append("\n");
            informations.append("Temperature : ").append(room.getTemperature()).append("\n");
            HeatingController heatingController = (HeatingController) room.getItem(HeatingController.class);
            if (heatingController != null) {
                informations.append("Desired temperature : ").append(heatingController.getDesiredTemperature()).append("\n");
            }
            informations.append("Light intensity : ").append(room.getLighting()).append("\n");
            informations.append("\n");
        }
        return informations.toString();
    }
}
