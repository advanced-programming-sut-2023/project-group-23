package Model;

import java.util.ArrayList;

public enum TroopProducerType {
    ;

    private ArrayList<TrapType> availableTroops;

    TroopProducerType(ArrayList<TrapType> availableTroops) {
        this.availableTroops = availableTroops;
    }
}
