package Controller;

import Model.*;
import View.TradeMenu.TradeMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenuController {
    private static Government receiverGovernment;
    private static Government requesterGovernment;

    public static void setReceiverGovernment(Government receiverGovernment) {
        TradeMenuController.receiverGovernment = receiverGovernment;
    }

    public static void setRequesterGovernment(Government requesterGovernment) {
        TradeMenuController.requesterGovernment = requesterGovernment;
    }

    public static String notification() {
        String result = "";
        if (receiverGovernment.getTradeList() == null) return result;
        for (Trade trade : receiverGovernment.getTradeList()) {
            if (!trade.isShowed()) {
                result += ("you hava a new trade request from " + trade.getRequester().getUser().getNickname() + "\n");
                trade.setShowed(true);
            }
        }
        return result;
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
                if (matcher.group("resourceType").equals(value.getName())) {
                    resourceType = value;
                    break;
                }
            }
        }
        if (resourceType == null)
            return "resource type in not valid";

        int price;
        if (!(matcher = TradeMenuCommands.getMatcher(content, TradeMenuCommands.RESOURCE_PRICE_FIELD)).find())
            return "price field is empty";
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
                return "resource amount can not be less than or equal to zero";
            }
        }
        matcher = TradeMenuCommands.getMatcher(content, TradeMenuCommands.MESSAGE_FIELD);
        String message = null;
        if (matcher.find()) message = matcher.group("message").replace("\"", "");
        if (message == null) return "you must enter a message";
        else {
            Trade trade = new Trade(price, message, resourceType, amount, requesterGovernment, receiverGovernment);
            requesterGovernment.addToTradeHistory(trade);
            receiverGovernment.addToTradeList(trade);
            return "your request has been sent to the desired government";
        }
    }

    public static String showTradeList() {
        String tradeList = "trade list:";
        ArrayList<Trade> trades = requesterGovernment.getTradeList();
        if (trades.size() == 0) return tradeList;
        else tradeList += "\n";
        return getOutput(tradeList, trades);
    }

    public static String showTradeHistory() {
        String tradeList = "trade history:";
        ArrayList<Trade> trades = requesterGovernment.getTradeHistory();
        if (trades.size() == 0) return tradeList;
        else tradeList += "\n";
        return getOutput(tradeList, trades);
    }


    private static String getOutput(String tradeList, ArrayList<Trade> trades) {
        for (int i = 0; i < trades.size(); i++) {
            if (trades.get(i).getPrice() != 0)
                tradeList += ("trade no." + (i + 1) + " type: " + trades.get(i).getResourceType() + " price: " +
                        trades.get(i).getPrice() + " amount: " + trades.get(i).getResourceAmount() + " requester message: " +
                        trades.get(i).getRequesterMessage() + " receiver message: " + trades.get(i).getReceiverMessage());
            else
                tradeList += ("trade no." + (i + 1) + " type: " + trades.get(i).getResourceType() + " # donate request # " +
                        trades.get(i).getPrice() + " amount: " + trades.get(i).getResourceAmount() + " requester message: " +
                        trades.get(i).getRequesterMessage() + " receiver message: " + trades.get(i).getReceiverMessage());
            if (i < trades.size() - 1) tradeList += "\n";
        }
        return tradeList;
    }


    public static String acceptTrade(Matcher matcher) {
        String acceptReject = matcher.group(1);
        Matcher matcher1, matcher2;
        Integer id = null;
        String receiverMessage = null;
        matcher1 = TradeMenuCommands.getMatcher(matcher.group("content"), TradeMenuCommands.ID_FIELD);
        if (!matcher1.find()) return "id field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        if (matcher1.find(0)) {
            matcher2 = TradeMenuCommands.getMatcher(matcher1.group("id"), TradeMenuCommands.ID_VALIDITY);
            if (matcher2.find()) id = Integer.parseInt(matcher2.group(1));
            else return "id is an integer";
            if (id <= 0 || id > receiverGovernment.getTradeList().size())
                return "there isn't any trade request with this id";
        }
        matcher1 = TradeMenuCommands.getMatcher(matcher.group(4), TradeMenuCommands.MESSAGE_FIELD);
        if (!matcher1.find()) return "message field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        else {
            if (matcher1.find(0)) receiverMessage = matcher1.group(1).replace("\"", "");
        }
        Trade trade = receiverGovernment.getTradeList().get(id - 1);
        if (trade.getPrice() > trade.getRequester().getGold()) return "the requester doesn't have enough gold";
        if (acceptReject.equals("reject")) {
            trade.setAccepted(-1);
            trade.setReceiverMessage(receiverMessage);
            receiverGovernment.getTradeList().remove(trade);
            receiverGovernment.getTradeHistory().add(trade);
            return "trade rejected";
        }
        int newAmount = 0;
        String resourceName = trade.getResourceType().getName();
        FoodType foodType = isFood(resourceName);
        ResourceType resourceType = getResourceByName(resourceName);
        if (foodType != null) {
            newAmount = trade.getRequester().getFoodAmountByFood(foodType) + trade.getResourceAmount();
            if (trade.getRequester().getMaxFoodStorage() >= newAmount) {
                if (receiverGovernment.getFoodAmountByFood(foodType) < trade.getResourceAmount()) return "receiver doesn't have enough resource";
                trade.getRequester().changeFoodAmount(foodType, newAmount);
                receiverGovernment.changeFoodAmount(foodType, receiverGovernment.getFoodAmountByFood(foodType) - trade.getResourceAmount());
                trade.getRequester().setGold(trade.getRequester().getGold() - trade.getPrice());
                receiverGovernment.setGold(receiverGovernment.getGold() + trade.getPrice());
            } else return "requester doesn't have enough space for this resource";
        } else {
            newAmount = trade.getRequester().getAmountByResource(resourceType) + trade.getResourceAmount();
            if (trade.getRequester().getMaxResourceStorage() >= newAmount) {
                if (receiverGovernment.getAmountByResource(resourceType) < trade.getResourceAmount()) return "receiver doesn't have enough resource";
                trade.getRequester().changeAmountOfResource(resourceType, newAmount);
                receiverGovernment.changeAmountOfResource(resourceType, receiverGovernment.getAmountByResource(resourceType) - trade.getResourceAmount());
                trade.getRequester().setGold(trade.getRequester().getGold() - trade.getPrice());
                receiverGovernment.setGold(receiverGovernment.getGold() + trade.getPrice());
            } else return "requester doesn't have enough space for this resource";
        }
        trade.setAccepted(1);
        trade.setReceiverMessage(receiverMessage);
        receiverGovernment.getTradeList().remove(trade);
        receiverGovernment.getTradeHistory().add(trade);
        return "trade done successfully";
    }

    private static FoodType isFood(String name) {
        for (FoodType value : FoodType.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }

    private static ResourceType getResourceByName(String name) {
        for (ResourceType value : ResourceType.values()) {
            if (value.getName().equals(name)) return value;
        }
        return null;
    }
}