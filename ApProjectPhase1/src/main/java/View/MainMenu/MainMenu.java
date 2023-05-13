package View.MainMenu;

import Controller.MainMenuController;
import View.GameMenu.GameMenu;
import View.PreGameMenu.PreGameMenu;
import View.ProfileMenu.ProfileMenu;
import View.TradeMenu.TradeMenu;

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
            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_NEW_GAME)).matches()) {
                String result = MainMenuController.startNewGame(matcher.group("content"));
                System.out.println(result);
                if(result.equals("set your game settings"))
                    PreGameMenu.run(scanner);
            }
            else if (MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_TRADE_MENU).matches()) {
                System.out.println("you entered trade menu");
                System.out.println("list of players:");
                //TODO: show a list of players
                TradeMenu.run(scanner);
            }
        }
    }
}
