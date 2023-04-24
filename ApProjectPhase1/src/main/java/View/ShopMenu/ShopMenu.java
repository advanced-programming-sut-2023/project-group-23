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

            if (ShopMenuCommands.getMatcher(command, ShopMenuCommands.SHOW_PRICE_LIST) != null)
                System.out.println(ShopMenuController.showPriceList());
            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.BUY_ITEM)) != null)
                System.out.println(ShopMenuController.buyItem(matcher));
            else if ((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.SELL_ITEM)) != null)
                System.out.println(ShopMenuController.sellItem(matcher));
        }

    }
}
