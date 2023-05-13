package Controller;

import Model.Government;
import Model.ResourceType;
import Model.Trade;
import Model.User;
import View.TradeMenu.TradeMenuCommands;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
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

    public static String trade(String content) {
        Matcher matcher;
        ResourceType resourceType = null;

        if (!(matcher = TradeMenuCommands.getMatcher(content, TradeMenuCommands.RESOURCE_TYPE_FIELD)).find())
            return "resource type field is empty";
        else {
            for (ResourceType value : ResourceType.values()) {
                if(matcher.group("resourceType").equals(value.name())) {
                    resourceType = value;
                    break;
                }
            }
        }
        if(resourceType == null)
            return "resource type in not valid";

        int price;
        if (!(matcher = TradeMenuCommands.getMatcher(content, TradeMenuCommands.RESOURCE_PRICE_FIELD)).find())
            return "price field is empty";
        //TODO:CHECK COUNT OF FIELDS
        else {
            price = Integer.parseInt(matcher.group("price"));
            if (price < 0) {
                return "price must not less than zero";
            }
        }

        int amount;
        if (!(matcher = TradeMenuCommands.getMatcher(content, TradeMenuCommands.RESOURCE_AMOUNT_FIELD)).find())
            return "resource amount field is empty";
        else {
            amount = Integer.parseInt(matcher.group("resourceAmount"));
            if (amount <= 0) {
                return "resource amount must not less than or equal to zero";
            }
        }
        matcher = TradeMenuCommands.getMatcher(content, TradeMenuCommands.MESSAGE_FIELD);
        String message = matcher.group("message");
        Trade trade = new Trade(price, message, resourceType, amount, requesterGovernment, receiverGovernment);
        receiverGovernment.addToTradeHistory(trade);
        requesterGovernment.addToTradeList(trade);
        return "your request has been sent to the desired government";
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