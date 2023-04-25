package View.TradeMenu;

import Controller.TradeMenuController;
import View.MapMenu.MapMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();
            if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_LIST).matches()) {
                System.out.println(TradeMenuController.showTradeList());
            } else if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_HISTORY).matches()) {
                System.out.println(TradeMenuController.showTradeHistory());
            } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE)).matches()) {
                System.out.println(TradeMenuController.trade(matcher.group("content"), scanner));
            } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_ACCEPT)).matches()) {
                System.out.println(TradeMenuController.acceptTrade(matcher));
            } else if (MapMenuCommands.getMatcher(command, MapMenuCommands.BACK).matches()) {
                System.out.println("returned to game menu");
                return;
            } else
                System.out.println("Invalid command!");
        }
    }
}
