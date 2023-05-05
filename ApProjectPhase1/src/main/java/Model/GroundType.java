package Model;

public enum GroundType {
    EARTH("earth", Colors.BLACK),
    EARTH_WITH_GRAVEL("earth with gravel", Colors.BLACK),
    SLATE("slate", Colors.WHITE),
    ROCK("rock", Colors.PURPLE),
    IRON("iron", Colors.RED),
    GRASS("grass", Colors.YELLOW),
    MEADOW("meadow", Colors.CYAN),
    DENSE_MEADOW("dense meadow", Colors.GREEN),
    WATER("water", Colors.BLUE);

    private String name;
    private Colors color;

    GroundType(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Colors getColor() {
        return color;
    }
}
