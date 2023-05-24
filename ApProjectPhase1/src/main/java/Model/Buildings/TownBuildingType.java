package Model.Buildings;

public enum TownBuildingType {
    KEEP ("keep", 8, 0, false),
    STONE_GATE ("stone gate", 10, 0, false),
    HOVEL ("hovel", 8, 0, false),
    INN ("inn", 0, 2, false),
    CHURCH ("church", 0, 2, true),
    CATHEDRAL ("cathedral", 0, 2, true),
    SHOP ("shop", 0, 0, false);

    private String name;
    private int maxPopulationIncrease;
    private int popularityRate;
    private boolean isReligious;

    TownBuildingType(String name, int maxPopulationIncrease, int popularityRate, boolean isReligious) {
        this.name = name;
        this.maxPopulationIncrease = maxPopulationIncrease;
        this.popularityRate = popularityRate;
        this.isReligious = isReligious;
    }

    public String getName() {
        return name;
    }

    public int getMaxPopulationIncrease() {
        return maxPopulationIncrease;
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public boolean isReligious() {
        return isReligious;
    }

    public static TownBuildingType getTownBuildingTypeByName(String name) {
        for(TownBuildingType townBuildingType : TownBuildingType.values())
            if(townBuildingType.getName().equals(name))
                return townBuildingType;

        return null;
    }
}
