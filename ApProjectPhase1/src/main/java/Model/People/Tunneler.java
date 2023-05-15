package Model.People;

import Model.Government;

public class Tunneler extends Troop{

    private int tunnelRange;

    public Tunneler(Government government, TroopType type, int x, int y) {
        super(government, type, x, y);
        tunnelRange = 5;
    }

    public int getTunnelRange() {
        return tunnelRange;
    }
}
