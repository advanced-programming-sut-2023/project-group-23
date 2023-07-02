package View.GameJFX;

import Controller.GameMenuController;
import Model.Map;
import Model.MapCell;
import Model.People.TempJFX.Tile;
import View.LoginMenu.LoginMenu;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class GameJFX extends Application {

    public static final double HEIGHT = 600;
    public static final double WIDTH = 800;
    private double startX;
    private double startY;
    private double distance;

    private Map map;

    @Override
    public void start(Stage stage) throws Exception {
        Pane gamePane = FXMLLoader.load(new URL(LoginMenu.class.getResource("/View/Game.fxml").toExternalForm()));
        Pane mapPane = new Pane();
        map = GameMenuController.getCurrentGame().getMap();
        //map.buildMap(mapPane, gamePane);
        Tile.setMapPane(mapPane);
        showMap(mapPane, WIDTH / 2, HEIGHT / 2);
        gamePane.getChildren().add(mapPane);
        gamePane.getChildren().add(Tile.statusPane);

        gamePane.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.DOWN))   showMap(mapPane, mapPane.getLayoutX(), mapPane.getLayoutY() - 10);
            else if(keyEvent.getCode().equals(KeyCode.RIGHT)) showMap(mapPane, mapPane.getLayoutX() - 10, mapPane.getLayoutY());
            else if(keyEvent.getCode().equals(KeyCode.LEFT)) showMap(mapPane, mapPane.getLayoutX() + 10, mapPane.getLayoutY());
            else if(keyEvent.getCode().equals(KeyCode.UP)) showMap(mapPane, mapPane.getLayoutX(), mapPane.getLayoutY() + 10);
            else if(keyEvent.getCode().equals(KeyCode.EQUALS)) {
                Tile.zoom(true);
                showMap(mapPane, mapPane.getLayoutX(), mapPane.getLayoutY());
            }
            else if(keyEvent.getCode().equals(KeyCode.MINUS)) {
                Tile.zoom(false);
                showMap(mapPane, mapPane.getLayoutX(), mapPane.getLayoutY());
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

        //System.out.println("ok");
        Scene scene = new Scene(gamePane);
        stage.setScene(scene);
        gamePane.requestFocus();
        stage.show();
    }

//    private void setupMap(Pane mapPane, double X, double Y) {
//        mapPane.getChildren().clear();
//        mapPane.setPrefWidth(200 * Tile.WIDTH);
//        mapPane.setPrefHeight(200 * Tile.HEIGHT);
//        mapPane.setLayoutX(X);
//        mapPane.setLayoutY(Y);
//        ImagePattern defaultTileImage = new ImagePattern(
//                new Image(getClass().getResource("/Assets/Tiles/desert_tile.jpg").toExternalForm()));
//        Tile tile;
//        double x, y;
//        for(int k = 0 ; k < 200 ; k++) {
//            y = (k - 199) * (Tile.HEIGHT / 2);
//            if(y + mapPane.getLayoutY() + Tile.HEIGHT / 2 < 0) continue;
//
//            if (k % 2 == 0)
//                for (int i = -k / 2; i <= k / 2; i++) {
//                    x = i * Tile.WIDTH;
//                    if(x + mapPane.getLayoutX() - Tile.WIDTH / 2 < 800 &&
//                        x + mapPane.getLayoutX() + Tile.WIDTH / 2 > 0) {
//                        tile = new Tile(x, y);
//                        tile.setFill(defaultTileImage);
//                        mapPane.getChildren().add(tile);
//                    }
//                }
//            else
//                for (int i = -(k + 1) / 2; i <= k / 2; i++) {
//                    x = (1 + 2 * i) * Tile.WIDTH / 2;
//                    if(x + mapPane.getLayoutX() - Tile.WIDTH / 2 < 800 &&
//                        x + mapPane.getLayoutX() + Tile.WIDTH / 2 > 0) {
//                        tile = new Tile(x, y);
//                        tile.setFill(defaultTileImage);
//                        mapPane.getChildren().add(tile);
//                    }
//                }
//        }
//        for(int k = 0 ; k < 199 ; k++) {
//            y = (199 - k) * (Tile.HEIGHT / 2);
//            if(y + mapPane.getLayoutY() - Tile.HEIGHT / 2 > 600) continue;
//
//            if (k % 2 == 0)
//                for (int i = -k / 2; i <= k / 2; i++) {
//                    x = i * Tile.WIDTH;
//                    if(x + mapPane.getLayoutX() - Tile.WIDTH / 2 < 800 &&
//                            x + mapPane.getLayoutX() + Tile.WIDTH / 2 > 0) {
//                        tile = new Tile(x, y);
//                        tile.setFill(defaultTileImage);
//                        mapPane.getChildren().add(tile);
//                    }
//                }
//            else
//                for (int i = -(k + 1) / 2; i <= k / 2; i++) {
//                    x = (1 + 2 * i) * Tile.WIDTH / 2;
//                    if(x + mapPane.getLayoutX() - Tile.WIDTH / 2 < 800 &&
//                            x + mapPane.getLayoutX() + Tile.WIDTH / 2 > 0) {
//                        tile = new Tile(x, y);
//                        tile.setFill(defaultTileImage);
//                        mapPane.getChildren().add(tile);
//                    }
//                }
//        }
//    }

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
}
