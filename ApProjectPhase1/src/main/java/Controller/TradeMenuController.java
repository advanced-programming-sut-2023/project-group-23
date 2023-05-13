package Controller;

import Model.Government;
import Model.User;
import View.TradeMenu.TradeMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController {
    private static Government receiverGovernment;
    private static Government requesterGovernment;

    public static void setReceiverGovernment(Government receiverGovernment) {
        receiverGovernment = receiverGovernment;
    }

    public static void setRequesterGovernment(Government requesterGovernment) {
        requesterGovernment = requesterGovernment;
    }

    public static String checkValidNumber(Integer number, Government[] governments) {
        if (number > governments.length || number.equals(0)) return "invalid command";
        return "ok";
    }

    public static String listOfPlayers(Government[] governments) {
        String showGovernments = "players:\n";
        for (int i = 0; i < governments.length; i++) {
            showGovernments += ((i + 1) + ". player Nickname : " + governments[i].getUser().getNickname());
            if (i < governments.length - 1) showGovernments += "\n";
        }
        return showGovernments;
    }

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
        return null;
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
        return null;
    }
}