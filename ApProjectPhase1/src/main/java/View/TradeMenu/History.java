package View.TradeMenu;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class History extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("History");

        Button sent = new Button("Sent");
        sent.setLayoutX(160);
        sent.setLayoutY(130);
        sent.setPrefWidth(80);


        Button received = new Button("Received");
        received.setLayoutX(160);
        received.setLayoutY(200);
        received.setPrefWidth(80);

        Button back = new Button("Back");
        back.setLayoutX(10);
        back.setLayoutY(10);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setTitle("Trade Menu");
                try {
                    new TradeMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        anchorPane.getChildren().addAll(back, sent, received);
        stage.show();
    }
}
