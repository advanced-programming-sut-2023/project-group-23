package Model;

import java.util.ArrayList;

public enum  ResourceType{
    STONE ("stone", 4, 1),
    IRON ("iron", 45, 23),
    WOOD ("wood", 4, 1),
    WHEAT ("wheat", 23, 8),
    FLOUR ("flour", 32, 10),
    HOPS ("hops", 15, 8),
    ALE ("ale", 20, 10),
    PITCH ("pitch", 20, 10),
    HORSE ("horse", 0, 0),
    BOW ("bow", 31, 15),
    SWORD ("sword", 58, 30),
    SPEAR ("spear", 20, 10),
    LEATHER_ARMOR ("leather armor", 25, 12),
    METAL_ARMOR ("metal armor", 58, 30),
    MEAT ("meat", 8, 4),
    APPLE ("apple", 8, 4),
    BREAD ("bread", 8, 4),
    CHEESE ("cheese", 8, 4);

    public String getName() {
        return name;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    private String name;
    private Integer buyPrice;
    private Integer sellPrice;

    public static ArrayList<ResourceType> weapons = new ArrayList<>() {{
        add(HORSE);
        add(BOW);
        add(SWORD);
        add(SPEAR);
        add(LEATHER_ARMOR);
        add(METAL_ARMOR);
    }};

    ResourceType(String name, Integer buyPrice, Integer sellPrice) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }
}
