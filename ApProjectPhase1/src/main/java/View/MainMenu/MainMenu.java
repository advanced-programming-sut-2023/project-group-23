package View.MainMenu;

import Controller.MainMenuController;
import Controller.TradeMenuController;
import View.GameMenu.GameMenu;
import View.LoginMenu.LoginMenu;
import View.PreGameMenu.PreGameMenu;
import View.ProfileMenu.ProfileMenu;
import View.TradeMenu.PreTradeMenu;
import View.TradeMenu.TradeMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Application {

    public static void run(Scanner scanner) throws IOException {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();
            if (MainMenuCommands.getMatcher(command, MainMenuCommands.LOGOUT).matches()) {
                System.out.println("you logged out");
                return;
            }
            else if (MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_PROFILE_MENU).matches()) {
                System.out.println("you entered profile menu");
                ProfileMenu.run(scanner);
            }
            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_NEW_GAME)).matches()) {
                String result = MainMenuController.startNewGame(matcher.group("content"));
                System.out.println(result);
                if(result.equals("set your game settings"))
                    PreGameMenu.run(scanner);
            } else System.out.println("invalid command");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        BackgroundSize backgroundSize1 = new BackgroundSize(600, 600, false, false, false, false);
        BackgroundImage backgroundImage1 = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/11.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize1);
        Background background1 = new Background(backgroundImage1);
        anchorPane.setBackground(background1);
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);

        Button logout = new Button("Log Out");
        logout.setLayoutX(10);
        logout.setLayoutY(10);

        Button startGame = new Button("Start a new game");
        startGame.setLayoutX(210);
        startGame.setLayoutY(210);
        startGame.setPrefWidth(200);
        startGame.setPrefHeight(50);
        startGame.setFont(new Font(20));

        Button profileMenu = new Button("Profile");
        profileMenu.setFont(new Font(20));
        profileMenu.setLayoutX(210);
        profileMenu.setLayoutY(320);
        profileMenu.setPrefWidth(200);
        profileMenu.setPrefHeight(50);

        logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    stage.setTitle("Login Menu");
                    new LoginMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO: start a new Game
            }
        });

        profileMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    stage.setTitle("Profile Menu");
                    new ProfileMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        anchorPane.getChildren().addAll(logout, startGame, profileMenu);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
