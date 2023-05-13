package Model.Buildings;

import Model.GroundType;
import Model.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public enum BuildingType {
    KEEP ("keep", BuildingHP.VERY_HIGH, 0, 0, 3, null);

    private String name;
    private BuildingHP hitPoint;
    private int workerNeeded;
    private int cost;
    private int size;
    private HashMap<ResourceType, Integer> resourceCost;
    private boolean isPassable;
    private ArrayList<GroundType> forbiddenGroundTypes;

    BuildingType(String name, BuildingHP hitPoint, int workerNeeded, int cost, int size, HashMap<ResourceType, Integer> resourceCost) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.workerNeeded = workerNeeded;
        this.cost = cost;
        this.size = size;
        this.resourceCost = resourceCost;
    }

    public String getName() {
        return name;
    }

    public BuildingHP getHitPoint() {
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

    public HashMap<ResourceType, Integer> getResourceCost() {
        return resourceCost;
    }
}
