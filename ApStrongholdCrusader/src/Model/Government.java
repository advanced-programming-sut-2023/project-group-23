package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private User user;
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
    private ArrayList<Food> foods;
    private ArrayList<Person> people;
    private ArrayList<Troop> troops;
    private ArrayList<Building> buildings;

    public Government(User user) {
        this.user = user;
        foods = new ArrayList<>();
        people = new ArrayList<>();
        troops = new ArrayList<>();
        buildings = new ArrayList<>();
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

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void addFood(Food food){
        foods.add(food);
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void addPerson(Person person){
        people.add(person);
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void addTroop(Troop troop){
        troops.add(troop);
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
}
