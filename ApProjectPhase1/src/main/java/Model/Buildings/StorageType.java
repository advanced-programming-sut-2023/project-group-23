package Model.Buildings;

import Model.ResourceType;

public enum StorageType {
    ;

    private int amount;
    private ResourceType resource;

    StorageType(int amount, ResourceType resource) {
        this.amount = amount;
        this.resource = resource;
    }
}
