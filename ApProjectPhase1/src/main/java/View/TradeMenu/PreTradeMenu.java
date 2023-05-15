package View.TradeMenu;

import Controller.TradeMenuController;
import Model.Game;
import Model.Government;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PreTradeMenu {
    private static Government currentGovernment;

    public static void setCurrentGovernment(Government currentGovernment) {
        PreTradeMenu.currentGovernment = currentGovernment;
    }

    private static Government[] createArrayOfGovernments() {
        int size = 0;
        for (Government government : Game.getCurrentGame().getGovernments()) {
            if (government.equals(currentGovernment)) continue;
            size ++;
        }
        Government[] governments = new Government[size];
        int counter = 0;
        for (Government government : Game.getCurrentGame().getGovernments()) {
            if (government.equals(currentGovernment)) continue;
            governments[counter] = government;
            counter++;
        }
        return governments;
    }

    public static void run(Scanner scanner) {
        String command;
        String notification;
        Matcher matcher;
        String respond;
        Government[] governmentList = createArrayOfGovernments();
        String list = TradeMenuController.listOfPlayers(governmentList);
        Integer playerSelected = 0;
        TradeMenuController.setRequesterGovernment(currentGovernment);
        TradeMenuController.setReceiverGovernment(currentGovernment);
        System.out.println(list);
        while (true) {
            notification = TradeMenuController.notification();
            if (!notification.equals("")) System.out.print(notification);
            command = scanner.nextLine();
            if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.BACK).matches()) {
                System.out.println("returned to game menu");
                return;
            } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.ENTER_PLAYER)).matches()) {
                if (!(respond = TradeMenuController.checkValidNumber(playerSelected = Integer.parseInt(matcher.group("playerNumber")), governmentList)).equals("ok"))
                    System.out.println(respond);
                else {
                    Government receiver = governmentList[playerSelected - 1];
                    System.out.println("now you can trade with " + receiver.getUser().getNickname());
                    TradeMenuController.setReceiverGovernment(receiver);
                    TradeMenu.run(scanner);
                    TradeMenuController.setReceiverGovernment(currentGovernment);
                }
            } else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_LIST).matches())
                System.out.println(TradeMenuController.showTradeList());
            else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_HISTORY).matches())
                System.out.println(TradeMenuController.showTradeHistory());
            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_ACCEPT)).matches())
                System.out.println(TradeMenuController.acceptTrade(matcher));
            else System.out.println("invalid command");
        }
    }
}
