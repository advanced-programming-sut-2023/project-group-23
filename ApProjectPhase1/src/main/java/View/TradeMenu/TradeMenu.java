package View.TradeMenu;

import Controller.LoginMenuController;
import Controller.TradeMenuController;
import Model.Game;
import Model.Trade;
import View.GameMenu.GameMenu;
import View.MainMenu.MainMenuCommands;
import View.ProfileMenu.ProfileMenu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();
            if (TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_LIST).matches()) {
                ArrayList<Trade> tradeList = new ArrayList<>();
                //TODO: show list of government trades
            }
            else if(TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_HISTORY).matches()){
                //TODO: show history of current government trades
            }
            else if((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE)).matches()){
                System.out.println(TradeMenuController.trade(matcher.group("content"), scanner));
            }

    }

}
