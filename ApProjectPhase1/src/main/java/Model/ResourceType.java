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
    CHEESE ("cheese");

    private String name;

    ResourceType(String name) {
        this.name = name;
    }
}
