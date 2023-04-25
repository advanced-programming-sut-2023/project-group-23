package View.ProfileMenu;

import Controller.ProfileMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)).matches())
                System.out.println(ProfileMenuController.changeUsername(matcher));
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE).matches())
                System.out.print(ProfileMenuController.showInfo());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGHSCORE).matches())
                System.out.println(ProfileMenuController.showHighScore());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK).matches())
                System.out.println(ProfileMenuController.showRank());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN).matches())
                System.out.println(ProfileMenuController.showSlogan());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN).matches())
                System.out.println(ProfileMenuController.removeSlogan());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.BACK).matches()) {
                System.out.println("you entered to ");
                return;
            }
            else System.out.println("invalid command");
        }
    }
}
