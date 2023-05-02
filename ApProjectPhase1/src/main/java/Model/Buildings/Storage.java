package Model.Buildings;

import Model.Government;
import Model.ResourceType;

public class Storage extends Building {
    private int amount;
    private ResourceType resource;
    private StorageType storageType;

    public Storage(BuildingType type, StorageType storageType, Government government) {
        super(type, government);
    }
}
