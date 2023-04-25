package Model;

import java.util.ArrayList;

public class MapCell {
    private Building building;
    private GroundType groundType;
    private ArrayList<Troop> troops;
    private boolean isPassable;

    MapCell(GroundType groundType) {
        this.groundType = groundType;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public GroundType getGroundType() {
        return groundType;
    }

    public void setGroundType(GroundType groundType) {
        this.groundType = groundType;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void setTroops(ArrayList<Troop> troops) {
        this.troops = troops;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }

    public String showCell() {
        return null;
    }
}
