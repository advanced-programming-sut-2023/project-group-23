package Model.Buildings;

public enum TowerType {
    ;

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
