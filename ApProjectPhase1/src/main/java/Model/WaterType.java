package Model;

public enum WaterType {
    OIL("oil"),
    PLAIN("plain"),
    SHALLOW_WATER("shallow water"),
    RIVER("river"),
    SMALL_POND("small pond"),
    BIG_POND("big pond"),
    BEACH("beach"),
    SEA("sea");

    private String name;

    WaterType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
