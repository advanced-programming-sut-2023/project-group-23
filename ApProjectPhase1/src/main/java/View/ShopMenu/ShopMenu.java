package View.ShopMenu;


import Controller.ShopMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if (ShopMenuCommands.getMatcher(command, ShopMenuCommands.SHOW_PRICE_LIST).matches())
                System.out.println(ShopMenuController.showPriceList());
            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.BUY_ITEM)).matches())
                System.out.println(ShopMenuController.buyItem(matcher));
            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.SELL_ITEM)).matches())
                System.out.println(ShopMenuController.sellItem(matcher));
            else if (ShopMenuCommands.getMatcher(command, ShopMenuCommands.BACK).matches()) {
                System.out.println("returned to game menu");
                return;
            }
            else
                System.out.println("invalid command");
        }

    }
}
