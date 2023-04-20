package Model;

public class Tower extends Building{
    private int height;
    private boolean isPassableForEnemy;
    private boolean isPassableForFriendly;
    private int fireRange;
    private int defendRange;
    private int maxPopulationIncrease;
    private TowerType towerType;

    public Tower(BuildingType type, TowerType towerType, Government government) {
        super(type, government);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isPassableForEnemy() {
        return isPassableForEnemy;
    }

    public void setPassableForEnemy(boolean passableForEnemy) {
        isPassableForEnemy = passableForEnemy;
    }

    public boolean isPassableForFriendly() {
        return isPassableForFriendly;
    }

    public void setPassableForFriendly(boolean passableForFriendly) {
        isPassableForFriendly = passableForFriendly;
    }

    public int getFireRange() {
        return fireRange;
    }

    public void setFireRange(int fireRange) {
        this.fireRange = fireRange;
    }

    public int getDefendRange() {
        return defendRange;
    }

    public void setDefendRange(int defendRange) {
        this.defendRange = defendRange;
    }

    public int getMaxPopulationIncrease() {
        return maxPopulationIncrease;
    }

    public void setMaxPopulationIncrease(int maxPopulationIncrease) {
        this.maxPopulationIncrease = maxPopulationIncrease;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public void setTowerType(TowerType towerType) {
        this.towerType = towerType;
    }
}
