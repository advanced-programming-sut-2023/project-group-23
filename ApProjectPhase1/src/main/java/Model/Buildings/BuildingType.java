package Model.Buildings;

import Model.GroundType;
import Model.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public enum BuildingType {
    KEEP ("keep", 0, 0, 0, 3, null, 0, true),
    STONE_GATE ("stone gate", 1000, 0, 0, 3, ResourceType.STONE, 20, true),
    STOCKPILE ("stockpile", 500, 0, 0, 2, null, 0, false),
    GRANARY ("granary", 500, 0, 0, 2, ResourceType.WOOD, 5, false);

    private String name;
    private int hitPoint;
    private int workerNeeded;
    private int cost;
    private int size;
    private ResourceType resourceCostType;
    private int resourceCost;
    private boolean isPassable;
    private ArrayList<GroundType> forbiddenGroundTypes;

    BuildingType(String name, int hitPoint, int workerNeeded, int cost, int size, ResourceType resourceCostType, int resourceCost, boolean isPassable) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.workerNeeded = workerNeeded;
        this.cost = cost;
        this.size = size;
        this.resourceCostType = resourceCostType;
        this.resourceCost = resourceCost;
        this.resourceCost = resourceCost;
        this.isPassable = isPassable;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getWorkerNeeded() {
        return workerNeeded;
    }

    public int getCost() {
        return cost;
    }

    public int getSize() {
        return size;
    }

    public ResourceType getResourceCostType() {
        return resourceCostType;
    }

    public int getResourceCost() {
        return resourceCost;
    }

    public boolean isPassable() {
        return isPassable;
    }
}
