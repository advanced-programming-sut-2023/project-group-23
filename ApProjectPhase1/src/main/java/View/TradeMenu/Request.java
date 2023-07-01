package View.TradeMenu;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Request extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);




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


        anchorPane.getChildren().addAll(back);
        stage.show();
    }
}
