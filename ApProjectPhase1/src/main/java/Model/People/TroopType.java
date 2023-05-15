package Model.People;

import Model.Buildings.TroopProducerType;
import Model.ResourceType;

import java.util.ArrayList;

public enum TroopType {
    SPEARMAN ("spearman", 100, 60, 60, 3, 0, 8, null, ResourceType.SPEAR, null, TroopProducerType.BARRACK),
    ARCHER ("archer", 150, 20, 20, 4, 5, 12, null, ResourceType.BOW, null, TroopProducerType.BARRACK),
    CROSSBOWMAN ("crossbowman", 200, 20, 20, 2, 3, 20, ResourceType.LEATHER_ARMOR, ResourceType.BOW, null, TroopProducerType.BARRACK),
    PIKEMAN ("pikeman", 300, 60, 60, 2, 0, 20, ResourceType.METAL_ARMOR, ResourceType.SPEAR, null, TroopProducerType.BARRACK),
    MACEMAN ("maceman", 200, 80, 80, 3, 0, 20, ResourceType.LEATHER_ARMOR, ResourceType.SPEAR, null, TroopProducerType.BARRACK),
    SWORDSMAN ("swordsman", 100, 100, 100, 1, 0, 40, ResourceType.METAL_ARMOR, ResourceType.SWORD, null, TroopProducerType.BARRACK),
    KNIGHT ("knight", 300, 100, 100, 5, 0, 40, ResourceType.METAL_ARMOR, ResourceType.SWORD, ResourceType.HORSE, TroopProducerType.BARRACK),
    ARCHERBOW ("archerbow", 150, 20, 20, 4, 5, 75, null, null, null, TroopProducerType.MERCENARY_POST),
    SLAVE ("slave", 50, 10, 150, 4, 0, 5, null, null, null, TroopProducerType.MERCENARY_POST),
    SLINGER ("slinger", 100, 20, 20, 4, 2, 12, null, null, null, TroopProducerType.MERCENARY_POST),
    HORSE_ARCHER ("hors earcher", 200, 20, 20, 5, 5, 80, null, null, null, TroopProducerType.MERCENARY_POST),
    ARABIAN_SWORDMAN ("arabian swordman", 300, 80, 80, 5, 0, 80, null, null, null, TroopProducerType.MERCENARY_POST),
    FIRE_THROWER ("fire thrower", 150, 80, 80, 5, 2, 100, null, null, null, TroopProducerType.MERCENARY_POST);

    private String name;
    private int hitPoint;
    private int humanDamage;
    private int buildingDamage;
    private int speed;
    private int fireRange;
    private int cost;
    private ResourceType armour;
    private ResourceType weapon;
    private ResourceType horse;
    private TroopProducerType troopProducerType;

    TroopType(String name, int hitPoint, int humanDamage, int buildingDamage, int speed, int fireRange, int cost, ResourceType armour, ResourceType weapon, ResourceType horse, TroopProducerType troopProducerType) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.humanDamage = humanDamage;
        this.buildingDamage = buildingDamage;
        this.speed = speed;
        this.fireRange = fireRange;
        this.cost = cost;
        this.armour = armour;
        this.weapon = weapon;
        this.horse = horse;
        this.troopProducerType = troopProducerType;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public int getHumanDamage() {
        return humanDamage;
    }

    public int getBuildingDamage() {
        return buildingDamage;
    }

    public int getSpeed() {
        return speed;
    }

    public int getFireRange() {
        return fireRange;
    }

    public int getCost() {
        return cost;
    }

    public ResourceType getArmour() {
        return armour;
    }

    public ResourceType getWeapon() {
        return weapon;
    }

    public ResourceType getHorse() {
        return horse;
    }

    public TroopProducerType getTroopProducerType() {
        return troopProducerType;
    }

    public static TroopType getTroopTypeByName(String name) {
        for(TroopType troopType : TroopType.values())
            if(troopType.getName().equals(name))
                return troopType;

        return null;
    }


}
