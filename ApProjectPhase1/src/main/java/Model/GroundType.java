package Model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public enum GroundType {
    EARTH("earth", Colors.BLACK, "/Assets/Tiles/earth.png"),
    EARTH_WITH_GRAVEL("earth with gravel", Colors.BLACK, "/Assets/Tiles/earth-with-gravel.png"),
    SLATE("slate", Colors.WHITE, "/Assets/Tiles/slate.png"),
    ROCK("rock", Colors.PURPLE, "/Assets/Tiles/rock.png"),
    IRON("iron", Colors.RED, "/Assets/Tiles/iron.png"),
    GRASS("grass", Colors.YELLOW, "/Assets/Tiles/grass.png"),
    MEADOW("meadow", Colors.CYAN, "/Assets/Tiles/meadow.png"),
    DENSE_MEADOW("dense meadow", Colors.GREEN, "/Assets/Tiles/dense-meadow.png"),
    WATER("water", Colors.BLUE, "/Assets/Tiles/sea_tile.png");

    private String name;
    private Colors color;
    private ImagePattern image;

    GroundType(String name, Colors color, String address) {
        this.name = name;
        this.color = color;
        this.image = new ImagePattern(
                new Image(getClass().getResource(address).toExternalForm()));
    }

    public String getName() {
        return name;
    }

    public Colors getColor() {
        return color;
    }

    public ImagePattern getImage() {
        return image;
    }
}
