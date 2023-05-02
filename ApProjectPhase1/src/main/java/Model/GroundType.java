package Model;

public enum GroundType {
    EARTH("earth", Colors.ANSI_BLACK_BACKGROUND),
    EARTH_WITH_GRAVEL("earth with gravel", Colors.ANSI_BLACK_BACKGROUND),
    SLATE("slate", Colors.ANSI_WHITE_BACKGROUND),
    ROCK("rock", Colors.ANSI_PURPLE_BACKGROUND),
    IRON("iron", Colors.ANSI_RED_BACKGROUND),
    GRASS("grass", Colors.ANSI_YELLOW_BACKGROUND),
    MEADOW("meadow", Colors.ANSI_CYAN_BACKGROUND),
    DENSE_MEADOW("dense meadow", Colors.ANSI_GREEN_BACKGROUND),
    WATER("water", Colors.ANSI_BLUE_BACKGROUND);

    private String name;
    private String color;

    GroundType(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
