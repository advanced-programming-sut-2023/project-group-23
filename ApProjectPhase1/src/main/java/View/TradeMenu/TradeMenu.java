package View.TradeMenu;

import Controller.PreGameController;
import Controller.TradeMenuController;
import View.MapMenu.MapMenuCommands;
import View.ShopMenu.ShopMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu extends Application {
    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE)).matches())
                System.out.println(TradeMenuController.trade(matcher.group("content")));
            else if (MapMenuCommands.getMatcher(command, MapMenuCommands.BACK).matches())
                return;
            else System.out.println("Invalid command!");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Trade Menu");

        Button back = new Button("Back");
        back.setLayoutX(10);
        back.setLayoutY(10);

        Button request = new Button("Request a new trade");
        request.setLayoutX(150);
        request.setLayoutY(200);
        request.setPrefWidth(250);
        request.setPrefHeight(20);
        request.setFont(new Font(20));

        Button tradeHistory = new Button("Trade history");
        tradeHistory.setLayoutX(150);
        tradeHistory.setLayoutY(300);
        tradeHistory.setPrefWidth(250);
        tradeHistory.setPrefHeight(20);
        tradeHistory.setFont(new Font(20));

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    stage.setTitle("Shop Menu");
                    new ShopMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        request.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setTitle("Request a trade");
                try {
                    new Request().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tradeHistory.setOnMouseClicked(new EventHandler<MouseEvent>() {
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

        anchorPane.getChildren().addAll(back, request, tradeHistory);
        stage.show();
    }
}