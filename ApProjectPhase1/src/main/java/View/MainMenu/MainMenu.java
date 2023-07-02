package View.MainMenu;

import Controller.LoginMenuController;
import Controller.MainMenuController;
import Controller.PreGameController;
import Controller.TradeMenuController;
import Model.Game;
import Model.Government;
import Model.Map;
import Model.User;
import View.GameMenu.GameMenu;
import View.LoginMenu.LoginMenu;
import View.PreGameMenu.PreGameMenu;
import View.ProfileMenu.ProfileMenu;
import View.TradeMenu.PreTradeMenu;
import View.TradeMenu.TradeMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
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
                    new PreGameMenu(new Government(User.getUserByUsername("ali"))).run(scanner);
            } else System.out.println("invalid command");
        }
    }

    private static Stage mainStage;

    private static ArrayList<Label> labels;
    private static ArrayList<TextField> textFields;
    public static Integer okPlayers = 0;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
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
                startANewGame();
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

    private static void startANewGame() {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 300);
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.setTitle("Set Players");

        MenuButton playerNumber = new MenuButton("Choose the number of players");
        playerNumber.setLayoutX(10);
        playerNumber.setLayoutY(115);

        for (int i = 1; i < 8; i++) {
            createMenuItemForPlayerNumber(i, playerNumber, anchorPane);
        }

        Label wrongNumber = new Label("");
        wrongNumber.setLayoutX(10);
        wrongNumber.setLayoutY(145);
        wrongNumber.setTextFill(Color.RED);

        Button resume = new Button("Continue");
        resume.setLayoutX(50);
        resume.setLayoutY(265);

        resume.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (playerNumber.getText().equals("Choose the number of players")) wrongNumber.setText("Choose an item!");
                else {
                    wrongNumber.setText("");
                    int counter = 0;
                    for (TextField nickname : textFields) {
                        if (nickname.getText().equals("") || nickname.getText() == null)
                            labels.get(counter).setText("nickname field is empty!");
                        else if (!LoginMenuController.checkNicknameForGame(nickname.getText()).equals("it's ok!"))
                            labels.get(counter).setText(LoginMenuController.checkNicknameForGame(nickname.getText()));
                        else if (nickname.getText().equals(User.getCurrentUser().getNickname()))
                            labels.get(counter).setText("you are already in the game!");
                        else labels.get(counter).setText("");
                        counter++;
                    }
                }
                if (checkResume(playerNumber)) {
                    ArrayList<User> usersInGame = new ArrayList<>();
                    ArrayList<Government> governments = new ArrayList<>();
                    usersInGame.add(User.getCurrentUser());
                    for (TextField textField : textFields) {
                        usersInGame.add(User.getUserByNickname(textField.getText()));
                    }
                    for (User user : usersInGame) {
                        governments.add(new Government(user));
                    }
                    Game game = new Game(governments, new Map());
                    Game.setCurrentGame(game);
                    PreGameMenu.setCurrentGame(game);
                    PreGameController.setCurrentGame(game);
                    ArrayList<Stage> stages = new ArrayList<>();
                    for (Government government : governments) {
                        try {
                            Stage stage = new Stage();
                            stages.add(stage);
                            new PreGameMenu(government).start(stage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    PreGameMenu.stages = stages;
                    mainStage.close();
                }
            }
        });


        Button back = new Button("Back");
        back.setLayoutX(10);
        back.setLayoutY(10);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    mainStage.setTitle("Main Menu");
                    new MainMenu().start(mainStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        anchorPane.getChildren().addAll(back, playerNumber, resume, wrongNumber);
        mainStage.show();
    }

    private static ArrayList<TextField> createFieldsForPlayers(MenuButton menuButton) {
        int size = Integer.parseInt(menuButton.getText());
        ArrayList<TextField> nicknames = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TextField nickname = new TextField();
            nickname.setPromptText("Nickname");
            nickname.setLayoutX(225);
            nickname.setLayoutY(50 + i * 30);
            nicknames.add(nickname);
        }
        return nicknames;
    }

    private static boolean checkResume(MenuButton menuButton) {
        if (menuButton.getText().equals("Choose the number of players")) return false;
        for (Label label : labels) {
            if (!label.getText().equals("")) return false;
        }
        return true;
    }

    private static ArrayList<Label> createLabels(MenuButton menuButton) {
        int size = Integer.parseInt(menuButton.getText());
        ArrayList<Label> wrongNicknames = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Label wrongNickname = new Label("");
            wrongNickname.setLayoutX(390);
            wrongNickname.setLayoutY(53 + 30 * i);
            wrongNickname.setTextFill(Color.RED);
            wrongNicknames.add(wrongNickname);
        }
        return wrongNicknames;
    }

    private static void createMenuItemForPlayerNumber(Integer i, MenuButton menuButton, AnchorPane anchorPane) {
        MenuItem iTh = new MenuItem(i.toString());
        menuButton.getItems().add(iTh);

        iTh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!menuButton.getText().equals("Choose the number of players")) {
                    anchorPane.getChildren().removeAll(textFields);
                    anchorPane.getChildren().removeAll(labels);
                }
                menuButton.setText(iTh.getText());
                textFields = createFieldsForPlayers(menuButton);
                labels = createLabels(menuButton);
                anchorPane.getChildren().addAll(textFields);
                anchorPane.getChildren().addAll(labels);
                createFieldsForPlayers(menuButton);
                createLabels(menuButton);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
