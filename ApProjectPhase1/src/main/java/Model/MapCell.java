package Model;

import Model.Buildings.Building;
import Model.People.Troop;

import java.util.ArrayList;

public class MapCell {
    private Building building;
    private GroundType groundType;
    private WaterType waterType;
    private Tree tree;
    private ArrayList<Troop> troops;
    private boolean passablity;

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

    public WaterType getWaterType() {
        return waterType;
    }

    public void setWaterType(WaterType waterType) {
        this.waterType = waterType;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public boolean isPassable() {
        return passablity;
    }

    public void setPassablity(boolean passablity) {
        this.passablity = passablity;
    }

    public String showCell() {
        return null;
    }
}
