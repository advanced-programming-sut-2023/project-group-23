package Controller;

import View.LoginMenu.LoginMenuCommands;
import View.TradeMenu.TradeMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController {

    public static String trade(String content, Scanner scanner) {
        Matcher matcher;
        //TODO:check valid resource type
        int price;
        if (!(matcher = TradeMenuCommands.getMatcher(content, TradeMenuCommands.RESOURCE_PRICE_FIELD)).find())
            return "price field is empty";
        else if (matcher.results().count() > 1)
            return "invalid command";
        else {
            price = Integer.parseInt(matcher.group("price"));
            if (price < 0) {
                return null; //TODO
            }
        }
    }

    public void requestTrade(Matcher matcher) {
    }

    public static String showTradeList() {
        return null;
    }

    public static String showTradeHistory() {
        return null;
    }

    public static String acceptTrade(Matcher matcher) {
    }
}
