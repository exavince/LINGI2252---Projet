package sample;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import main.ConnectedHouseSimulator;
import main.item.sounds.ConnectedSpeakers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Main extends Application {

    private static Scanner userInput;
    private static String command = "";

    @Override
    public void start(Stage stage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 603, 643, Color.BLACK);
        ArrayList<Rectangle> rectangleArrayList = new ArrayList<>();

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));

        TextField notification = new TextField ();
        notification.setPrefColumnCount(41);
        notification.setText("");

        InputStream in = new ByteArrayInputStream(command.getBytes());
        userInput = new Scanner(in);

        notification.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER))
            {
                command = notification.getText();
                System.out.println(command);
                userInput = new Scanner(in);
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

        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++) {
                Rectangle rectangle = new Rectangle(151*i, 40+151*j, 150, 150);
                rectangle.setFill(Color.WHITE);
                rectangle.setOnMouseClicked(e -> {
                    for (Rectangle r:rectangleArrayList) {
                        r.setFill(Color.WHITE);
                    }
                    rectangle.setFill(Color.BLUE);
                });

                root.getChildren().add(rectangle);
                rectangleArrayList.add(rectangle);
            }
        }

        root.getChildren().add(grid);
        stage.setTitle("Connected House");
        stage.setScene(scene);
        stage.show();
        try {
            ConnectedHouseSimulator house = new ConnectedHouseSimulator();
            house.main(userInput);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
