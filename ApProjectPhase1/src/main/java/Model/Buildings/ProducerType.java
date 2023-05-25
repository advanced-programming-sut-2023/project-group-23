package Model.Buildings;

import Model.FoodType;
import Model.GroundType;
import Model.ResourceType;

public enum ProducerType {
    MILL ("mill", ResourceType.FLOUR, 2, null, FoodType.BREAD, 10, null),
    IRON_MINE ("iron mine", null, 0, ResourceType.IRON, null, 8, GroundType.IRON),
    QUARRY ("quarry", null, 0, ResourceType.STONE, null, 20, GroundType.SLATE),
    WOOD_CUTTER ("wood cutter", null, 0, ResourceType.WOOD, null, 16, GroundType.GRASS),
    ARMOURER ("armourer", ResourceType.IRON, 5, ResourceType.METAL_ARMOR, null, 1, null),
    BLACKSMITH ("blacksmith", ResourceType.IRON, 10, ResourceType.SWORD, null, 3, null),
    FLETCHER ("fletcher", ResourceType.WOOD, 10, ResourceType.BOW, null, 3, null),
    POLETURNER ("poleturner", ResourceType.WOOD, 10, ResourceType.SPEAR, null, 3, null),
    STABLE ("stable", null, 0, ResourceType.HORSE, null, 4, null),
    APPLE_GARDEN ("apple garden", null, 0, null, FoodType.APPLE, 20, GroundType.MEADOW),
    DAIRY_PRODUCTS ("dairy products", null, 0, ResourceType.LEATHER_ARMOR, FoodType.CHEESE, 20, null),
    HOP_FARM ("hop farm", null, 0, ResourceType.HOPS, null, 50, GroundType.MEADOW),
    WHEAT_FARM ("wheat farm", null, 0, ResourceType.WHEAT, null, 50, GroundType.MEADOW),
    BAKERY ("bakery", ResourceType.FLOUR, 1, null, FoodType.BREAD, 20, null),
    BREWERY ("brewery", ResourceType.HOPS, 30, ResourceType.ALE, null, 2, null),
    HUNT_POST ("hunt post", null, 0, null, FoodType.MEAT, 20, null),
    PITCH_RIG ("pitch rig", null, 0, ResourceType.PITCH, null, 50, GroundType.WATER);

    private ResourceType inputResource;
    private int inputRate;
    private ResourceType outputResource;
    private FoodType outputFood;
    private int outputRate;
    private GroundType specialGroundType;
    private String name;

    ProducerType(String name, ResourceType inputResource, int inputRate, ResourceType outputResource, FoodType outputFood, int outputRate, GroundType specialGroundType) {
        this.name = name;
        this.inputResource = inputResource;
        this.inputRate = inputRate;
        this.outputResource = outputResource;
        this.outputFood = outputFood;
        this.outputRate = outputRate;
        this.specialGroundType = specialGroundType;
    }

    public ResourceType getInputResource() {
        return inputResource;
    }

    public int getInputRate() {
        return inputRate;
    }

    public ResourceType getOutputResource() {
        return outputResource;
    }

    public FoodType getOutputFood() {
        return outputFood;
    }

    public int getOutputRate() {
        return outputRate;
    }

    public GroundType getSpecialGroundType() {
        return specialGroundType;
    }

    public String getName() {
        return name;
    }

    public static ProducerType getProducerTypeByName(String name) {
        for(ProducerType producerType : ProducerType.values())
            if(producerType.getName().equals(name))
                return producerType;

        return null;
    }
}
