package Model.Buildings;

public enum BuildingHP {
    VERY_HIGH (5),
    HIGH (4),
    NORMAL (3),
    LOW (2),
    VERY_LOW (1);

    public int hitPoint;

    BuildingHP(int hitPoint) {
        this.hitPoint = hitPoint;
    }
}
