package View.TradeMenu;

import Controller.PreGameController;
import Model.*;
import View.ShopMenu.ShopMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Request extends Application {
    private static Stage mainStage;

    private Government receiverGovernment;

    public void setReceiverGovernment(Government receiverGovernment) {
        this.receiverGovernment = receiverGovernment;
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);

        initializeTradeMenu(anchorPane);


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

    private void initializeTradeMenu(AnchorPane anchorPane) {
        Text nickname = new Text("Nickname");
        nickname.setLayoutX(150);
        nickname.setLayoutY(20);

        int counter = 0;
        for (Government government : PreGameController.getCurrentGame().getGovernments()) {
            if (government.equals(PreGameController.getCurrentGovernment())) continue;
            counter++;
            Text playerNickname = new Text();
            playerNickname.setText(government.getUser().getNickname());
            playerNickname.setLayoutX(150);
            playerNickname.setLayoutY(30 + 30 * counter);

            playerNickname.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mainStage.close();
                    Stage stage = new Stage();
                    stage.setTitle("Trade with " + playerNickname.getText());
                    setReceiverGovernment(government);
                    createTradePage(stage);
                }
            });

            anchorPane.getChildren().add(playerNickname);
        }


        anchorPane.getChildren().add(nickname);
    }

    private void createTradePage(Stage stage) {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 300, 800);
        stage.setScene(scene);

        Button back = new Button("Back");
        back.setLayoutX(10);
        back.setLayoutY(10);
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
                Stage stage1 = new Stage();
                stage1.setTitle("Request a trade");
                try {
                    new Request().start(stage1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Label resource = new Label("Resource Type");
        resource.setLayoutX(30);
        resource.setLayoutY(40);

        Label storage = new Label("Storage");
        storage.setLayoutX(200);
        storage.setLayoutY(40);

        int counter = 0;
        for (FoodType value : FoodType.values()) {
            counter++;
            ImageView imageView = new ImageView(new Image(getClass().getResource("/resource/" + value.getName() + ".png").toExternalForm()));
            imageView.setLayoutX(30);
            imageView.setLayoutY(40 + 40 * counter);
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    makeTrade(imageView.getImage(), value.getName());
                }
            });
            Label amount = new Label(PreGameController.getCurrentGovernment().getFoodAmountByFood(value).toString());
            amount.setLayoutX(200);
            amount.setLayoutY(40 + 40 * counter);
            anchorPane.getChildren().addAll(imageView, amount);
        }

        for (ResourceType value : ResourceType.values()) {
            if (isFood(value.getName())) continue;
            counter++;
            ImageView imageView = new ImageView(new Image(getClass().getResource("/resource/" + value.getName() + ".png").toExternalForm()));
            imageView.setLayoutX(30);
            imageView.setLayoutY(40 + 40 * counter);
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    makeTrade(imageView.getImage(), value.getName());
                }
            });
            Label amount = new Label(PreGameController.getCurrentGovernment().getAmountByResource(value).toString());
            amount.setLayoutX(200);
            amount.setLayoutY(40 + 40 * counter);
            anchorPane.getChildren().addAll(imageView, amount);
        }



        anchorPane.getChildren().addAll(resource, storage, back);
        stage.show();
    }

    private boolean isFood(String name) {
        for (FoodType value : FoodType.values()) {
            if (value.getName().equals(name)) return true;
        }
        return false;
    }

    private void makeTrade(Image image, String name) {
        AnchorPane anchorPane = new AnchorPane();
        Stage stage = new Stage();
        stage.setTitle("Trade");
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setResizable(false);
        stage.setScene(scene);

        Label wrong = new Label("");
        wrong.setLayoutX(200);
        wrong.setLayoutY(225);
        wrong.setTextFill(Color.RED);

        TextArea textArea = new TextArea();
        textArea.setPromptText("Message");
        textArea.setLayoutX(200);
        textArea.setLayoutY(120);
        textArea.setPrefHeight(100);
        textArea.setPrefWidth(150);

        TextField price = new TextField();
        price.setPromptText("Price");
        price.setLayoutX(200);
        price.setLayoutY(90);

        Button increase = new Button("+");
        increase.setLayoutX(200);
        increase.setLayoutY(10);

        Button decrease = new Button("-");
        decrease.setLayoutX(200);
        decrease.setLayoutY(60);

        Label amount = new Label("1");
        amount.setLayoutX(207);
        amount.setLayoutY(40);


        increase.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Integer newAmount = Integer.parseInt(amount.getText()) + 1;
                amount.setText(newAmount.toString());
            }
        });

        decrease.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!amount.getText().equals("1")) {
                    Integer newAmount = Integer.parseInt(amount.getText()) - 1;
                    amount.setText(newAmount.toString());
                }
            }
        });

        ImageView imageView = new ImageView(image);
        imageView.setLayoutX(10);
        imageView.setLayoutY(40);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);


        Button cancel = new Button("Cancel");
        cancel.setLayoutX(10);
        cancel.setLayoutY(10);

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        Button request = new Button("Request");
        request.setLayoutX(130);
        request.setLayoutY(350);

        request.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrong.setText("");
                if (!price.getText().matches("\\d+") || price.getText().equals("0")) {
                    wrong.setText("price must be a positive integer!");
                    return;
                } else if (textArea.getText().equals("")) {
                    wrong.setText("Fill the message field!");
                    return;
                }
                ResourceType resourceType = null;
                for (ResourceType value : ResourceType.values()) {
                    if (value.getName().equals(name)) {
                        resourceType = value;
                        break;
                    }
                }
                finishRequest(stage, Integer.parseInt(price.getText()), textArea.getText(), resourceType, Integer.parseInt(amount.getText()),
                        ShopMenu.getCurrentGovernment(), receiverGovernment);
            }
        });

        Button donate = new Button("Donate");
        donate.setLayoutX(250);
        donate.setLayoutY(350);

        donate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrong.setText("");
                if (textArea.getText().equals("")) {
                    wrong.setText("Fill the message field!");
                    return;
                }
                ResourceType resourceType = null;
                for (ResourceType value : ResourceType.values()) {
                    if (value.getName().equals(name)) {
                        resourceType = value;
                        break;
                    }
                }
                finishRequest(stage, 0, textArea.getText(), resourceType, Integer.parseInt(amount.getText()),
                        ShopMenu.getCurrentGovernment(), receiverGovernment);
            }
        });

        anchorPane.getChildren().addAll(request, donate, cancel, imageView, price, textArea, increase, decrease, amount, wrong);
        stage.show();
    }

    private void finishRequest(Stage stage1, Integer price, String message, ResourceType resourceType, Integer amount,
                               Government requesterGovernment, Government receiverGovernment) {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 300, 300);
        stage.setTitle("Request Confirmation");
        stage.setScene(scene);
        stage.setResizable(false);

        Label question = new Label("Are you sure to send this request?");
        question.setLayoutX(10);
        question.setLayoutY(10);

        Button send = new Button("Send");
        send.setLayoutX(20);
        send.setLayoutY(150);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(130);
        cancel.setLayoutY(150);

        Alert successful = new Alert(Alert.AlertType.INFORMATION);
        successful.setTitle("Congratulations!");
        successful.setHeaderText("Request sent successfully!");
        successful.getButtonTypes().clear();
        successful.getButtonTypes().add(ButtonType.OK);

        Alert failure = new Alert(Alert.AlertType.ERROR);
        failure.setTitle("Failed!");
        failure.setHeaderText("Request didn't send!");
        failure.getButtonTypes().clear();
        failure.getButtonTypes().add(ButtonType.OK);

        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
                stage1.close();
                Trade trade = new Trade(price, message, resourceType, amount,
                        requesterGovernment, receiverGovernment);
                requesterGovernment.addToTradeHistory(trade);
                receiverGovernment.addToTradeList(trade);
                successful.show();
            }
        });

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
                failure.show();
            }
        });

        anchorPane.getChildren().addAll(question, send, cancel);
        stage.show();
    }
}
