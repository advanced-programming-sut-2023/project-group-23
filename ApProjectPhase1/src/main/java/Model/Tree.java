package Model;

public enum Tree {
    DESERT_SHRUB("desert shrub"),
    CHERRY_PALM("cherry palm"),
    OLIVE_TREE("olive tree"),
    COCONUT_PALM("coconut palm"),
    DATES_PALM("dates palm");

    private String name;

    Tree(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
