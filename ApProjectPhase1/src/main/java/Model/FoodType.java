package Model;

public enum FoodType {
    MEAT ("meat", 8, 4),
    APPLE ("apple", 8, 4),
    BREAD ("bread", 8, 4),
    CHEESE ("cheese", 8, 4);

    private String name;
    private Integer buyPrice;
    private Integer sellPrice;


    public String getName() {
        return name;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    FoodType(String name, Integer buyPrice, Integer sellPrice) {
        this.name = name;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }
}
