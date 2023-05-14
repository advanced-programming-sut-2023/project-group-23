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

            if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE)).matches())
                System.out.println(TradeMenuController.trade(matcher.group("content")));
            else if (MapMenuCommands.getMatcher(command, MapMenuCommands.BACK).matches())
                return;
            else System.out.println("Invalid command!");
        }
    }
}