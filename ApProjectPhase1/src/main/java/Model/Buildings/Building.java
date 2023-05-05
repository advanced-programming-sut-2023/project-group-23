package Model.Buildings;

import Model.Government;
import Model.GroundType;
import Model.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public class Building {
    private String name;
    private int hitPoint;
    private int xCoordinate;
    private int yCoordinate;
    private int workerNeeded;
    private BuildingType type;
    private int cost;
    private int size;
    private HashMap<ResourceType, Integer> resourceCost;
    private static ArrayList<GroundType> forbiddenGroundTypes = new ArrayList<>() {{
        add(GroundType.WATER);
        add(GroundType.SLATE);
        add(GroundType.ROCK);
        add(GroundType.IRON);
    }};
    private Government government;

    public Building(BuildingType type, Government government, int x, int y) {
        this.type = type;
        this.government = government;
        this.xCoordinate = x;
        this.yCoordinate = y;
        government.addBuilding(this);
        this.name = type.getName();
        this.hitPoint = type.getHitPoint().hitPoint;
        this.workerNeeded = type.getWorkerNeeded();
        this.cost = type.getCost();
        this.size = type.getSize();
        this.resourceCost = type.getResourceCost();
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static ArrayList<GroundType> getForbiddenGroundTypes() {
        return forbiddenGroundTypes;
    }

    public void setForbiddenGroundTypes(ArrayList<GroundType> forbiddenGroundTypes) {
        this.forbiddenGroundTypes = forbiddenGroundTypes;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
