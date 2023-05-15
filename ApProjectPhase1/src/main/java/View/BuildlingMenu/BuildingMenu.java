package View.BuildlingMenu;

import Controller.BuildingMenuController;
import Model.Buildings.Building;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {
    public static void run(Scanner scanner, Building building) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if(BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.BACK).matches()) {
                System.out.println("back to game menu");
                return;
            }

            else if(BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.REPAIR).matches())
                System.out.println(BuildingMenuController.repair(building));

            else if((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.CREATEUNIT)).matches())
                System.out.println(BuildingMenuController.createunit(building, matcher));

            else
                System.out.println("invalid command");
        }
    }
}
