package Model.Buildings;

import Model.Government;
import Model.ResourceType;

public class Storage extends Building {
    private static int maxResourceCapacity = 500;
    private static int maxFoodCapacity = 250;
    private StorageType storageType;

    public Storage(BuildingType type, StorageType storageType, Government government, int x, int y) {
        super(type, government, x, y);
        this.storageType = storageType;
    }

    public static int getMaxResourceCapacity() {
        return maxResourceCapacity;
    }

    public static int getMaxFoodCapacity() {
        return maxFoodCapacity;
    }

    public StorageType getStorageType() {
        return storageType;
    }
}
