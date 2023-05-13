package Model;

import java.util.ArrayList;

public enum  ResourceType {
    STONE ("stone"),
    IRON ("iron"),
    WOOD ("wood"),
    WHEAT ("wheat"),
    FLOUR ("flour"),
    HOPS ("hops"),
    ALE ("ale"),
    PITCH ("pitch"),
    MEAT ("meat"),
    APPLE ("apple"),
    BREAD ("bread"),
    CHEESE ("cheese"),
    HORSE ("horse"),
    BOW ("bow"),
    SWORD ("sword"),
    SPEAR ("spear"),
    LEATHER_ARMOR ("leather armor"),
    METAL_ARMOR ("metal armor");

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

    ResourceType(String name) {
        this.name = name;
    }

}
