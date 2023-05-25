package Model.Buildings;

import Model.GroundType;
import Model.ResourceType;

import java.util.ArrayList;
import java.util.HashMap;

public enum BuildingType {
    KEEP ("keep", 500, 0, 0, 3, null, 0, true, true),
    STONE_GATE ("stone gate", 1000, 0, 0, 3, ResourceType.STONE, 20, true, true),
    HOVEL ("hovel", 100, 0, 0, 2, ResourceType.WOOD, 6, true, false),
    CHURCH ("church", 800, 0, 250, 2, null, 0, true, false),
    CATHEDRAL ("cathedral", 1200, 0, 1000, 3, null, 0, true, false),
    SHOP ("shop", 500, 0, 100, 2, ResourceType.WOOD, 5, true, false),
    STOCKPILE ("stockpile", 500, 0, 0, 2, null, 0, false, true),
    GRANARY ("granary", 500, 0, 0, 2, ResourceType.WOOD, 5, false, false),
    ARMOURY ("armoury", 500, 0, 0, 2, ResourceType.WOOD, 5, false, true),
    MILL ("mill", 300, 3, 0, 2, ResourceType.WOOD, 20, false, false),
    IRON_MINE ("iron mine", 100, 2, 0, 3, ResourceType.WOOD, 20, false, false),
    QUARRY ("quarry", 300, 3, 0, 3, ResourceType.WOOD, 20, false, false),
    WOOD_CUTTER ("wood cutter", 100, 1, 0, 1, ResourceType.WOOD, 3, false, false),
    ARMOURER ("armourer", 300, 1, 100, 1, ResourceType.WOOD, 20, false, false),
    BLACKSMITH ("blacksmith", 300, 1, 100, 1, ResourceType.WOOD, 20, false, false),
    FLETCHER ("fletcher", 300, 1, 100, 1, ResourceType.WOOD, 20, false, false),
    POLETURNER ("poleturner", 300, 1, 100, 1, ResourceType.WOOD, 20, false, false),
    STABLE ("stable", 300, 0, 400, 3, ResourceType.WOOD, 20, false, true),
    APPLE_GARDEN ("apple garden", 300, 1, 0, 3, ResourceType.WOOD, 5, true, false),
    DAIRY_PRODUCTS ("dairy products", 300, 1, 0, 3, ResourceType.WOOD, 5, true, false),
    HOP_FARM ("hop farm", 300, 1, 0, 3, ResourceType.WOOD, 15, true, false),
    WHEAT_FARM ("wheat farm", 300, 1, 0, 3, ResourceType.WOOD, 15, true, false),
    BAKERY ("bakery", 300, 1, 0, 2, ResourceType.WOOD, 10, false, false),
    BREWERY ("brewery", 300, 1, 0, 2, ResourceType.WOOD, 10, false, false),
    HUNT_POST ("hunt post", 300, 1, 0, 1, ResourceType.WOOD, 5, false, false),
    PITCH_RIG ("pitch rig", 300, 1, 0, 2, ResourceType.WOOD, 20, true, false),
    BARRACK ("barrack", 500, 0, 0, 3, ResourceType.STONE, 15, true, false),
    MERCENARY_POST ("mercenary post", 300, 0, 0, 3, ResourceType.WOOD, 10, true, false),
    ENGINEER_GUILD ("engineer guild", 300, 0, 100, 3, ResourceType.WOOD, 10, true, false),
    WOODEN_TOWER ("wooden tower", 200, 0, 0, 1, ResourceType.STONE, 10, false, true),
    PERIMETER_TOWER ("perimeter tower", 1000, 0, 0, 2, ResourceType.WOOD, 10, false, true),
    DEFENSE_TURRET ("defense turret", 1200, 0, 0, 2, ResourceType.STONE, 15, false, true),
    SQUARE_TOWER ("square tower", 1600, 0, 0, 3, ResourceType.STONE, 35, false, true),
    ROUND_TOWER ("round tower", 2000, 0, 0, 3, ResourceType.STONE, 40, false, true),
    HIGH_WALL ("high wall", 200, 0, 0, 1, ResourceType.STONE, 10, false, true),
    LOW_WALL ("low wall", 100, 0, 0, 1, ResourceType.STONE, 5, false, true)
    ;

    private String name;
    private int hitPoint;
    private int workerNeeded;
    private int cost;
    private int size;
    private ResourceType resourceCostType;
    private int resourceCost;
    private boolean isPassable;
    private boolean isRepairable;
    private ArrayList<GroundType> forbiddenGroundTypes;

    BuildingType(String name, int hitPoint, int workerNeeded, int cost, int size, ResourceType resourceCostType, int resourceCost, boolean isPassable, boolean isRepairable) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.workerNeeded = workerNeeded;
        this.cost = cost;
        this.size = size;
        this.resourceCostType = resourceCostType;
        this.resourceCost = resourceCost;
        this.resourceCost = resourceCost;
        this.isPassable = isPassable;
        this.isRepairable = isRepairable;
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

    public boolean isRepairable() {
        return isRepairable;
    }

    public static BuildingType getBuildingTypeByName(String name) {
        for (BuildingType buildingType : BuildingType.values())
            if(buildingType.getName().equals(name))
                return buildingType;

        return null;
    }
}
