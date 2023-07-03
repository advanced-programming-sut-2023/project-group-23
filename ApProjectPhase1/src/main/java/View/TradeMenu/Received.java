package View.TradeMenu;

import Controller.TradeMenuController;
import Model.Trade;
import View.ShopMenu.ShopMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

import static View.TradeMenu.Send.createTrade;
import static View.TradeMenu.Send.showMessage;

public class Received extends Application {

    private ArrayList<Node> nodes = new ArrayList<>();
    private Integer number = 0;
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Received");

        Label playerNickname = new Label("Nickname");
        playerNickname.setLayoutX(10);
        playerNickname.setLayoutY(40);

        Label status = new Label("Status");
        status.setLayoutX(100);
        status.setLayoutY(40);

        Label resourceName = new Label("Resource");
        resourceName.setLayoutX(200);
        resourceName.setLayoutY(40);

        Label amount = new Label("Amount");
        amount.setLayoutX(300);
        amount.setLayoutY(40);

        Label price = new Label("Price");
        price.setLayoutX(400);
        price.setLayoutY(40);

        Label message = new Label("Message");
        message.setLayoutX(500);
        message.setLayoutY(40);

        anchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.UP)) {
                    if (number > 0) {
                        number--;
                        nodes.clear();
                    }
                } else if (keyEvent.getCode().equals(KeyCode.DOWN)) {
                    if (number < (ShopMenu.getCurrentGovernment().getTradeHistory().size() - 1) / 10) {
                        number++;
                        nodes.clear();
                    }
                }
            }
        });

        int size = Math.min(number * 10 + 10, ShopMenu.getCurrentGovernment().getTradeList().size());
        for (int i = number * 10 + 1; i <= size; i++) {
            int integer = i % 10;
            if (integer == 0) integer = 10;
            nodes.addAll(createTrade(ShopMenu.getCurrentGovernment().getTradeList().get(i - 1), integer));
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

        anchorPane.getChildren().addAll(nodes);
        anchorPane.getChildren().addAll(playerNickname, status, resourceName, amount, price, message, back);
        anchorPane.requestFocus();
        stage.show();
    }

    protected static void showDetails(Trade trade) {
        Stage stage = new Stage();
        stage.setTitle(trade.getRequester().getUser().getNickname() + " request");
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);

        Label playerNickname = new Label("Nickname");
        playerNickname.setLayoutX(10);
        playerNickname.setLayoutY(40);

        Label status = new Label("Status");
        status.setLayoutX(100);
        status.setLayoutY(40);

        Label resourceName = new Label("Resource");
        resourceName.setLayoutX(200);
        resourceName.setLayoutY(40);

        Label amount = new Label("Amount");
        amount.setLayoutX(300);
        amount.setLayoutY(40);

        Label price = new Label("Price");
        price.setLayoutX(400);
        price.setLayoutY(40);

        Label message = new Label("Message");
        message.setLayoutX(500);
        message.setLayoutY(40);

        Button close = new Button("Close");
        close.setLayoutX(10);
        close.setLayoutY(10);

        TextArea textArea = new TextArea();
        textArea.setPromptText("Message");
        textArea.setLayoutX(200);
        textArea.setLayoutY(200);

        anchorPane.getChildren().addAll(createTrade(trade, 1));

        Button accept = new Button("Accept");
        accept.setLayoutX(200);
        accept.setLayoutY(550);

        Button reject = new Button("Reject");
        reject.setLayoutX(400);
        reject.setLayoutY(550);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed!");

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Congratulations!");
        alert1.setHeaderText("Success");

        accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!textArea.getText().equals("")) {
                    String wrong = TradeMenuController.accept(trade, textArea.getText());
                    if (wrong.equals("")) alert1.show();
                    else {
                        alert.setHeaderText(wrong);
                        alert.show();
                    }
                } else {
                    alert.setHeaderText("Message field is empty!");
                    alert.show();
                }
                stage.close();
            }
        });

        reject.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (textArea.getText().equals("")) {
                    alert.setHeaderText("Message field is empty!");
                    alert.show();
                } else {
                    TradeMenuController.accept(trade, textArea.getText());
                    alert1.show();
                }
                stage.close();
            }
        });

        message.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showMessage(trade);
            }
        });

        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        anchorPane.getChildren().addAll(close, playerNickname, resourceName, amount, price, message, accept, reject);
        stage.show();
    }
}
