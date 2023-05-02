package Model.Buildings;

import Model.Government;
import Model.GroundType;
import Model.ResourceType;

import java.util.HashMap;

public class Building {
    private String name;
    private int hitPoint;
    private int workerNeeded;
    private BuildingType type;
    private int cost;
    private int size;
    private HashMap<ResourceType, Integer> resourceCost;
    private GroundType allowedGroundType;
    private Government government;

    public Building(BuildingType type, Government government) {
        this.type = type;
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

    public int getWorkerNeeded() {
        return workerNeeded;
    }

    public void setWorkerNeeded(int workerNeeded) {
        this.workerNeeded = workerNeeded;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public HashMap<ResourceType, Integer> getResourceCost() {
        return resourceCost;
    }

    public void setResourceCost(HashMap<ResourceType, Integer> resourceCost) {
        this.resourceCost = resourceCost;
    }

    public GroundType getAllowedGroundType() {
        return allowedGroundType;
    }

    public void setAllowedGroundType(GroundType allowedGroundType) {
        this.allowedGroundType = allowedGroundType;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }
}
