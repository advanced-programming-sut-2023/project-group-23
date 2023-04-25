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

            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SETTEXTTURE)).matches())
                MapMenuController.setTextture(matcher);

            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SETTEXTTURE_MORE_THAN_ONE)).matches())
                MapMenuController.setTextture(matcher);

            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DROP_TREE)).matches())
                MapMenuController.dropTree(matcher);

            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DROP_ROCK)).matches())
                MapMenuController.dropRock(matcher);

            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.CLEAR)).matches())
                MapMenuController.clearBlock(matcher);

            else if((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAILS)).matches())
                System.out.println(MapMenuController.showDetails(matcher));

            else if((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.MOVE_MAP)).matches())
                System.out.println(MapMenuController.moveMap(matcher));

            else if(MapMenuCommands.getMatcher(command, MapMenuCommands.BACK).matches()) {
                System.out.println("returned to game menu");
                return;
            }

            else
                System.out.println("invalid command");
        }
    }
}
