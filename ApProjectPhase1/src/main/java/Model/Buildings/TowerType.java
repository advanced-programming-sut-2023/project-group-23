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

    public String getName() {
        return name;
    }

    public static TowerType getTowerTypeByName(String name) {
        for(TowerType towerType : TowerType.values())
            if(towerType.getName().equals(name))
                return towerType;

        return null;
    }
}
