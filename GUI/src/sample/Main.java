package sample;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import main.ConnectedHouse;
import main.ConnectedHouseSimulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;


public class Main extends Application {

    private static Boolean firstGet = true;
    private static String command = "";
    private static ConnectedHouseSimulator house;
    private static ArrayList<Rectangle> rectangleArrayList = new ArrayList<>();
    private static Rectangle bedroom;
    private static Rectangle garage;

    @Override
    public void start(Stage stage) throws Exception{
        Thread thread = new Thread(() -> {
            StartHouse();
        });
        thread.start();

        Group root = new Group();
        Scene scene = new Scene(root, 603, 643, Color.BLACK);

        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                if (!house.dataOUT.isEmpty()) {
                    Rectangle rectangle = new Rectangle(151 * j, 40 + 151 * i, 150, 150);
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
                    stack.setLayoutY(40 + 151 * i);
                    stack.getChildren().addAll(rectangle, text);
                    rectangle.setOnMouseClicked(e -> {
                        OnRectangeClick(rectangle, name);
                    });

                    root.getChildren().add(stack);
                    rectangleArrayList.add(rectangle);
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
                command = notification.getText();
                house.dataIN.add(command);
                System.out.println(command);
                if(firstGet) {
                    if (command.equals("1")) {
                        for (Rectangle r : rectangleArrayList) {
                            r.setFill(Color.WHITE);
                        }
                        bedroom.setFill(Color.BLUE);
                    }
                    else {
                        for (Rectangle r : rectangleArrayList) {
                            r.setFill(Color.WHITE);
                        }
                        garage.setFill(Color.BLUE);
                    }
                }
                firstGet = false;
                notification.clear();
            }
        });
        grid.add(notification, 0, 0);

        Button send = new Button("Send");
        send.setOnAction(e -> {
            String command = notification.getText();
            System.out.println(command);
            notification.clear();
        });
        grid.add(send,1,0);

        root.getChildren().add(grid);
        stage.setTitle("Connected House");
        stage.setScene(scene);
        stage.show();
    }

    public void StartHouse() {
        house = new ConnectedHouseSimulator();
        house.run();
    }

    public void OnRectangeClick(Rectangle rectangle, String name) {
        house.dataIN.add("MOVETO");
        house.dataIN.add(name);
        for (Rectangle r : rectangleArrayList) {
            r.setFill(Color.WHITE);
        }
        rectangle.setFill(Color.BLUE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
