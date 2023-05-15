package Model;

import Model.Buildings.Building;
import Model.People.Person;
import Model.People.Troop;

import java.util.ArrayList;

public class MapCell {
    private Building building;
    private GroundType groundType;
    private WaterType waterType;
    private Tree tree;
    private Rock rock;
    private ArrayList<Person> people;
    private ArrayList<Troop> troops;
    private boolean passablity;
    private int x;
    private int y;

    MapCell(GroundType groundType, int x, int y) {
        people = new ArrayList<>();
        troops = new ArrayList<>();
        this.groundType = groundType;
        this.x = x;
        this.y = y;
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

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void addToPeople(Person person) {
        people.add(person);
    }

    public void removeFromPeople(Person person) {
        people.remove(person);
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addToTroops(Troop troop) {
        troops.add(troop);
    }

    public void removeFromTroops(Troop troop) {
        troops.remove(troop);
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

    public Rock getRock() {
        return rock;
    }

    public void setRock(Rock rock) {
        this.rock = rock;
    }

    public boolean isPassablity() {
        return passablity;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
