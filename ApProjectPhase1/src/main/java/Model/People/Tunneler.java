package Model.People;

import Model.Buildings.Building;
import Model.Buildings.TowerType;
import Model.Government;

import java.util.ArrayList;

public class Tunneler extends Troop{

    private static int tunnelRange = 5;

    private static ArrayList<TowerType> allowedTowers = new ArrayList<>() {{
        add(TowerType.LOW_WALL);
        add(TowerType.HIGH_WALL);
        add(TowerType.WOODEN_TOWER);
        add(TowerType.DEFENSE_TURRET);
        add(TowerType.PERIMETER_TOWER);
    }};

    public Tunneler(Government government, TroopType type, int x, int y) {
        super(government, type, x, y);
    }

    public static int getTunnelRange() {
        return tunnelRange;
    }

    public static boolean isAllowed(Building building, Government currentGovernment) {
        boolean notAllowed = building == null ||
                building.getGovernment().equals(currentGovernment) ||
                TowerType.getTowerTypeByName(building.getType().getName()) == null ||
                !allowedTowers.contains(TowerType.getTowerTypeByName(building.getType().getName()));
        return !notAllowed;
    }
}
