package Model;

import java.util.ArrayList;

public enum TroopType {
    ;

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

    TroopType(String name, int hitPoint, int humanDamage, int buildingDamage, int speed, int fireRange, int cost, boolean hasLadder, boolean canClimb, ArrayList<ResourceType> requirements, TroopType type) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.humanDamage = humanDamage;
        this.buildingDamage = buildingDamage;
        this.speed = speed;
        this.fireRange = fireRange;
        this.cost = cost;
        this.hasLadder = hasLadder;
        this.canClimb = canClimb;
        this.requirements = requirements;
        this.type = type;
    }
}
