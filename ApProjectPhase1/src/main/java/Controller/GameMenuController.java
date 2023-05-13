package Controller;

import Model.FoodType;
import Model.Game;
import Model.Government;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class GameMenuController {
    private static Government currentGovernment;
    private Game game;

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        GameMenuController.currentGovernment = currentGovernment;
    }

    public static void showPopularityFactors() {
        int foodDiversity = -1;
        for(int amount : currentGovernment.getFoods().values()) {
            if(amount > 0)
                foodDiversity++;
        }
        int foodPopularity = Math.max(foodDiversity, 0) + currentGovernment.getFoodRate() * 4 + currentGovernment.getInnPopularity();
        System.out.println("food popularity: " + foodPopularity);

        int taxPopularity;
        int taxRate = currentGovernment.getTaxRate();
        if(taxRate < 1)
            taxPopularity = 1 - 2 * taxRate;
        else if(taxRate < 5)
            taxPopularity = -2 * taxRate;
        else
            taxPopularity = 4 * taxRate - 8;
        System.out.println("tax popularity: " + taxPopularity);

        System.out.println("fear popularity: " + currentGovernment.getFearRate());

        System.out.println("religious popularity: " + currentGovernment.getReligiousRate());

        int totalPopularity = foodPopularity + taxPopularity +
                currentGovernment.getFearRate() +
                currentGovernment.getReligiousRate();
        System.out.println("total popularity rate: " + totalPopularity);
    }

    public static String showPopularity() {
        int foodDiversity = -1;
        for(int amount : currentGovernment.getFoods().values()) {
            if(amount > 0)
                foodDiversity++;
        }
        int foodPopularity = Math.max(foodDiversity, 0) + currentGovernment.getFoodRate() * 4;

        int taxPopularity;
        int taxRate = currentGovernment.getTaxRate();
        if(taxRate < 1)
            taxPopularity = 1 - 2 * taxRate;
        else if(taxRate < 5)
            taxPopularity = -2 * taxRate;
        else
            taxPopularity = 4 * taxRate - 8;

        int totalPopularity = foodPopularity + taxPopularity +
                currentGovernment.getFearRate() +
                currentGovernment.getReligiousRate();
        return "total popularity rate: " + totalPopularity;
    }

    public static void showFoodList() {
        for(Map.Entry<FoodType, Integer> entry : currentGovernment.getFoods().entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }
    }

    public static String setFoodRate(Matcher matcher) {
        matcher.matches();
        int inputRate = Integer.parseInt(matcher.group("rateNumber"));

        if(inputRate < -2 || inputRate > 2)
            return "rate number out of bounds";

        boolean isStorageEmpty = true;
        for(int amount : currentGovernment.getFoods().values())
            if(amount > 0) {
                isStorageEmpty = false;
                break;
            }
        if(isStorageEmpty)
            return "rate only can be -2 when storage is empty";

        currentGovernment.setFoodRate(inputRate);
        return "set food rate successfully";
    }

    public static String  showFoodRate() {
         return "food rate: " + currentGovernment.getFoodRate();
    }

    public static String setTaxRate(Matcher matcher) {
        matcher.matches();
        int inputRate = Integer.parseInt(matcher.group("rateNumber"));

        if(inputRate < -3 || inputRate > 8)
            return "rate number out of bounds";

        if(currentGovernment.getGold() < 1 && inputRate < 0)
            return "can't donate gold when you're out of golds";

        currentGovernment.setTaxRate(inputRate);
        return "set tax rate successfully";
    }

    public static String showTaxRate() {
        return "tax rate: " + currentGovernment.getTaxRate();
    }

    public static String setFearRate(Matcher matcher) {
        matcher.matches();
        int inputRate = Integer.parseInt(matcher.group("rateNumber"));

        if(inputRate < -5 || inputRate > 5)
            return "rate number out of bounds";

        currentGovernment.setFearRate(inputRate);
        return "set fear rate successfully";
    }

    public static void dropBuilding(Matcher matcher) {
    }

    public static void selectBuilding(Matcher matcher) {
    }

    public static void createUnit(Matcher matcher) {
    }

    public static void repair() {
    }

    public static void selectUnit(Matcher matcher) {
    }

    public static void moveUnit(Matcher matcher) {
    }

    public static void patrolUnit(Matcher matcher) {
    }

    public static void setState(Matcher matcher) {
    }

    public static void attack(Matcher matcher) {
    }

    public static void airAttack(Matcher matcher) {
    }

    public static void pourOil(Matcher matcher) {
    }

    public static void digTunnel(Matcher matcher) {
    }

    public static void buildSurroundEquipment(Matcher matcher) {
    }

    public static void disbandUnit() {
    }
}
