package Model.Buildings;

import Model.Buildings.TrapType;

import java.util.ArrayList;

public enum TroopProducerType {
    BARRACK ("barrack"),
    MERCENARY_POST ("mercenary post"),
    ENGINEER_GUILD ("engineer guild")
    ;

    private ArrayList<TrapType> availableTroops;
    private String name;

    TroopProducerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TroopProducerType getTroopProducerTypeByName(String name) {
        for(TroopProducerType troopProducerType : TroopProducerType.values())
            if(troopProducerType.getName().equals(name))
                return troopProducerType;

        return null;
    }
}
