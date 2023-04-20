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

            if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)) != null)
                System.out.println(ProfileMenuController.changeUsername(matcher));
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE) != null)
                System.out.print(ProfileMenuController.showInfo());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGHSCORE) != null)
                System.out.println(ProfileMenuController.showHighScore());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK) != null)
                System.out.println(ProfileMenuController.showRank());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN) != null)
                System.out.println(ProfileMenuController.showSlogan());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN) != null)
                System.out.println(ProfileMenuController.removeSlogan());
        }
    }
}
