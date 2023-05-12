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
            String result;

            if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_MAP)).matches()) {
                result = MapMenuController.showMapInput(matcher);
                if(result != null) System.out.println(result);
            }

            else if((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAILS)).matches()) {
                result = MapMenuController.showDetails(matcher);
                if(result != null) System.out.println(result);
            }

            else if((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.MOVE_MAP)).matches()) {
                result = MapMenuController.moveMap(matcher);
                if(result != null) System.out.println(result);
            }

            else if(MapMenuCommands.getMatcher(command, MapMenuCommands.BACK).matches()) {
                System.out.println("returned to game menu");
                return;
            }

            else
                System.out.println("invalid command");
        }
    }
}
