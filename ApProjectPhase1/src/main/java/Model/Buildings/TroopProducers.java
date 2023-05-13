package Model.Buildings;

import Model.Government;
import Model.People.TroopType;

import java.util.ArrayList;

public class TroopProducers extends Building {
    private ArrayList<TrapType> availableTroops;
    private TroopProducerType troopProducerType;

    public TroopProducers(BuildingType type, TroopProducerType troopProducerType, Government government, int x, int y) {
        super(type, government, x, y);
        this.troopProducerType = troopProducerType;
    }

    public TroopProducerType getTroopProducerType() {
        return troopProducerType;
    }

    public void produceTroop(TroopType troopType) {

    }
}
