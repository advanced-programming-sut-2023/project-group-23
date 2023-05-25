package Model.Buildings;

import Model.ResourceType;

public enum TowerType {
    WOODEN_TOWER ("wooden tower"),
    PERIMETER_TOWER ("perimeter tower"),
    DEFENSE_TURRET ("defense turret"),
    SQUARE_TOWER ("square tower"),
    ROUND_TOWER ("round tower"),
    HIGH_WALL ("high wall"),
    LOW_WALL ("low wall")
    ;

    private String name;

    TowerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TowerType getTowerTypeByName(String name) {
        for(TowerType towerType : TowerType.values())
            if(towerType.getName().equals(name))
                return towerType;

        return null;
    }
}
