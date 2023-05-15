package Model;

import Model.Buildings.Building;
import Model.People.Lord;
import Model.People.Person;
import Model.People.Troop;

import java.util.ArrayList;
import java.util.HashMap;

public class Government {
    private User user;
    private Colors governmentColor;
    private double gold;
    private int fearRate;
    private int taxRate;
    private int foodRate;
    private int popularityRate;
    private int religiousRate;
    private int innPopularity;
    private int maxPopulation;
    private int population;
    private int peasantPopulation;
    private int workerPopulation;
    private int troopsPopulation;
    private HashMap<ResourceType, Integer> resources;
    private int maxResourceStorage;
    private int maxFoodStorage;
    private int maxWeaponStorage;
    private ArrayList<Trade> tradeHistory;
    private ArrayList<Trade> tradeList;
    private HashMap<FoodType, Integer> foods;
    private ArrayList<Person> people;
    private ArrayList<Troop> troops;
    private ArrayList<Building> buildings;


    public Government(User user) {
        this.user = user;
        this.maxPopulation = 8;
        this.peasantPopulation = 8;
        this.workerPopulation = 0;
        this.taxRate = 0;
        this.foodRate = 0;
        this.religiousRate = 0;
        this.fearRate = 0;
        this.innPopularity = 0;
        foods = new HashMap<>() {{
            put(FoodType.BREAD, 200);
            put(FoodType.APPLE, 0);
            put(FoodType.CHEESE, 0);
            put(FoodType.MEAT, 0);
        }};
        this.maxFoodStorage = 0;
        this.maxResourceStorage = 0;
        this.maxWeaponStorage = 0;
        this.gold = 10000;
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

    public int getInnPopularity() {
        return innPopularity;
    }

    public void setInnPopularity(int innPopularity) {
        this.innPopularity = innPopularity;
    }

    public double getGold() {
        return gold;
    }

    public HashMap<ResourceType, Integer> getResources() {
        return resources;
    }
    public int getAmountByResource(ResourceType resourceType) {
        return resources.getOrDefault(resourceType, 0);
    }

    public void changeAmountOfResource(ResourceType resourceType, int newAmount) {
        resources.put(resourceType, newAmount);
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
    public int getFoodAmountByFood(FoodType foodType) {
        return foods.get(foodType);
    }

    public void changeFoodAmount(FoodType foodType, int newAmount) {
        foods.put(foodType, newAmount);
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

    public int getPeasantPopulation() {
        return peasantPopulation;
    }

    public int getWorkerPopulation() {
        return workerPopulation;
    }

    public int getTroopsPopulation() {
        return troopsPopulation;
    }

    public int getMaxResourceStorage() {
        return maxResourceStorage;
    }

    public int getMaxFoodStorage() {
        return maxFoodStorage;
    }

    public int getMaxWeaponStorage() {
        return maxWeaponStorage;
    }

    public void setMaxPopulation(int maxPopulation) {
        this.maxPopulation = maxPopulation;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setPeasantPopulation(int peasantPopulation) {
        this.peasantPopulation = peasantPopulation;
    }

    public void setWorkerPopulation(int workerPopulation) {
        this.workerPopulation = workerPopulation;
    }

    public void setTroopsPopulation(int troopsPopulation) {
        this.troopsPopulation = troopsPopulation;
    }

    public void setMaxResourceStorage(int maxResourceStorage) {
        this.maxResourceStorage = maxResourceStorage;
    }

    public void setMaxFoodStorage(int maxFoodStorage) {
        this.maxFoodStorage = maxFoodStorage;
    }

    public void setMaxWeaponStorage(int maxWeaponStorage) {
        this.maxWeaponStorage = maxWeaponStorage;
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

    public void setGold(double gold) {
        this.gold = gold;
    }
}
