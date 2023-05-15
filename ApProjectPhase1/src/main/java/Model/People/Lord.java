package Model.People;

import Model.Buildings.Building;
import Model.Buildings.BuildingType;
import Model.Government;

public class Lord extends Troop{

    public Lord(Government government, TroopType type, int x, int y) {
        super(government, type, x, y);
    }
}
