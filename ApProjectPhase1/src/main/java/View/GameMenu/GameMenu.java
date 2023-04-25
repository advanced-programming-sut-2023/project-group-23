package View.GameMenu;
import Controller.GameMenuController;
import View.ShopMenu.ShopMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {

    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if (GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_SHOP_MENU).matches()) {
                System.out.println("you entered shop menu");
                ShopMenu.run(scanner);
            }
            else System.out.println("invalid command");
        }
    }
}
