package Model.People.TempJFX;

import Model.GroundType;
import Model.MapCell;
import Model.People.Troop;
import View.GameJFX.GameJFX;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class Tile extends Polygon {
    public static final double BASE_WIDTH = 66.0;
    public static final double BASE_HEIGHT = 36.0;
    public static double WIDTH = 66.0;
    public static double HEIGHT = 36.0;
    private static double zoomRate = 1.0;
    private final double x;
    private final double y;
    private MapCell cell;
    private static Tile selectedTile = null;
    private static Pane mapPane;
//    private static HashMap<GroundType, ImagePattern> tileImages = new HashMap<>() {{
//        put(GroundType.EARTH, new ImagePattern(
//                new Image(getClass().getResource("/Assets/Tiles/desert_tile.jpg").toExternalForm())));
//    }};
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

        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if(selectedTile != null) {
                    selectedTile.setOpacity(1);
                }
                selectedTile = this;
                this.setOpacity(0.5);
            }
            else if(mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                if(selectedTile != null) {
                    selectedTile.setOpacity(1);
                    selectedTile = null;
                }
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
        this.setFill(groundType.getImage());
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

        Text coordinatesField = new Text("X: " + cell.getX() + "    Y: " + cell.getY());
        statusPane.getChildren().add(coordinatesField);

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
                troopField = new Text(troop.getType().getName() +
                        "   Government: " + troop.getGovernment().getUser().getNickname() +
                        "   HP: " + troop.getHitPoint() +
                        "   Damage: " + troop.getType().getHumanDamage());
                statusPane.getChildren().add(troopField);
            }
        }
    }

    public static void zoom(boolean zoomIn) {
        if(zoomIn) {
            if (zoomRate < 1.5) zoomRate += 0.25;
        }
        else {
                if (zoomRate > 0.5) zoomRate -= 0.25;
        }

        WIDTH = zoomRate * BASE_WIDTH;
        HEIGHT = zoomRate * BASE_HEIGHT;
    }

    public static Tile getSelectedTile() {
        return selectedTile;
    }

    public static void setSelectedTile(Tile selectedTile) {
        Tile.selectedTile = selectedTile;
    }
}
