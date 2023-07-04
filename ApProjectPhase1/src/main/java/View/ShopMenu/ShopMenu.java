package View.ShopMenu;


import Controller.PreGameController;
import Controller.ShopMenuController;
import Model.*;
import Model.Buildings.BuildingType;
import Model.Buildings.Storage;
import Model.Buildings.StorageType;
import View.TradeMenu.TradeMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu extends Application {

    private static Government currentGovernment;
    private Stage mainStage;

    public static void setCurrentGovernment(Government currentGovernment) {
        ShopMenu.currentGovernment = currentGovernment;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if (ShopMenuCommands.getMatcher(command, ShopMenuCommands.SHOW_PRICE_LIST).matches())
                System.out.print(ShopMenuController.showPriceList());
            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.BUY_ITEM)).matches())
                System.out.println(ShopMenuController.buyItem("dorod", "bikaran"));
            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.SELL_ITEM)).matches())
                System.out.println(ShopMenuController.sellItem("salam", "aleyk"));
            else if (ShopMenuCommands.getMatcher(command, ShopMenuCommands.BACK).matches()) {
                System.out.println("returned to game menu");
                return;
            }
            else
                System.out.println("invalid command");
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Button back = new Button("Back");
        back.setLayoutX(10);
        back.setLayoutY(10);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        Button trade = new Button("Trade");
        trade.setLayoutX(645);
        trade.setLayoutY(10);

        Label header = new Label("Shopping List");
        header.setLayoutX(290);
        header.setLayoutY(40);
        header.setFont(new Font(20));

        Label resourceName = new Label("Resource Name");
        resourceName.setLayoutX(40);
        resourceName.setLayoutY(100);
        resourceName.setFont(new Font(16));

        Label buyPrice = new Label("Buy Price");
        buyPrice.setLayoutX(210);
        buyPrice.setLayoutY(100);
        buyPrice.setFont(new Font(16));

        Label sellPrice = new Label("Sell Price");
        sellPrice.setLayoutX(380);
        sellPrice.setLayoutY(100);
        sellPrice.setFont(new Font(16));

        int counter = 0;
        for (ResourceType value : ResourceType.values()) {
            if (value.getName().equals("meat") || value.getName().equals("apple") || value.getName().equals("bread")
                    || value.getName().equals("cheese")) continue;
            else {
                createResources(value, anchorPane, counter);
                counter++;
            }
        }
        for (FoodType value : FoodType.values()) {
            createFoods(value, anchorPane, counter);
            counter++;
        }

        trade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    stage.setTitle("Trade Menu");
                    new TradeMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


        anchorPane.getChildren().addAll(back, trade, header, resourceName, buyPrice, sellPrice);
        Scene scene = new Scene(anchorPane, 700, 700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Shop Menu");
        mainStage = stage;
        stage.show();
    }

    private void createResources(ResourceType value, AnchorPane anchorPane, int i) {
        Label resourceName = new Label();
        resourceName.setText(value.getName());
        resourceName.setLayoutX(40);
        resourceName.setLayoutY(130 + 30 * i);

        Label buyPrice = new Label();
        buyPrice.setText(value.getBuyPrice().toString());
        buyPrice.setLayoutX(210);
        buyPrice.setLayoutY(130 + 30 * i);

        Label sellPrice = new Label();
        sellPrice.setText(value.getSellPrice().toString());
        sellPrice.setLayoutX(380);
        sellPrice.setLayoutY(130 + 30 * i);



        resourceName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resourceDialog(value);
            }
        });

        anchorPane.getChildren().addAll(resourceName, buyPrice, sellPrice);
    }

    private void createFoods(FoodType value, AnchorPane anchorPane, int i) {
        Label resourceName = new Label();
        resourceName.setText(value.getName());
        resourceName.setLayoutX(40);
        resourceName.setLayoutY(130 + 30 * i);

        Label buyPrice = new Label();
        buyPrice.setText(value.getBuyPrice().toString());
        buyPrice.setLayoutX(210);
        buyPrice.setLayoutY(130 + 30 * i);

        Label sellPrice = new Label();
        sellPrice.setText(value.getSellPrice().toString());
        sellPrice.setLayoutX(380);
        sellPrice.setLayoutY(130 + 30 * i);


        resourceName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                foodDialog(value);
            }
        });

        anchorPane.getChildren().addAll(resourceName, buyPrice, sellPrice);
    }

    private void resourceDialog(ResourceType resourceType) {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 400, 200);
        stage.setScene(scene);

        Button close = new Button("Back");
        close.setLayoutX(10);
        close.setLayoutY(170);

        Button increase = new Button("+");
        increase.setLayoutX(60);
        increase.setLayoutY(50);

        Text amount = new Text("1");
        amount.setX(68);
        amount.setY(102);

        Button decrease = new Button("-");
        decrease.setLayoutX(60);
        decrease.setLayoutY(120);

        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
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

        increase.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Integer newAmount = Integer.parseInt(amount.getText()) + 1;
                amount.setText(newAmount.toString());
            }
        });

        Double goldAmount = currentGovernment.getGold();
        Label gold = new Label(goldAmount.toString());
        gold.setLayoutX(300);
        gold.setLayoutY(55);

        Button buy = new Button("Buy\t" + resourceType.getBuyPrice().toString());
        buy.setLayoutX(150);
        buy.setLayoutY(50);

        Button sell = new Button("Sell\t" + resourceType.getSellPrice().toString());
        sell.setLayoutX(150);
        sell.setLayoutY(120);

        ImageView coin = new ImageView(new Image(getClass().getResource("/coin.png").toExternalForm()));
        coin.setLayoutX(250);
        coin.setLayoutY(40);
        coin.setFitWidth(45);
        coin.setFitHeight(45);

        ImageView storage = new ImageView(new Image(getClass().getResource("/storage.png").toExternalForm()));
        storage.setLayoutX(250);
        storage.setLayoutY(110);
        storage.setFitWidth(45);
        storage.setFitHeight(45);

        Label storageAmount = new Label();
        storageAmount.setLayoutX(300);
        storageAmount.setLayoutY(125);
        if (currentGovernment.getResources().containsKey(resourceType))
            storageAmount.setText(currentGovernment.getResources().get(resourceType).toString());
        else storageAmount.setText("0");

        Alert buySuccess = new Alert(Alert.AlertType.INFORMATION);
        buySuccess.setTitle("Congratulations!");

        Alert buyError = new Alert(Alert.AlertType.ERROR);
        buyError.setTitle("Failed!");


        buy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String result = ShopMenuController.buyItem(resourceType.getName(), amount.getText());
                if (result.equals("bought!")) {
                    buySuccess.setHeaderText("you bought " + amount.getText() + " of " + resourceType.getName() + "!");
                    stage.close();
                    buySuccess.show();
                } else {
                    buyError.setHeaderText(result);
                    stage.close();
                    buyError.show();
                }
            }
        });

        Alert sellSuccess = new Alert(Alert.AlertType.INFORMATION);
        sellSuccess.setTitle("Congratulations!");

        Alert sellError = new Alert(Alert.AlertType.ERROR);
        sellError.setTitle("Failed!");

        sell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String result = ShopMenuController.sellItem(resourceType.getName(), amount.getText());
                if (result.equals("sold!")) {
                    sellSuccess.setHeaderText("you sold " + amount.getText() + " of " + resourceType.getName() + "!");
                    stage.close();
                    sellSuccess.show();
                } else {
                    sellError.setHeaderText(result);
                    stage.close();
                    sellError.show();
                }
            }
        });

        anchorPane.getChildren().addAll(close, increase, decrease, amount, buy, sell, gold, coin, storage, storageAmount);
        stage.setTitle(resourceType.getName());
        stage.setResizable(false);
        stage.show();
    }

    private void foodDialog(FoodType foodType) {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 400, 200);
        stage.setScene(scene);

        Button close = new Button("Back");
        close.setLayoutX(10);
        close.setLayoutY(170);

        Button increase = new Button("+");
        increase.setLayoutX(60);
        increase.setLayoutY(50);

        Text amount = new Text("1");
        amount.setX(68);
        amount.setY(102);

        Button decrease = new Button("-");
        decrease.setLayoutX(60);
        decrease.setLayoutY(120);

        Double goldAmount = currentGovernment.getGold();
        Label gold = new Label(goldAmount.toString());
        gold.setLayoutX(300);
        gold.setLayoutY(55);

        close.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
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

        increase.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Integer newAmount = Integer.parseInt(amount.getText()) + 1;
                amount.setText(newAmount.toString());
            }
        });

        Button buy = new Button("Buy\t" + foodType.getBuyPrice().toString());
        buy.setLayoutX(150);
        buy.setLayoutY(54);

        Button sell = new Button("Sell\t" + foodType.getSellPrice().toString());
        sell.setLayoutX(150);
        sell.setLayoutY(120);

        ImageView coin = new ImageView(new Image(getClass().getResource("/coin.png").toExternalForm()));
        coin.setLayoutX(250);
        coin.setLayoutY(40);
        coin.setFitWidth(45);
        coin.setFitHeight(45);

        ImageView storage = new ImageView(new Image(getClass().getResource("/storage.png").toExternalForm()));
        storage.setLayoutX(250);
        storage.setLayoutY(110);
        storage.setFitWidth(45);
        storage.setFitHeight(45);

        Label storageAmount = new Label();
        storageAmount.setLayoutX(300);
        storageAmount.setLayoutY(125);
        if (currentGovernment.getFoods().containsKey(foodType))
            storageAmount.setText(currentGovernment.getFoods().get(foodType).toString());
        else storageAmount.setText("0");

        Alert buySuccess = new Alert(Alert.AlertType.INFORMATION);
        buySuccess.setTitle("Congratulations!");

        Alert buyError = new Alert(Alert.AlertType.ERROR);
        buyError.setTitle("Failed!");

        buy.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String result = ShopMenuController.buyItem(foodType.getName(), amount.getText());
                if (result.equals("bought!")) {
                    buySuccess.setHeaderText("you bought " + amount.getText() + " of " + foodType.getName() + "!");
                    stage.close();
                    buySuccess.show();
                } else {
                    buyError.setHeaderText(result);
                    stage.close();
                    buyError.show();
                }
            }
        });

        Alert sellSuccess = new Alert(Alert.AlertType.INFORMATION);
        sellSuccess.setTitle("Congratulations!");

        Alert sellError = new Alert(Alert.AlertType.ERROR);
        sellError.setTitle("Failed!");

        sell.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String result = ShopMenuController.sellItem(foodType.getName(), amount.getText());
                if (result.equals("sold!")) {
                    sellSuccess.setHeaderText("you sold " + amount.getText() + " of " + foodType.getName() + "!");
                    stage.close();
                    sellSuccess.show();
                } else {
                    sellError.setHeaderText(result);
                    stage.close();
                    sellError.show();
                }
            }
        });

        anchorPane.getChildren().addAll(close, increase, decrease, amount, buy, sell, gold, coin, storage, storageAmount);
        stage.setTitle(foodType.getName());
        stage.setResizable(false);
        stage.show();
    }

}
