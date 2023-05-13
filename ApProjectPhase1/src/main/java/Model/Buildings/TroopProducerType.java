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
}
