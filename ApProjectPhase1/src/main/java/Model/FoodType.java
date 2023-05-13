package Model;

public enum FoodType {
    MEAT ("meat"),
    APPLE ("apple"),
    BREAD ("bread"),
    CHEESE ("cheese");

    private String name;
    private Integer sellPrice;
    private Integer buyPrice;

    FoodType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }
}
