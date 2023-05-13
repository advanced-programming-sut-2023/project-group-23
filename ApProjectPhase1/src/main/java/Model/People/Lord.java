package Model.People;

import Model.Buildings.Building;
import Model.Buildings.BuildingType;
import Model.Government;

public class Lord {
    private Government government;
    private int hitPoint;
    private int damage;
    private int x;
    private int y;

    public Lord(Government government) {
        this.government = government;
        this.hitPoint = 2000;
        this.damage = 100;
        for(Building building : government.getBuildings()) {
            if(building.getType().equals(BuildingType.KEEP)) {
                this.x = building.getxCoordinate();
                this.y = building.getyCoordinate();
                break;
            }
        }
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
