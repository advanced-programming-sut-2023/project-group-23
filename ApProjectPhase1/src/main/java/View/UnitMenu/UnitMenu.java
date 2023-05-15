package View.UnitMenu;

import Controller.BuildingMenuController;
import Controller.GameMenuController;
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
                System.out.println(UnitMenuController.moveUnit(matcher, GameMenuController.getCurrentGovernment(), cell));

            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.SET_STATE)).matches())
                System.out.println(UnitMenuController.setState(matcher, GameMenuController.getCurrentGovernment(), cell));

            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.ATTACK)).matches())
                System.out.println(UnitMenuController.attack(matcher, GameMenuController.getCurrentGovernment(), cell));

            else if((matcher = UnitMenuCommands.getMatcher(command, UnitMenuCommands.AIR_ATTACK)).matches())
                System.out.println(UnitMenuController.airAttack(matcher, GameMenuController.getCurrentGovernment(), cell));

            else if(UnitMenuCommands.getMatcher(command, UnitMenuCommands.DISBAND_UNIT).matches())
                System.out.println(UnitMenuController.disbandUnit(GameMenuController.getCurrentGovernment(), cell));

            else
                System.out.println("invalid command");
        }
    }
}
