package Model;

public class Producer extends Building{
    private ResourceType inputResource;
    private int inputRate;
    private ResourceType outputResource;
    private int outputRate;
    private ProducerType producerType;

    public Producer(BuildingType type, ProducerType producerType, Government government) {
        super(type, government);
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
