package Model.Buildings;

import Controller.GameMenuController;
import Model.FoodType;
import Model.Government;
import Model.ResourceType;

public class Producer extends Building {
    private ResourceType inputResource;
    private int inputRate;
    private ResourceType outputResource;
    private int outputRate;
    private ProducerType producerType;

    public Producer(BuildingType type, ProducerType producerType, Government government, int x, int y) {
        super(type, government, x, y);
        this.producerType = producerType;
    }

    public void produce(Government government) {
        if(super.getWorkerNeeded() != 0) return;

        ResourceType inputResource = this.producerType.getInputResource();
        if(inputResource != null) {
            if(government.getAmountByResource(inputResource) < inputRate) return;
            else
                government.changeAmountOfResource(inputResource,
                        government.getAmountByResource(inputResource) - inputRate);
        }

        FoodType outputFood = this.producerType.getOutputFood();
        if(outputFood != null) {
            int newAmount = Math.max(government.getFoodAmountByFood(outputFood) + this.producerType.getOutputRate(),
                    government.getMaxFoodStorage());
            government.changeFoodAmount(outputFood, newAmount);
        }
        ResourceType outputResource = this.producerType.getOutputResource();
        if(outputResource != null) {
            int newAmount;
            if(ResourceType.weapons.contains(outputResource)) {
                newAmount = Math.max(government.getAmountByResource(outputResource) + this.producerType.getOutputRate(),
                        government.getMaxWeaponStorage());
            }
            else {
                newAmount = Math.max(government.getAmountByResource(outputResource) + this.producerType.getOutputRate(),
                        government.getMaxResourceStorage());
            }
            government.changeAmountOfResource(outputResource, newAmount);
        }
    }

    public ResourceType getInputResource() {
        return inputResource;
    }

    public void setInputResource(ResourceType inputResource) {
        this.inputResource = inputResource;
    }

    public int getInputRate() {
        return inputRate;
    }

    public void setInputRate(int inputRate) {
        this.inputRate = inputRate;
    }

    public ResourceType getOutputResource() {
        return outputResource;
    }

    public void setOutputResource(ResourceType outputResource) {
        this.outputResource = outputResource;
    }

    public int getOutputRate() {
        return outputRate;
    }

    public void setOutputRate(int outputRate) {
        this.outputRate = outputRate;
    }

    public ProducerType getProducerType() {
        return producerType;
    }

    public void setProducerType(ProducerType producerType) {
        this.producerType = producerType;
    }
}
