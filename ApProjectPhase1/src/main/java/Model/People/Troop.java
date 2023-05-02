package Model.People;

import Model.Government;
import Model.ResourceType;

import java.util.ArrayList;

public class Troop extends Person {
    private String name;
    private int hitPoint;
    private int humanDamage;
    private int buildingDamage;
    private int speed;
    private int fireRange;
    private int cost;
    private boolean hasLadder;
    private boolean canClimb;
    private ArrayList<ResourceType> requirements;
    private TroopType type;
    private TroopState state;


    public Troop(Government government, TroopType type) {
        super(government);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getHumanDamage() {
        return humanDamage;
    }

    public void setHumanDamage(int humanDamage) {
        this.humanDamage = humanDamage;
    }

    public int getBuildingDamage() {
        return buildingDamage;
    }

    public void setBuildingDamage(int buildingDamage) {
        this.buildingDamage = buildingDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFireRange() {
        return fireRange;
    }

    public void setFireRange(int fireRange) {
        this.fireRange = fireRange;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isHasLadder() {
        return hasLadder;
    }

    public void setHasLadder(boolean hasLadder) {
        this.hasLadder = hasLadder;
    }

    public ArrayList<ResourceType> getRequirements() {
        return requirements;
    }

    public void setRequirements(ArrayList<ResourceType> requirements) {
        this.requirements = requirements;
    }

    public TroopType getType() {
        return type;
    }

    public void setType(TroopType type) {
        this.type = type;
    }

    public boolean isCanClimb() {
        return canClimb;
    }

    public void setCanClimb(boolean canClimb) {
        this.canClimb = canClimb;
    }

    public TroopState getState() {
        return state;
    }

    public void setState(TroopState state) {
        this.state = state;
    }
}
