package View.UnitMenu;

import Controller.BuildingMenuController;
import Controller.UnitMenuController;
import Model.Game;
import Model.MapCell;
import View.BuildlingMenu.BuildingMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class UnitMenu {
    public static void run(Scanner scanner, Game game, MapCell cell) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if(BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.BACK).matches()) {
                System.out.println("back to game menu");
                return;
            }

            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.MOVE_UNIT)).matches())
                System.out.println(UnitMenuController.moveUnit(matcher, game, cell));

            else
                System.out.println("invalid command");
        }
    }
}
