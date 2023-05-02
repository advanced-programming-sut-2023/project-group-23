package Model.Buildings;

import Model.Government;
import Model.People.TroopType;

import java.util.ArrayList;

public class TroopProducers extends Building {
    private ArrayList<TrapType> availableTroops;
    private TroopProducerType troopProducerType;

    public TroopProducers(BuildingType type, TroopProducerType troopProducerType, Government government) {
        super(type, government);
    }

    public void produceTroop(TroopType troopType) {

    }
}
