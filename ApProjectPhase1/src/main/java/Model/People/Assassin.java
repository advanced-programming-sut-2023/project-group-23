package Model.People;

import Model.Government;

public class Assassin extends Troop {

    private boolean isVisible;

    public Assassin(Government government, TroopType type) {
        super(government, type);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
