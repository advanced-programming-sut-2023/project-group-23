package Controller;

import Model.FoodType;
import Model.Game;
import Model.Government;
import Model.ResourceType;
import View.ShopMenu.ShopMenuCommands;

import java.util.regex.Matcher;

public class ShopMenuController {
    private Game game;
    private static Government currentGovernment;
    public static String showPriceList() {
        String result = "Resources:\n";
        for (ResourceType value : ResourceType.values()) {
            if (value.getName().equals("meat") || value.getName().equals("apple") || value.getName().equals("bread") || value.getName().equals("cheese")) continue;
            result += ("resource name : " + value.getName() + " , buy price : " + value.getBuyPrice() + " , sell price : " + value.getSellPrice());
            result += (", storage : ");
            if (currentGovernment.getResources().containsKey(value)) result += (currentGovernment.getResources().get(value));
            else result += ("you don't have this resource");
            result += "\n";
        }
        result += "Foods:\n";
        for (FoodType value : FoodType.values()) {
            result += ("food name : " + value.getName() + " , buy price : " + value.getBuyPrice() + " , sell price : " + value.getSellPrice());
            if (currentGovernment.getFoods().containsKey(value)) result += (currentGovernment.getFoods().get(value));
            else result += ("you don't have this resource");
            result += "\n";
        }
        return result;
    }

    private static ResourceType isResource(String name) {
        for (ResourceType value : ResourceType.values()) {
            if (value.getName().equals(name)) return value;
        }
        return null;
    }

    private static FoodType isFood(String name) {
        for (FoodType value : FoodType.values()) {
            if (value.getName().equals(name)) return value;
        }
        return null;
    }


    public static String buyItem(Matcher matcher) {
        String command = matcher.group(1);
        String itemName = null;
        String itemAmountField = null;
        Integer totalPrice = 0;
        Integer buyAmount = 0;
        Integer newAmount = 0;
        Matcher matcher1 = ShopMenuCommands.getMatcher(command, ShopMenuCommands.ITEM_NAME_FIELD);
        if (!matcher1.find()) return "item field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) itemName = matcher1.group("itemName");
        if (!(matcher1 = ShopMenuCommands.getMatcher(command, ShopMenuCommands.ITEM_AMOUNT)).find()) return "amount field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) itemAmountField = matcher1.group("itemAmount");
        if (!(matcher1 = ShopMenuCommands.getMatcher(itemAmountField, ShopMenuCommands.ITEM_AMOUNT_VALIDITY)).matches())
            return "amount must be an integer";
        buyAmount = Integer.parseInt(matcher1.group(1));
        if (buyAmount <= 0) return "amount must be a positive integer";
        ResourceType resourceType = isResource(itemName);
        FoodType foodType = isFood(itemName);
        if (resourceType == null && foodType == null) return "there isn't any item with this name";
        if (foodType != null){
            newAmount += (currentGovernment.getFoodAmountByFood(foodType) + buyAmount);
            if (newAmount > currentGovernment.getMaxFoodStorage()) return "you don't have enough space for this food";
            totalPrice = buyAmount * foodType.getBuyPrice();
            if (totalPrice > currentGovernment.getGold()) return "you don't have enough gold for buying this food";
            currentGovernment.changeFoodAmount(foodType, newAmount);
            currentGovernment.setGold(currentGovernment.getGold() - totalPrice);
            return "you bought " + buyAmount + " of " + foodType.getName() + " with total price " + totalPrice;
        }
        newAmount += (currentGovernment.getAmountByResource(resourceType) + buyAmount);
        if (newAmount > currentGovernment.getMaxResourceStorage()) return "you don't have enough space for this resource";
        totalPrice = buyAmount * resourceType.getBuyPrice();
        if (totalPrice > currentGovernment.getGold()) return "you don't have enough gold for buying this resource";
        currentGovernment.changeAmountOfResource(resourceType, newAmount);
        currentGovernment.setGold(currentGovernment.getGold() - totalPrice);
        return "you bought " + buyAmount + " of " + resourceType.getName() + " with total price " + totalPrice;
    }

    public static String sellItem(Matcher matcher) {
        String command = matcher.group(1);
        String itemName = null;
        String itemAmountField = null;
        Integer newAmount = 0;
        Integer sellAmount = 0;
        Integer totalPrice = 0;
        Matcher matcher1 = ShopMenuCommands.getMatcher(command, ShopMenuCommands.ITEM_NAME_FIELD);
        if (!matcher1.find()) return "item field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) itemName = matcher1.group("itemName");
        if (!(matcher1 = ShopMenuCommands.getMatcher(command, ShopMenuCommands.ITEM_AMOUNT)).find()) return "amount field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) itemAmountField = matcher1.group("itemAmount");
        if (!(matcher1 = ShopMenuCommands.getMatcher(itemAmountField, ShopMenuCommands.ITEM_AMOUNT_VALIDITY)).matches())
            return "amount must be an integer";
        sellAmount = Integer.parseInt(matcher1.group(1));
        if (sellAmount <= 0) return "amount must be a positive integer";
        ResourceType resourceType = isResource(itemName);
        FoodType foodType = isFood(itemName);
        if (resourceType == null && foodType == null) return "there isn't any item with this name";
        if (foodType != null) {
            newAmount += (currentGovernment.getAmountByResource(resourceType) - sellAmount);
            if (newAmount < 0) return "you can't sell foods more than your Storages balance";
            totalPrice = sellAmount * resourceType.getSellPrice();
            currentGovernment.changeAmountOfResource(resourceType, newAmount);
            currentGovernment.setGold(currentGovernment.getGold() - totalPrice);
            return "you sold " + sellAmount + " of " + resourceType.getName() + " with total price " + totalPrice;
        }
        newAmount += (currentGovernment.getAmountByResource(resourceType) - sellAmount);
        if (newAmount < 0) return "you can't sell resources more than your Storages balance";
        totalPrice = sellAmount * resourceType.getSellPrice();
        currentGovernment.changeAmountOfResource(resourceType, newAmount);
        currentGovernment.setGold(currentGovernment.getGold() - totalPrice);
        return "you sold " + sellAmount + " of " + resourceType.getName() + " with total price " + totalPrice;
    }
}
