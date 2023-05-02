package Model.Buildings;

import Model.ResourceType;

public enum ProducerType {
    ;

    private ResourceType inputResource;
    private int inputRate;
    private ResourceType outputResource;
    private int outputRate;

    ProducerType(ResourceType inputResource, int inputRate, ResourceType outputResource, int outputRate) {
        this.inputResource = inputResource;
        this.inputRate = inputRate;
        this.outputResource = outputResource;
        this.outputRate = outputRate;
    }
}
