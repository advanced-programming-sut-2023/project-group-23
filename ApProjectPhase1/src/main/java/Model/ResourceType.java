package Model;

public enum ResourceType {
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

    private String name;

    ResourceType(String name) {
        this.name = name;
    }
}
