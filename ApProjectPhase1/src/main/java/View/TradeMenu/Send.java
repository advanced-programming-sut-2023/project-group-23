package View.TradeMenu;

import Model.Trade;
import View.ShopMenu.ShopMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Send extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sent");


        int counter = 0;
        for (Trade trade : ShopMenu.getCurrentGovernment().getTradeHistory()) {
            counter++;
//            createTrade(trade, counter, anchorPane);
        }

        Button back = new Button("Back");
        back.setLayoutX(10);
        back.setLayoutY(10);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setTitle("History");
                try {
                    new History().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        stage.show();
    }

//    private static void createTrade(Trade trade, int i, AnchorPane anchorPane) {
//        Label nickname = new Label();
//        nickname.setLayoutX();
//        nickname.setLayoutY();
//
//        Label status = new Label();
//        status.setLayoutX();
//        status.setLayoutY();
//
//        Label resourceName = new Label();
//        resourceName.setLayoutX();
//        resourceName.setLayoutY();
//
//        Label amount = new Label();
//        amount.setLayoutX();
//        amount.setLayoutY();
//
//        Label price = new Label();
//        price.setLayoutX();
//        price.setLayoutY();
//
//        Label message = new Label("Show");
//        message.setLayoutX();
//        message.setLayoutY();
//
//        message.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                showMessage(trade);
//            }
//        });
//
//        anchorPane.getChildren().addAll(nickname, status, resourceName, amount, price, message);
//    }

    private static void showMessage(Trade trade) {
        AnchorPane anchorPane = new AnchorPane();
        Stage messageStage = new Stage();
        messageStage.setTitle("Trade Messages");
        Scene messageScene = new Scene(anchorPane, 600, 600);
        messageStage.setResizable(false);
        messageStage.setScene(messageScene);

        Button back = new Button("Back");
        back.setLayoutX(10);
        back.setLayoutY(10);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                messageStage.close();
            }
        });

        messageStage.show();
    }
}
