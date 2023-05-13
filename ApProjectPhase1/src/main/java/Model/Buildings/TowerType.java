package Model.Buildings;

public enum TowerType {
    KEEP ("keep", 0, true, true, 0, 0, 8),
    STONE_GATE ("stone gate", 0, true, true, 0, 0, 10);

    private int height;
    private boolean isPassableForEnemy;
    private boolean isPassableForFriendly;
    private int fireRange;
    private int defendRange;
    private int maxPopulationIncrease;
    private String name;

    TowerType(String name, int height, boolean isPassableForEnemy, boolean isPassableForFriendly, int fireRange, int defendRange, int maxPopulationIncrease) {
        this.name = name;
        this.height = height;
        this.isPassableForEnemy = isPassableForEnemy;
        this.isPassableForFriendly = isPassableForFriendly;
        this.fireRange = fireRange;
        this.defendRange = defendRange;
        this.maxPopulationIncrease = maxPopulationIncrease;
    }
}
