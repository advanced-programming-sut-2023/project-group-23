package Model.Buildings;

import Model.ResourceType;

public enum StorageType {
    STOCKPILE ("stockpile"),
    GRANARY ("granary"),
    ARMOURY ("armoury");

    private String name;

    StorageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static StorageType getStorageTypeByName(String name) {
        for(StorageType storageType : StorageType.values())
            if(storageType.getName().equals(name))
                return storageType;

        return null;
    }
}
