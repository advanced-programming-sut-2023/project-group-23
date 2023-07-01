package Model.People.TempJFX;

import Model.GroundType;
import Model.MapCell;
import Model.People.Troop;
import View.GameJFX.GameJFX;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.HashMap;

public class Tile extends Polygon {
    public static final double WIDTH = 44.0 * 1.5;
    public static final double HEIGHT = 24.0 * 1.5;
    private final double x;
    private final double y;
    private MapCell cell;
    private static Pane mapPane;
    private static HashMap<GroundType, String> tileImages = new HashMap<>() {{
        put(GroundType.EARTH, "/Assets/Tiles/desert_tile.jpg");
    }};
    public static VBox statusPane;

    static {
        statusPane = new VBox();
        statusPane.setPrefHeight(GameJFX.HEIGHT / 2);
        statusPane.setPrefWidth(GameJFX.WIDTH / 2);
        statusPane.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        statusPane.setVisible(false);
    }

    public Tile(double x, double y, MapCell cell) {
        this.x = x;
        this.y = y;
        this.getPoints().addAll(x - WIDTH / 2, y, x, y - HEIGHT / 2, x + WIDTH / 2, y, x, y + HEIGHT / 2);
        this.cell = cell;
        this.setTileImage(cell.getGroundType());
        this.hoverProperty().addListener((observableValue, aBoolean, t1) -> {
            if(t1) {
                double X = x + mapPane.getLayoutX();
                double Y = y + mapPane.getLayoutY();

                if(Y <= (GameJFX.HEIGHT - 50) / 2) statusPane.setLayoutY(Y + Tile.HEIGHT / 2);
                else  statusPane.setLayoutY(Y - (GameJFX.HEIGHT - 50) / 2 - Tile.HEIGHT);
                if(X < GameJFX.WIDTH / 2) statusPane.setLayoutX(X);
                else statusPane.setLayoutX(X - GameJFX.WIDTH / 2);

                setStatusPaneContent(cell);

                statusPane.setVisible(true);
            }
            else {
                statusPane.getChildren().clear();
                statusPane.setVisible(false);
            }
        });
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setTileImage(GroundType groundType) {
        this.setFill(new ImagePattern(
                new Image(getClass().getResource(tileImages.get(groundType)).toExternalForm())));
    }

    public MapCell getCell() {
        return cell;
    }

    public void setCell(MapCell cell) {
        this.cell = cell;
    }

    public static Pane getMapPane() {
        return mapPane;
    }

    public static void setMapPane(Pane mapPane) {
        Tile.mapPane = mapPane;
    }

    public void setStatusPaneContent(MapCell cell) {
        Text groundTypeField = new Text("Ground Type: " + cell.getGroundType().getName());
        statusPane.getChildren().add(groundTypeField);

        if(cell.getTree() != null) {
            Text treeField = new Text("Tree: " + cell.getTree().getName());
            statusPane.getChildren().add(treeField);
        }

        if(cell.getRock() != null) {
            Text rockField = new Text("Rock     Direction: " + cell.getRock().getDirection().getName());
            statusPane.getChildren().add(rockField);
        }

        if(cell.getBuilding() != null) {
            Text buildingField = new Text("Building: " + cell.getBuilding().getName() +
                    "   Government: " + cell.getBuilding().getGovernment().getUser().getNickname());
            statusPane.getChildren().add(buildingField);
        }

        if(cell.getTroops().size() > 0) {
            Text troopsTitle = new Text("Troops:");
            statusPane.getChildren().add(troopsTitle);
            Text troopField;
            for (Troop troop : cell.getTroops()) {
                troopField = new Text(troop.getName() +
                        "   Government: " + troop.getGovernment().getUser().getNickname());
                statusPane.getChildren().add(troopField);
            }
        }
    }
}
