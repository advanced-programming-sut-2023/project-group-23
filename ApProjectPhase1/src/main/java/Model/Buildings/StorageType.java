package Model.Buildings;

import Model.ResourceType;

public enum StorageType {
    STOCKPILE ("stockpile"),
    GRANARY ("granary");

    private String name;

    StorageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
