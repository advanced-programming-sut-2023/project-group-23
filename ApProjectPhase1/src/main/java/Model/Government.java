package Model;

import Model.Buildings.Building;
import Model.People.Person;
import Model.People.Troop;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private User user;
    private Colors governmentColor;
    private int gold;
    private int fearRate;
    private int taxRate;
    private int foodRate;
    private int popularityRate;
    private int religiousRate;
    private int maxPopulation;
    private int population;
    private HashMap<ResourceType, Integer> resources;
    private ArrayList<Trade> tradeHistory;
    private ArrayList<Trade> tradeList;
    private HashMap<FoodType, Integer> foods;
    private ArrayList<Person> people;
    private ArrayList<Troop> troops;
    private ArrayList<Building> buildings;


    public Government(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }


    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public void setPopularityRate(int popularityRate) {
        this.popularityRate = popularityRate;
    }

    public int getReligiousRate() {
        return religiousRate;
    }


    public void setReligiousRate(int religiousRate) {
        this.religiousRate = religiousRate;
    }

    public int getGold() {
        return gold;
    }

    public HashMap<ResourceType, Integer> getResources() {
        return resources;
    }

    public void addResources(ResourceType resource, int amount) {
    }

    public void addFoodForSale(FoodType food, int amount) {
    }

    public void addResourceForSale(ResourceType resource, int amount) {
    }

    public ArrayList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    public void addToTradeHistory(Trade trade) {
        tradeHistory.add(trade);
    }

    public ArrayList<Trade> getTradeList() {
        return tradeList;
    }

    public void addToTradeList(Trade trade) {
        tradeList.add(trade);
    }

    public HashMap<FoodType, Integer> getFoods() {
        return foods;
    }

    public void addFood(FoodType food, int amount){
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void addPerson(Person person){
        people.add(person);
    }

    public void removePerson(Person person) {
        people.remove(person);
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addTroop(Troop troop){
        troops.add(troop);
    }

    public void removeTroop(Troop troop) {
        troops.remove(troop);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public int getPopulation() {
        return population;
    }

    public void changeMaxPopulation(int amount) {
    }

    public void changePopulation(int amount) {
    }

    public Colors getGovernmentColor() {
        return governmentColor;
    }

    public void setGovernmentColor(Colors governmentColor) {
        this.governmentColor = governmentColor;
    }
    public void addBuilding(Building building) {
        buildings.add(building);
    }
    public void removeBuilding(Building building) {
        buildings.remove(building);
    }
}