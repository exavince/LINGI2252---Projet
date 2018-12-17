package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import main.Room;

class RoomGUI {
    private final Room room;
    private final Rectangle rectangle;
    private final FlowPane flow;
    private final Rectangle light;
    private final Text text;

    RoomGUI(int i, int j, Room room) {
        this.room = room;
        rectangle = new Rectangle(151 * j, 240 + 151 * i, 150, 150);

        flow = new FlowPane();
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(100);

        light = new Rectangle(151 * j + 110, 240 + 151 * i + 80, 40, 40);
        light.setFill(Color.YELLOW);

        text = new Text();
        text.setText(room.toString() + "\n");
        update();
    }

    void addToPane(Pane pane) {
        flow.getChildren().add(text);
        flow.getChildren().add(light);
        pane.getChildren().addAll(rectangle, flow);
    }

    void update() {
        if (room.getHouse().getPosition() == room.getType()) {
            rectangle.setFill(Color.BLUE);
        } else {
            rectangle.setFill(Color.WHITE);
        }
        light.setVisible(room.getLighting() > 0);
    }
}
