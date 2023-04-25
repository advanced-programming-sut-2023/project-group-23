package View.MainMenu;

import Controller.MainMenuController;
import View.GameMenu.GameMenu;
import View.ProfileMenu.ProfileMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {

    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();
            if (MainMenuCommands.getMatcher(command, MainMenuCommands.LOGOUT).matches()) {
                System.out.println("you logged out");
                return;
            }
            else if (MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_PROFILE_MENU).matches()) {
                System.out.println("you entered profile menu");
                ProfileMenu.run(scanner);
            }
            else if (MainMenuCommands.getMatcher(command, MainMenuCommands.START_NEW_GAME).matches()) {
                System.out.println();
                GameMenu.run(scanner);
            }
        }
    }
}
