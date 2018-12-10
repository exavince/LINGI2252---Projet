package sample;


import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import main.ConnectedHouseSimulator;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.ArrayList;


public class Main extends Application implements Runnable {

    private static String command = "";
    private static ConnectedHouseSimulator house;
    private static ArrayList<Rectangle> rectangleArrayList = new ArrayList<>();
    private static ArrayList<String> rooms = new ArrayList<>();
    private static ArrayList<StackPane> pane = new ArrayList<>();
    private static Rectangle bedroom;
    private static Rectangle garage;
    TextArea area = new TextArea();

    @Override
    public void start(Stage stage) throws Exception{
        Thread home = new Thread(() -> {
            StartHouse();
        });
        home.start();

        Group root = new Group();
        Scene scene = new Scene(root, 603, 903, Color.BLACK);

        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (!house.dataOUT.isEmpty()) {
                    Rectangle rectangle = new Rectangle(151 * j, 220 + 151 * i, 150, 150);
                    rectangle.setFill(Color.WHITE);
                    Text text = new Text();
                    String name = house.dataOUT.poll();
                    if (name.equals("GARAGE")) {
                        garage = rectangle;
                    }
                    if (name.equals("BEDROOM")) {
                        bedroom = rectangle;
                    }
                    text.setText(name);
                    StackPane stack = new StackPane();
                    stack.setPrefSize(150, 150);
                    stack.setLayoutX(151 * j);
                    stack.setLayoutY(220 + 151 * i);
                    stack.getChildren().addAll(rectangle, text);
                    root.getChildren().add(stack);
                    rectangleArrayList.add(rectangle);
                    pane.add(stack);
                    rooms.add(name);
                }
            }
        }

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        TextField notification = new TextField ();
        notification.setPrefColumnCount(41);
        notification.setText("");

        notification.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER))
            {
                OnDataSend(notification);
            }
        });
        grid.add(notification, 0, 0);


        grid.add(area, 0, 1);
        area.setPrefRowCount(10);
        area.setText("#Welcome to ConnectedHouseSimulator \n" +
                "Choose a scenario (1 or 2)");
        area.setEditable(false);


        Button send = new Button("Send");
        send.setOnAction(e -> {
            OnDataSend(notification);
        });
        grid.add(send,1,0);

        root.getChildren().add(grid);
        stage.setTitle("Connected House");
        stage.setScene(scene);
        stage.show();
        Thread dataProcess = new Thread(() -> {
            run();
        });
        dataProcess.start();
    }

    public void StartHouse() {
        house = new ConnectedHouseSimulator();
        house.run();
    }

    public void OnRectangleClick(Rectangle rectangle, String name) {
        house.dataIN.add("MOVETO");
        house.dataIN.add(name);
        for (Rectangle r : rectangleArrayList) {
            r.setFill(Color.WHITE);
        }
        rectangle.setFill(Color.BLUE);
    }

    public void OnDataSend(TextField notification) {
        command = notification.getText();

        String[] parse = command.split(" ");
        for (String word: parse) {
            house.dataIN.add(word);
        }
        System.out.println(command);
        notification.clear();
    }

    public void run() {
        while (true) {
            if (!house.dataOUT.isEmpty()) {
                //area.setText(area.getText() + "\n" + house.dataOUT.poll());
                String data = house.dataOUT.poll();
                area.appendText(data + "\n");
                if (data.contains("Moving to room")) {
                    String[] parse = data.split("Moving to room ");
                    System.out.println(parse[1]);
                    Rectangle rectangle = null;
                    for (int i=0; i < rooms.size(); i++) {
                        if (rooms.get(i).equals(parse[1])) {
                            rectangle = rectangleArrayList.get(i);
                        }
                    }
                    if (rectangle != null) {
                        for (Rectangle r : rectangleArrayList) {
                            r.setFill(Color.WHITE);
                        }
                        rectangle.setFill(Color.BLUE);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
