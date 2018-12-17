package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.Room;

class RoomGUI {
    private final Room room;
    private final Rectangle selected;
    private final Rectangle light;
    private final StackPane stack;

    RoomGUI(Room room) {
        this.room = room;
        Rectangle background = new Rectangle();

        background.setHeight(100);
        background.setWidth(100);

        background.setFill(Color.WHITE);

        selected = new Rectangle();
        selected.setFill(Color.TRANSPARENT);
        selected.setStroke(Color.BLUE);
        selected.setStrokeWidth(10);
        selected.widthProperty().bind(background.widthProperty());
        selected.heightProperty().bind(background.heightProperty());

        FlowPane flow = new FlowPane();
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(100);
        Text text = new Text();
        text.setText(room.toString() + "\n");
        flow.getChildren().add(text);

        light = new Rectangle();
        light.setWidth(25);
        light.setHeight(25);
        flow.getChildren().add(light);

        stack = new StackPane();
        stack.setPrefSize(100, 100);
        stack.getChildren().addAll(background, flow, selected);
        stack.setOnMouseClicked(e -> {
            room.getHouse().sendCommand("MOVETO " + room);
        });

        update();
    }

    void addToGrid(GridPane roomsGrid, int column, int row) {
        roomsGrid.add(stack, column, row);
    }

    void update() {
        selected.setVisible(room.getHouse().getPosition() == room.getType());
        light.setFill(room.getLighting() > 0 ? Color.YELLOW : Color.YELLOW.darker().darker().darker());
    }
}
