package View.GameJFX;

import Controller.GameMenuController;
import Controller.PreGameController;
import Controller.ShopMenuController;
import Model.Buildings.BuildingType;
import Model.Game;
import Model.Government;
import Model.MapCell;
import Model.Maps;
import Model.People.TempJFX.Tile;
import Model.People.TroopType;
import View.LoginMenu.LoginMenu;
import View.PreGameMenu.PreGameMenu;
import View.ShopMenu.ShopMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameJFX extends Application {

    private static Game currentGame;
    private static Government currentGovernment;
    public static final double HEIGHT = 600;
    public static final double WIDTH = 800;
    private double startX;
    private double startY;
    private double distance;

    private Maps map;

    @Override
    public void start(Stage stage) throws Exception {
        GameJFX.setCurrentGame(Game.getCurrentGame());
        GameMenuController.setCurrentGame(Game.getCurrentGame());
        GameJFX.setCurrentGovernment(currentGame.getGovernments().get(0));
        GameMenuController.setCurrentGovernment(currentGame.getGovernments().get(0));

        Pane gamePane = FXMLLoader.load(new URL(LoginMenu.class.getResource("/View/Game.fxml").toExternalForm()));
        Pane mapPane = new Pane();
        map = GameMenuController.getCurrentGame().getMap();
//        map = new Map();
        Tile.setMapPane(mapPane);
        showMap(mapPane, WIDTH / 2, HEIGHT / 2);
        gamePane.getChildren().add(mapPane);
        gamePane.getChildren().add(Tile.statusPane);

        Button shop = new Button("Shop");
        if (currentGovernment.isCanShop()) {
            shop.setLayoutX(WIDTH - 90);
            shop.setLayoutY(80);
            shop.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ShopMenu.setCurrentGovernment(currentGovernment);
                    ShopMenuController.setCurrentGovernment(currentGovernment);
                    Stage stage1 = new Stage();
                    stage1.setTitle("Shop Menu");
                    stage1.setResizable(false);
                    try {
                        new ShopMenu().start(stage1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            gamePane.getChildren().add(shop);
        }


        Button nextTurnButton = new Button("Next Turn");
        nextTurnButton.setLayoutX(WIDTH - 90);
        nextTurnButton.setLayoutY(40);
        nextTurnButton.setOnMouseClicked(mouseEvent -> {
            try {
                nextTurnHandler(gamePane, shop);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        gamePane.getChildren().add(nextTurnButton);

        gamePane.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.DOWN))   showMap(mapPane, mapPane.getLayoutX(), mapPane.getLayoutY() - 20);
            else if(keyEvent.getCode().equals(KeyCode.RIGHT)) showMap(mapPane, mapPane.getLayoutX() - 20, mapPane.getLayoutY());
            else if(keyEvent.getCode().equals(KeyCode.LEFT)) showMap(mapPane, mapPane.getLayoutX() + 20, mapPane.getLayoutY());
            else if(keyEvent.getCode().equals(KeyCode.UP)) showMap(mapPane, mapPane.getLayoutX(), mapPane.getLayoutY() + 20);
            else if(keyEvent.getCode().equals(KeyCode.EQUALS)) {
                Tile.zoom(true);
                showMap(mapPane, mapPane.getLayoutX(), mapPane.getLayoutY());
            }
            else if(keyEvent.getCode().equals(KeyCode.MINUS)) {
                Tile.zoom(false);
                showMap(mapPane, mapPane.getLayoutX(), mapPane.getLayoutY());
            }
            else if(keyEvent.getCode().equals(KeyCode.M)) {
                if(Tile.getSelectedTile() != null) moveTroop(mapPane, gamePane);
            }
            else if(keyEvent.getCode().equals(KeyCode.A)) {
                if(Tile.getSelectedTile() != null) allAttack(mapPane, gamePane);
            }
            else if(keyEvent.getCode().equals(KeyCode.I)) {
                if(Tile.getSelectedTile() != null) airAttack(mapPane, gamePane);
            }
            else if(keyEvent.getCode().equals(KeyCode.B)) {
                dropBuilding();
            }
            else if(keyEvent.getCode().equals(KeyCode.F)) {
                showFoodMenu();
            }
        });

//        gamePane.setOnMouseClicked(mouseEvent -> {
//            startX = mouseEvent.getSceneX();
//            startY = mouseEvent.getSceneY();
//            //System.out.println(startX + "   " + startY);
//        });
//
//        gamePane.setOnMouseDragged(mouseEvent -> {
//            mapPane.setTranslateX(mouseEvent.getSceneX() - startX);
//            mapPane.setTranslateY(mouseEvent.getSceneY() - startY);
//            //distance = mouseEvent.getSceneX() - startX;
//            //System.out.println(distance);
//            //System.out.println(mapPane.getLayoutX() + "     " + mapPane.getLayoutY());
//            showMap(mapPane, mapPane.getLayoutX() + mouseEvent.getSceneX() - startX, mapPane.getLayoutY() + mouseEvent.getSceneY() - startY);
//        });


        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        gamePane.requestFocus();
        stage.show();
    }

    private void showFoodMenu() {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Food Menu");
        stage.setResizable(false);

        int[] selectedValue = new int[1];

        ArrayList<Text> foodList = GameMenuController.showFoodListJFX();
        for(int i = 0 ; i < foodList.size() ; i++) {
            foodList.get(i).setX(200);
            foodList.get(i).setY(30 * (i + 1));
            anchorPane.getChildren().add(foodList.get(i));
        }

        Text foodRate = new Text("Food Rate: " + currentGovernment.getFoodRate());
        foodRate.setX(200);
        foodRate.setY(150);
        anchorPane.getChildren().add(foodRate);

        MenuButton rateMenu = new MenuButton("Food Rate");
        anchorPane.getChildren().add(rateMenu);
        for(int i = -2 ; i <= 2 ; i++) {
            MenuItem menuItem = new MenuItem(String.valueOf(i));
            rateMenu.getItems().add(menuItem);
            menuItem.setOnAction(actionEvent -> {
                selectedValue[0] = Integer.parseInt(menuItem.getText());
            });
        }

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(10);
        cancel.setLayoutY(560);
        cancel.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        Button okButton = new Button("OK");
        okButton.setLayoutX(550);
        okButton.setLayoutY(560);
        okButton.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Set Food Rate");
            alert.setContentText(GameMenuController.setFoodRateJFX(selectedValue[0]));
            alert.showAndWait();
            stage.close();
        });

        anchorPane.getChildren().add(okButton);

        stage.show();
    }

    private void dropBuilding() {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Drop Building");
        stage.setResizable(false);

        BuildingType[] selectedBuildingType = new BuildingType[1];

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(10);
        cancel.setLayoutY(560);
        cancel.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        TextField xCoordinate = new TextField();
        xCoordinate.setPromptText("X Coordinate");
        xCoordinate.setLayoutX(200);
        xCoordinate.setLayoutY(30);
        anchorPane.getChildren().add(xCoordinate);

        TextField yCoordinate = new TextField();
        yCoordinate.setPromptText("Y Coordinate");
        yCoordinate.setLayoutX(200);
        yCoordinate.setLayoutY(60);
        anchorPane.getChildren().add(yCoordinate);

        Text wrongCoordinate = new Text("");
        wrongCoordinate.setLayoutX(400);
        wrongCoordinate.setLayoutY(65);
        wrongCoordinate.setFill(Color.RED);
        anchorPane.getChildren().add(wrongCoordinate);

        MenuButton buildings = new MenuButton("Building Type");
        buildings.setLayoutX(200);
        buildings.setLayoutY(90);
        anchorPane.getChildren().add(buildings);
        for (BuildingType value : BuildingType.values()) {
            MenuItem building = new MenuItem(value.getName());
            buildings.getItems().add(building);
            building.setOnAction(actionEvent -> {
                selectedBuildingType[0] = BuildingType.getBuildingTypeByName(value.getName());
            });
        }

        Button okButton = new Button("OK");
        okButton.setLayoutX(550);
        okButton.setLayoutY(560);
        okButton.setOnMouseClicked(mouseEvent -> {
            wrongCoordinate.setText("");
            if (!xCoordinate.getText().matches("\\d+")) {
                wrongCoordinate.setText("coordinate must be an integer!");
                return;
            } else if (!yCoordinate.getText().matches("\\d+")) {
                wrongCoordinate.setText("coordinate must be an integer!");
                return;
            }
            int x = Integer.parseInt(xCoordinate.getText());
            int y = Integer.parseInt(yCoordinate.getText());
            MapCell destinationCell = currentGame.getMap().getCellByCoordinate(x, y);

            wrongCoordinate.setText(GameMenuController.dropBuildingJFX(selectedBuildingType[0], destinationCell));
            if(wrongCoordinate.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Dropped Building Successfully!");
                alert.setContentText(selectedBuildingType[0].getName() + " dropped successfully on X: " + x + " Y: " + y);
                alert.showAndWait();
                stage.close();
            }
        });
        anchorPane.getChildren().add(okButton);
        stage.show();
    }

    private void airAttack(Pane mapPane, Pane gamePane) {
        HashMap<TroopType, Integer> troopTypes = GameMenuController.getTroopTypes(Tile.getSelectedTile());
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Air Attack");
        stage.setResizable(false);

        TroopType[] selectedType = new TroopType[1];

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(10);
        cancel.setLayoutY(560);
        cancel.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        anchorPane.getChildren().add(cancel);

        if(troopTypes.size() == 0) {
            Text error = new Text("No Troops Available!");
            error.setX(200);
            error.setY(300);
            anchorPane.getChildren().add(error);
        }
        else {
            TextField xCoordinate = new TextField();
            xCoordinate.setPromptText("X Coordinate");
            xCoordinate.setLayoutX(200);
            xCoordinate.setLayoutY(30);
            anchorPane.getChildren().add(xCoordinate);

            TextField yCoordinate = new TextField();
            yCoordinate.setPromptText("Y Coordinate");
            yCoordinate.setLayoutX(200);
            yCoordinate.setLayoutY(60);
            anchorPane.getChildren().add(yCoordinate);

            TextField amount = new TextField();
            amount.setPromptText("Amount");
            amount.setLayoutX(200);
            amount.setLayoutY(90);
            anchorPane.getChildren().add(amount);

            Text wrongCoordinate = new Text("");
            wrongCoordinate.setLayoutX(400);
            wrongCoordinate.setLayoutY(65);
            wrongCoordinate.setFill(Color.RED);
            anchorPane.getChildren().add(wrongCoordinate);

            MenuButton troopsMenu = new MenuButton("Troop Type");
            troopsMenu.setLayoutX(200);
            troopsMenu.setLayoutY(120);
            anchorPane.getChildren().add(troopsMenu);
            for(Map.Entry<TroopType, Integer> entry : troopTypes.entrySet()) {
                MenuItem menuItem = new MenuItem(entry.getKey().getName());
                menuItem.setOnAction(actionEvent -> {
                    selectedType[0] = TroopType.getTroopTypeByName(entry.getKey().getName());
                });
                troopsMenu.getItems().add(menuItem);
            }

            Button okButton = new Button("OK");
            okButton.setLayoutX(550);
            okButton.setLayoutY(560);
            okButton.setOnMouseClicked(mouseEvent -> {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!amount.getText().matches("\\d+")) {
                    wrongCoordinate.setText("Amount must be an integer!");
                    return;
                }
                int troopAmount = Integer.parseInt(amount.getText());
                int x = Integer.parseInt(xCoordinate.getText());
                int y = Integer.parseInt(yCoordinate.getText());
                MapCell destinationCell = currentGame.getMap().getCellByCoordinate(x, y);

                if(troopAmount > troopTypes.get(selectedType[0]) || troopAmount < 1) {
                    wrongCoordinate.setText("Amount must be between 1 and " + amount.getText());
                    return;
                }
                wrongCoordinate.setText(GameMenuController.airAttackJFX(selectedType[0], troopAmount, destinationCell));
                if(wrongCoordinate.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Attacked Enemy Successfully!");
                    alert.setContentText("Air Attack to X: " + x + " Y: " + y + " with " + selectedType[0].getName());
                    alert.showAndWait();
                    stage.close();
                }
            });
            anchorPane.getChildren().add(okButton);
        }
        stage.show();
    }

    private void allAttack(Pane mapPane, Pane gamePane) {
        HashMap<TroopType, Integer> troopTypes = GameMenuController.getTroopTypes(Tile.getSelectedTile());
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("All Attack");
        stage.setResizable(false);

        TroopType[] selectedType = new TroopType[1];

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(10);
        cancel.setLayoutY(560);
        cancel.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        anchorPane.getChildren().add(cancel);

        if(troopTypes.size() == 0) {
            Text error = new Text("No Troops Available!");
            error.setX(200);
            error.setY(300);
            anchorPane.getChildren().add(error);
        }
        else {
            TextField xCoordinate = new TextField();
            xCoordinate.setPromptText("X Coordinate");
            xCoordinate.setLayoutX(200);
            xCoordinate.setLayoutY(30);
            anchorPane.getChildren().add(xCoordinate);

            TextField yCoordinate = new TextField();
            yCoordinate.setPromptText("Y Coordinate");
            yCoordinate.setLayoutX(200);
            yCoordinate.setLayoutY(60);
            anchorPane.getChildren().add(yCoordinate);

            TextField amount = new TextField();
            amount.setPromptText("Amount");
            amount.setLayoutX(200);
            amount.setLayoutY(90);
            anchorPane.getChildren().add(amount);

            Text wrongCoordinate = new Text("");
            wrongCoordinate.setLayoutX(400);
            wrongCoordinate.setLayoutY(65);
            wrongCoordinate.setFill(Color.RED);
            anchorPane.getChildren().add(wrongCoordinate);

            MenuButton troopsMenu = new MenuButton("Troop Type");
            troopsMenu.setLayoutX(200);
            troopsMenu.setLayoutY(120);
            anchorPane.getChildren().add(troopsMenu);
            for(Map.Entry<TroopType, Integer> entry : troopTypes.entrySet()) {
                MenuItem menuItem = new MenuItem(entry.getKey().getName());
                menuItem.setOnAction(actionEvent -> {
                    selectedType[0] = TroopType.getTroopTypeByName(entry.getKey().getName());
                });
                troopsMenu.getItems().add(menuItem);
            }

            Button okButton = new Button("OK");
            okButton.setLayoutX(550);
            okButton.setLayoutY(560);
            okButton.setOnMouseClicked(mouseEvent -> {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!amount.getText().matches("\\d+")) {
                    wrongCoordinate.setText("Amount must be an integer!");
                    return;
                }
                int troopAmount = Integer.parseInt(amount.getText());
                int x = Integer.parseInt(xCoordinate.getText());
                int y = Integer.parseInt(yCoordinate.getText());
                MapCell destinationCell = currentGame.getMap().getCellByCoordinate(x, y);

                if(troopAmount > troopTypes.get(selectedType[0]) || troopAmount < 1) {
                    wrongCoordinate.setText("Amount must be between 1 and " + amount.getText());
                    return;
                }
                wrongCoordinate.setText(GameMenuController.allAttack(selectedType[0], troopAmount, destinationCell));
                if(wrongCoordinate.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Attacked Enemy Successfully!");
                    String attackType = selectedType[0].getFireRange() > 0 ? "Air Attack" : "Melee Attack";
                    alert.setContentText(attackType + " to X: " + x + " Y: " + y + " with " + selectedType[0].getName());
                    alert.showAndWait();
                    stage.close();
                }
            });
            anchorPane.getChildren().add(okButton);
        }
        stage.show();
    }

    private void moveTroop(Pane mapPane, Pane gamePane) {
        HashMap<TroopType, Integer> troopTypes = GameMenuController.getTroopTypes(Tile.getSelectedTile());
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 600, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Move Troop");
        stage.setResizable(false);

        TroopType[] selectedType = new TroopType[1];

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(10);
        cancel.setLayoutY(560);
        cancel.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        anchorPane.getChildren().add(cancel);

        if(troopTypes.size() == 0) {
            Text error = new Text("No Troops Available!");
            error.setX(200);
            error.setY(300);
            anchorPane.getChildren().add(error);
        }
        else {
            TextField xCoordinate = new TextField();
            xCoordinate.setPromptText("X Coordinate");
            xCoordinate.setLayoutX(200);
            xCoordinate.setLayoutY(30);
            anchorPane.getChildren().add(xCoordinate);

            TextField yCoordinate = new TextField();
            yCoordinate.setPromptText("Y Coordinate");
            yCoordinate.setLayoutX(200);
            yCoordinate.setLayoutY(60);
            anchorPane.getChildren().add(yCoordinate);

            TextField amount = new TextField();
            amount.setPromptText("Amount");
            amount.setLayoutX(200);
            amount.setLayoutY(90);
            anchorPane.getChildren().add(amount);

            Text wrongCoordinate = new Text("");
            wrongCoordinate.setLayoutX(400);
            wrongCoordinate.setLayoutY(65);
            wrongCoordinate.setFill(Color.RED);
            anchorPane.getChildren().add(wrongCoordinate);

            MenuButton troopsMenu = new MenuButton("Troop Type");
            troopsMenu.setLayoutX(200);
            troopsMenu.setLayoutY(120);
            anchorPane.getChildren().add(troopsMenu);
            for(Map.Entry<TroopType, Integer> entry : troopTypes.entrySet()) {
                MenuItem menuItem = new MenuItem(entry.getKey().getName());
                menuItem.setOnAction(actionEvent -> {
                    selectedType[0] = TroopType.getTroopTypeByName(entry.getKey().getName());
                });
                troopsMenu.getItems().add(menuItem);
            }

            Button okButton = new Button("OK");
            okButton.setLayoutX(550);
            okButton.setLayoutY(560);
            okButton.setOnMouseClicked(mouseEvent -> {
                wrongCoordinate.setText("");
                if (!xCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!yCoordinate.getText().matches("\\d+")) {
                    wrongCoordinate.setText("coordinate must be an integer!");
                    return;
                } else if (!amount.getText().matches("\\d+")) {
                    wrongCoordinate.setText("Amount must be an integer!");
                    return;
                }
                int troopAmount = Integer.parseInt(amount.getText());
                int x = Integer.parseInt(xCoordinate.getText());
                int y = Integer.parseInt(yCoordinate.getText());
                MapCell destinationCell = currentGame.getMap().getCellByCoordinate(x, y);

                if(troopAmount > troopTypes.get(selectedType[0]) || troopAmount < 1) {
                    wrongCoordinate.setText("Amount must be between 1 and " + amount.getText());
                    return;
                }
                wrongCoordinate.setText(GameMenuController.moveUnitJFX(selectedType[0], troopAmount, destinationCell));
                if(wrongCoordinate.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Moved Troops Successfully!");
                    alert.setContentText(amount.getText() + " " + selectedType[0].getName() + " moved to x: " + x + " y: " + y);
                    alert.showAndWait();
                    stage.close();
                }
            });
            anchorPane.getChildren().add(okButton);
        }
        stage.show();
    }

    private void nextTurnHandler(Pane gamePane, Button shopButton) throws IOException {
        int nextIndex = currentGame.getGovernments().indexOf(currentGovernment) + 1;
        if(nextIndex == currentGame.getGovernments().size()) {
            GameMenuController.nextTurn();
            if(GameMenuController.isGameOver())
                GameMenuController.endGame();
            else nextIndex = 0;
        }
        GameJFX.setCurrentGovernment(currentGame.getGovernments().get(nextIndex));
        GameMenuController.setCurrentGovernment(currentGovernment);
        ShopMenu.setCurrentGovernment(currentGovernment);
        ShopMenuController.setCurrentGovernment(currentGovernment);
        PreGameController.setCurrentGovernment(currentGovernment);
        gamePane.getChildren().remove(shopButton);
        if(currentGovernment.isCanShop()) gamePane.getChildren().add(shopButton);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(currentGovernment.getUser().getNickname() + " Is Playing Now!");
        alert.showAndWait();
        gamePane.requestFocus();
    }

    private void showMap(Pane mapPane, double X, double Y) {
        mapPane.getChildren().clear();
        mapPane.setPrefWidth(200 * Tile.WIDTH);
        mapPane.setPrefHeight(200 * Tile.HEIGHT);
        mapPane.setLayoutX(X);
        mapPane.setLayoutY(Y);
        
        Tile tile;
        double x, y;
        for(int k = 198 - (int) (2 * mapPane.getLayoutY() / Tile.HEIGHT) ; k < 200 ; k++) {
            y = (k - 199) * (Tile.HEIGHT / 2);
            if (k % 2 == 0)
                for (int i = (int) -Math.min(k / 2, mapPane.getLayoutX() / Tile.WIDTH + 0.5); i <= (int) Math.min(k / 2, (WIDTH - mapPane.getLayoutX()) / Tile.WIDTH + 0.5) ; i++) {
                    x = i * Tile.WIDTH;
                    if(Tile.getSelectedTile() != null &&
                            (int) Tile.getSelectedTile().getX() == (int) x &&
                            (int) Tile.getSelectedTile().getY() == (int) y) {
                        mapPane.getChildren().add(Tile.getSelectedTile());
                        continue;
                    }
                    tile = new Tile(x, y, map.getMap()[k / 2 - i][k / 2 + i]);
                    mapPane.getChildren().add(tile);
                }
            else
                for (int i = (int) -Math.min((k + 1) / 2, mapPane.getLayoutX() / Tile.WIDTH + 1); i <= (int) Math.min(k / 2, (WIDTH - mapPane.getLayoutX()) / Tile.WIDTH); i++) {
                    x = (1 + 2 * i) * Tile.WIDTH / 2;
                    if(Tile.getSelectedTile() != null &&
                            (int) Tile.getSelectedTile().getX() == (int) x &&
                            (int) Tile.getSelectedTile().getY() == (int) y) {
                        mapPane.getChildren().add(Tile.getSelectedTile());
                        continue;
                    }
                    tile = new Tile(x, y, map.getMap()[(k - 1) / 2 - i][(k + 1) / 2 + i]);
                    mapPane.getChildren().add(tile);
                }
        }
        for(int k = 198 + (int) (2 * (mapPane.getLayoutY() - HEIGHT) / Tile.HEIGHT) ; k < 199 ; k++) {
            y = (199 - k) * (Tile.HEIGHT / 2);
            if (k % 2 == 0)
                for (int i = (int) -Math.min(k / 2, mapPane.getLayoutX() / Tile.WIDTH + 0.5); i <= (int) Math.min(k / 2, (WIDTH - mapPane.getLayoutX()) / Tile.WIDTH + 0.5) ; i++) {
                    x = i * Tile.WIDTH;
                    if(Tile.getSelectedTile() != null &&
                            (int) Tile.getSelectedTile().getX() == (int) x &&
                            (int) Tile.getSelectedTile().getY() == (int) y) {
                        mapPane.getChildren().add(Tile.getSelectedTile());
                        continue;
                    }
                    tile = new Tile(x, y, map.getMap()[199 - k / 2 - i][199 - k / 2 + i]);
                    mapPane.getChildren().add(tile);
                }
            else
                for (int i = (int) -Math.min((k + 1) / 2, mapPane.getLayoutX() / Tile.WIDTH + 1); i <= (int) Math.min(k / 2, (WIDTH - mapPane.getLayoutX()) / Tile.WIDTH); i++) {
                    x = (1 + 2 * i) * Tile.WIDTH / 2;
                    if(Tile.getSelectedTile() != null &&
                            (int) Tile.getSelectedTile().getX() == (int) x &&
                            (int) Tile.getSelectedTile().getY() == (int) y) {
                        mapPane.getChildren().add(Tile.getSelectedTile());
                        continue;
                    }
                    tile = new Tile(x, y, map.getMap()[199 - (k + 1) / 2 - i][199 - (k - 1) / 2 + i]);
                    mapPane.getChildren().add(tile);
                }
        }
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        GameJFX.currentGame = currentGame;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        GameJFX.currentGovernment = currentGovernment;
    }
}
