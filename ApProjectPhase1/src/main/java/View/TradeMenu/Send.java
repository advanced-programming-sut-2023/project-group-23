package View.TradeMenu;

import Controller.GameMenuController;
import Model.*;
import View.ShopMenu.ShopMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Send extends Application {

    private static ArrayList<Node> nodes = new ArrayList<>();
    private Integer number = 0;

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sent");

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

        nodes.clear();
        int size = Math.min(number * 10 + 10, ShopMenu.getCurrentGovernment().getTradeHistory().size());
        for (int i = number * 10 + 1; i <= size; i++) {
            Trade trade = GameMenuController.getCurrentGovernment().getTradeHistory().get(i - 1);
            int integer = i % 10;
            if (integer == 0) integer = 10;
//            System.out.println(ShopMenu.getCurrentGovernment().getUser().getNickname());
            nodes.addAll(createSendTrade(trade, integer));
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

    private static ArrayList<Node> createSendTrade(Trade trade, int i) {
        ArrayList<Node> results = new ArrayList<>();
        int y = 40 + 30 * i;
        Label nickname = new Label(trade.getReceiver().getUser().getNickname());
        nickname.setLayoutX(10);
        nickname.setLayoutY(y);
        results.add(nickname);

        Label status = new Label();
        if (!trade.isShowed()) status.setText("not seen");
        else if (trade.getIsAccepted().equals(1)) status.setText("accepted");
        else if (trade.getIsAccepted().equals(-1)) status.setText("rejected");
        else status.setText("seen");
        status.setLayoutX(100);
        status.setLayoutY(y);
        results.add(status);

        Label resourceName = new Label(trade.getResourceType().getName());
        resourceName.setLayoutX(200);
        resourceName.setLayoutY(y);
        results.add(resourceName);

        Integer amountInt = trade.getResourceAmount();
        Label amount = new Label(amountInt.toString());
        amount.setLayoutX(300);
        amount.setLayoutY(y);
        results.add(amount);

        Integer priceInt = trade.getPrice();
        Label price = new Label(priceInt.toString());
        price.setLayoutX(400);
        price.setLayoutY(y);
        results.add(price);

        Label message = new Label("Show");
        message.setLayoutX(500);
        message.setLayoutY(y);
        results.add(message);

        message.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                showMessage(trade);
            }
        });

        return results;
    }

    protected static void showMessage(Trade trade) {
        AnchorPane anchorPane = new AnchorPane();
        Stage messageStage = new Stage();
        messageStage.setTitle("Trade Messages");
        Scene messageScene = new Scene(anchorPane, 600, 600);
        messageStage.setResizable(false);
        messageStage.setScene(messageScene);

        Label requester = new Label("Requester Message:");
        requester.setLayoutX(50);
        requester.setLayoutY(50);

        Text requesterMessage = new Text(trade.getRequesterMessage());
        requesterMessage.setLayoutX(50);
        requesterMessage.setLayoutY(90);

        Label receiver = new Label("Receiver Message:");
        receiver.setLayoutX(50);
        receiver.setLayoutY(300);

        Text receiverMessage = new Text(trade.getReceiverMessage());
        receiverMessage.setLayoutX(50);
        receiverMessage.setLayoutY(340);


        Button back = new Button("Back");
        back.setLayoutX(10);
        back.setLayoutY(10);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                messageStage.close();
            }
        });

        anchorPane.getChildren().addAll(back, receiverMessage, requesterMessage, receiver, requester);
        messageStage.show();
    }
}
