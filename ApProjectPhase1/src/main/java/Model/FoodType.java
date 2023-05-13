package Model;

public enum FoodType {
    MEAT ("meat"),
    APPLE ("apple"),
    BREAD ("bread"),
    CHEESE ("cheese");

    private String name;

    FoodType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
