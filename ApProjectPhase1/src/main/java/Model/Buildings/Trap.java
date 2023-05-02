package Model.Buildings;

import Model.Government;

public class Trap extends Building {
    private int damage;
    private TrapType trapType;

    public Trap(BuildingType type, TrapType trapType, Government government) {
        super(type, government);
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
