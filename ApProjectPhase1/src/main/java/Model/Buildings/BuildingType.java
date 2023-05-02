package Model.Buildings;

import Model.GroundType;
import Model.ResourceType;

import java.util.HashMap;

public enum BuildingType {
    ;

    private String name;
    private int hitPoint;
    private int workerNeeded;
    private BuildingType type;
    private int cost;
    private int size;
    private HashMap<ResourceType, Integer> resourceCost;
    private GroundType allowedGroundType;

    BuildingType(String name, int hitPoint, int workerNeeded, BuildingType type, int cost, int size, HashMap<ResourceType, Integer> resourceCost, GroundType allowedGroundType) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.workerNeeded = workerNeeded;
        this.type = type;
        this.cost = cost;
        this.size = size;
        this.resourceCost = resourceCost;
        this.allowedGroundType = allowedGroundType;
    }
}
