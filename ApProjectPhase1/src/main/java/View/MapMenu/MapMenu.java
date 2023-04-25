package View.MapMenu;

import Controller.MainMenuController;
import Controller.MapMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu {
    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_MAP)).matches())
                System.out.println(MapMenuController.showMap(matcher));
        }
    }
}
