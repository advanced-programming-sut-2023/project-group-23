package Controller;

import Model.Game;
import Model.Government;

import java.util.regex.Matcher;

public class ShopMenuController {
    private Game game;
    public static String showPriceList() {
        String result = "Resources:\n";
        //TODO : add resources
        result += "Foods:\n";
        //TODO: add foods
        return result;
    }

    public static String buyItem(Matcher matcher) {
        return null;
    }

    public static String sellItem(Matcher matcher) {
        return null;
    }
}
