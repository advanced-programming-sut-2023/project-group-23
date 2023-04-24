package View.MainMenu;

import Controller.MainMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {

    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();
            if (MainMenuCommands.getMatcher(command, MainMenuCommands.LOGOUT) != null)
                System.out.println(MainMenuController.logout());
            else if (MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_PROFILE_MENU) != null)
                System.out.println(MainMenuController.enterProfileMenu());
            else if (MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_SHOP_MENU) != null)
                System.out.println(MainMenuController.enterShopMenu());
            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_NEW_GAME)) != null)
                System.out.println(MainMenuController.startNewGame(matcher));
        }
    }
}
