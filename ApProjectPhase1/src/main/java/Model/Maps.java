package Model;

import Model.People.TempJFX.Tile;
import Model.People.Troop;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Maps {
    private MapCell[][] map;
    private int width;
    private int height;
    private int currentX;
    private int currentY;

    public Maps() {
        this.width = 200;
        this.height = 200;
        this.map = new MapCell[height][width];

        for(int i = 0 ; i < height ; i++)
            for(int j = 0 ; j < width; j++)
                map[i][j] = new MapCell(GroundType.EARTH, i, j);
    }

    private VBox buildStatusPane(Pane mapPane, Pane gamePane, Tile tile) {
        VBox statusPane = new VBox();
        statusPane.setPrefHeight(300);
        statusPane.setPrefWidth(400);

        MapCell cell = tile.getCell();

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

        return statusPane;
    }

//    public void buildMap(Pane mapPane, Pane gamePane) {
//        Tile tile;
//
//        for(int i = 0 ; i < 200 ; i++) {
//            for (int j = 0 ; j < 200 ; j++) {
//                tile = new Tile((j - i) * (Tile.WIDTH / 2), (i + j - 199) * (Tile.HEIGHT / 2));
//                tile.setTileImage(map[i][j].getGroundType());
//                System.out.println("ok");
//                Tile finalTile = tile;
//                tile.hoverProperty().addListener(hoverHandler -> {
//                    double x = finalTile.getX() + mapPane.getLayoutX();
//                    double y = finalTile.getY() + mapPane.getLayoutY();
//                    VBox statusPane = buildStatusPane(mapPane, gamePane, finalTile);
//
//                    if(y <= 250) statusPane.setLayoutY(y);
//                    else if(y > 250 && y <= 500) statusPane.setLayoutY(y - 250);
//                    if(x < 400) statusPane.setLayoutX(x);
//                    else statusPane.setLayoutX(x - 400);
//
//                    gamePane.getChildren().add(statusPane);
//                });
//                tile.setCell(map[i][j]);
//                map[i][j].setTile(tile);
//            }
//        }
//    }

    public MapCell getCellByCoordinate(int x, int y) {
        return map[x][y];
    }

    public MapCell[][] getMap() {
        return map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }
}
