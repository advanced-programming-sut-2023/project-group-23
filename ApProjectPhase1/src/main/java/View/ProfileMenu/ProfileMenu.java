package View.ProfileMenu;

import Controller.ProfileMenuController;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    public static void run(Scanner scanner) throws IOException {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)).matches())
                System.out.println(ProfileMenuController.changeUsername(matcher));
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_EMAIL)).matches())
                System.out.println(ProfileMenuController.changeEmail(matcher));
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_NICKNAME)).matches())
                System.out.println(ProfileMenuController.changeNickname(matcher));
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD)).matches())
                System.out.println(ProfileMenuController.changePassword(matcher, scanner));
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_SLOGAN)).matches())
                System.out.println(ProfileMenuController.changeSlogan(matcher));
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
                System.out.println("you entered to main menu");
                return;
            }
            else System.out.println("invalid command");
        }
    }
}
