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

    private static boolean isResource(String name) {
        boolean is = false;
        for (ResourceType value : ResourceType.values()) {
            if (value.getName().equals(name)) {
                is = true;
                break;
            }
        }
        return is;
    }

    private static boolean isFood(String name) {
        boolean is = false;
        for (FoodType value : FoodType.values()) {
            if (value.getName().equals(name)) {
                is = true;
                break;
            }
        }
        return is;
    }


    public static String buyItem(Matcher matcher) {
        String command = matcher.group(1);
        ResourceType resourceType = null;
        FoodType foodType = null;
        String itemName = null;
        String itemAmountField = null;
        Matcher matcher1 = ShopMenuCommands.getMatcher(command, ShopMenuCommands.ITEM_NAME_FIELD);
        if (!matcher1.find()) return "item field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) itemName = matcher1.group("itemName");
        if (!(matcher1 = ShopMenuCommands.getMatcher(command, ShopMenuCommands.ITEM_AMOUNT)).find()) return "amount field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) itemAmountField = matcher1.group("itemAmount");
        if (!(matcher1 = ShopMenuCommands.getMatcher(itemAmountField, ShopMenuCommands.ITEM_AMOUNT_VALIDITY)).matches())
            return "amount must be an integer";
        if (Integer.parseInt(matcher1.group(1)) <= 0) return "amount must be a positive integer";
        if (!isResource(itemName) && !isFood(itemName)) return "there isn't any item with this name";
        //TODO: update resources behrad
        return null;
    }

    public static String sellItem(Matcher matcher) {
        String command = matcher.group(1);
        String itemName = null;
        String itemAmountField = null;
        Matcher matcher1 = ShopMenuCommands.getMatcher(command, ShopMenuCommands.ITEM_NAME_FIELD);
        if (!matcher1.find()) return "item field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) itemName = matcher1.group("itemName");
        if (!(matcher1 = ShopMenuCommands.getMatcher(command, ShopMenuCommands.ITEM_AMOUNT)).find()) return "amount field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) itemAmountField = matcher1.group("itemAmount");
        if (!(matcher1 = ShopMenuCommands.getMatcher(itemAmountField, ShopMenuCommands.ITEM_AMOUNT_VALIDITY)).matches())
            return "amount must be an integer";
        if (Integer.parseInt(matcher1.group(1)) <= 0) return "amount must be a positive integer";
        if (!isResource(itemName) && !isFood(itemName)) return "there isn't any item with this name";
        //TODO: update resources behrad
        return null;
    }
}
