package View.PreGameMenu;

import Controller.PreGameController;
import Model.Buildings.Building;
import Model.Buildings.BuildingType;
import Model.Colors;
import Model.Game;
import Model.Government;
import Model.People.Lord;
import Model.People.Troop;
import Model.People.TroopType;
import Model.User;
import View.GameMenu.GameMenu;
import View.MainMenu.MainMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class PreGameMenu extends Application {
    private PreGameMenu preGameMenu;
    private static Game currentGame;
    public static ArrayList<Stage> stages;

    private Government currentGovernment;

    public PreGameMenu(Government government) {
        this.preGameMenu = this;
        this.currentGovernment = government;
    }

    public Government getCurrentGovernment() {
        return currentGovernment;
    }

    public void run(Scanner scanner) throws IOException {
        PreGameMenu.setCurrentGame(Game.getCurrentGame());
        PreGameController.setCurrentGame(Game.getCurrentGame());

        String command;
        Matcher matcher;

        for(Government government : currentGame.getGovernments()) {
            PreGameController.setCurrentGovernment(government);
            System.out.println(government.getUser().getNickname() + " is editing game settings");

            if(pickGovernmentColor(scanner).equals("back")) {
                System.out.println("back to main menu");
                return;
            }

            if(placeKeep(scanner).equals("back")) {
                System.out.println("back to main menu");
                return;
            }

            int keepXCoordinate = 0;
            int keepYCoordinate = 0;
            for(Building building : government.getBuildings())
                if(building.getType().equals(BuildingType.KEEP)) {
                    keepXCoordinate = building.getxCoordinate();
                    keepYCoordinate = building.getyCoordinate();
                    break;
                }
            Lord lord = new Lord(government, TroopType.LORD, keepXCoordinate, keepYCoordinate);
            currentGame.getMap().getCellByCoordinate(keepXCoordinate, keepYCoordinate).addToTroops(lord);
            government.setLord(lord);

            while (true) {
                command = scanner.nextLine();

                if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.SET_TEXTURE_SINGLE)).matches())
                    System.out.println(PreGameController.setTextureSingle(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.SET_TEXTURE_ZONE)).matches())
                    System.out.println(PreGameController.setTextureZone(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.CLEAR)).matches())
                    System.out.println(PreGameController.clear(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.DROPROCK)).matches())
                    System.out.println(PreGameController.droprock(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.DROPTREE)).matches())
                    System.out.println(PreGameController.droptree(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.DROPBUILDING)).matches())
                    System.out.println(PreGameController.dropbuilding(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.DROPUNIT)).matches())
                    System.out.println(PreGameController.dropunit(matcher));

                else if(PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.DONE).matches())
                    break;

                else if(PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.BACK).matches()) {
                    System.out.println("back to main menu");
                    return;
                }

                else
                    System.out.println("invalid command");
            }
        }

        GameMenu.run(scanner);
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        PreGameMenu.currentGame = currentGame;
    }

    public String pickGovernmentColor(Scanner scanner) {
        Matcher matcher;
        String command;
        while (true) {
            System.out.println("choose one of these colors as your government color:");
            for (Colors color : Colors.values()) {
                if (!currentGame.getUsedGovernmentColors().contains(color) && !color.equals(Colors.RESET))
                    System.out.println(color.getName());
            }
            command = scanner.nextLine();
            if ((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.TEXT_INPUT)).matches()) {
                String inputColor = matcher.group("content").replace("\"", "");
                String result = PreGameController.pickGovernmentColor(inputColor, preGameMenu.getCurrentGovernment(), 1);
                System.out.println(result);
                if(!result.equals("invalid color"))
                    return "ok";
            }
            else if(PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.BACK).matches())
                return "back";
            else
                System.out.println("invalid command");
        }
    }

    public static String placeKeep(Scanner scanner) {
        Matcher matcher;
        String command;
        while (true) {
            System.out.println("enter coordinates of your keep:");
            command = scanner.nextLine();
            if ((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.COORDINATE_INPUT)).matches()) {
                String result = PreGameController.placeKeep("salam", "aleyk", 1);
                if (result.equals("placed building successfully"))
                    return "ok";
            } else if (PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.BACK).matches())
                return "back";
            else
                System.out.println("invalid command");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setResizable(false);
        stage.setTitle("initialize " + currentGovernment.getUser().getNickname() + " map");
        stage.setScene(scene);

        MenuButton selectColor = new MenuButton("Color");
        selectColor.setLayoutX(150);
        selectColor.setLayoutY(170);
        for (Colors value : Colors.values()) {
            if (value.getName().equals("reset")) continue;
            MenuItem menuItem = new MenuItem(value.getName());
            selectColor.getItems().add(menuItem);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    selectColor.setText(menuItem.getText());
                }
            });
        }

        Text wrongColor = new Text("");
        wrongColor.setLayoutX(150);
        wrongColor.setLayoutY(210);
        wrongColor.setFill(Color.RED);

        TextField keepXCoordinate = new TextField();
        keepXCoordinate.setPromptText("Keep X coordinate");
        keepXCoordinate.setLayoutX(50);
        keepXCoordinate.setLayoutY(80);

        TextField keepYCoordinate = new TextField();
        keepYCoordinate.setPromptText("Keep Y coordinate");
        keepYCoordinate.setLayoutX(50);
        keepYCoordinate.setLayoutY(120);

        Text wrongPlace = new Text();
        wrongPlace.setLayoutX(200);
        wrongPlace.setLayoutY(115);
        wrongPlace.setFill(Color.RED);


        Button cancelGame = new Button("Cancel this game");
        cancelGame.setLayoutX(10);
        cancelGame.setLayoutY(10);

        Button show = new Button("Go to game");
        show.setLayoutX(10);
        show.setLayoutY(350);
        show.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongPlace.setText("");
                wrongColor.setText("");
                PreGameController.setCurrentGovernment(preGameMenu.getCurrentGovernment());
                int flag = 0;
                if (!PreGameController.isColorUsed(selectColor.getText()) && !selectColor.getText().equals("Color"))
                    flag = 1;
                String putKeep = PreGameController.placeKeep(keepXCoordinate.getText(), keepYCoordinate.getText(), flag);
                String color;
                if (putKeep.equals("ok")) color = isColorOk(selectColor.getText(), 1);
                else color = isColorOk(selectColor.getText(), 0);
                if (!putKeep.equals("ok")) wrongPlace.setText(putKeep);
                if (!color.equals("ok")) wrongColor.setText(color);
                if (color.equals("ok") && putKeep.equals("ok")) {
                    stage.close();
                    if (MainMenu.okPlayers == stages.size() - 1) {
                        //TODO:start the game
                    } else {
                        MainMenu.okPlayers++;
                    }
                }
            }
        });

        cancelGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Stage stage1 : stages) {
                    stage1.close();
                }
                try {
                    Stage stage1 = new Stage();
                    stage1.setTitle("Main Menu");
                    new MainMenu().start(stage1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        anchorPane.getChildren().addAll(cancelGame, selectColor, show, keepXCoordinate, keepYCoordinate, wrongPlace
        , wrongColor);
        stage.show();
    }

    private String isColorOk(String inputColor, int i) {
        if (inputColor.equals("Color")) return "you must choose a color!";
        return PreGameController.pickGovernmentColor(inputColor, preGameMenu.getCurrentGovernment(), i);
    }
}
