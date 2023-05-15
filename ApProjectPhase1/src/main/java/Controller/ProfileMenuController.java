package Controller;

import Model.User;

import java.io.IOException;
import java.util.regex.Matcher;

import Controller.LoginMenuController;
import View.ProfileMenu.ProfileMenuCommands;

import static Controller.LoginMenuController.isUsernameFormatCorrect;

public class ProfileMenuController {

    public static String change(String string) {
        return null;
    }

    public static String changeUsername(Matcher matcher) {
        String username = matcher.group("username");
        username = Controller.deleteWhiteSpacesOfEnd(username);
        if(username.matches("\\s+"))
            return "please enter a username";
        if(!isUsernameFormatCorrect(username))
            return "username format is not correct";
        User.getCurrentUser().setUsername(username);
        return "Done!";
    }

    public static String changeNickname(Matcher matcher) {
        String nickname = matcher.group("nickname");
        nickname = Controller.deleteWhiteSpacesOfEnd(nickname);
        if(nickname.matches("\\s+"))
            return "please enter a nickname";
        User.getCurrentUser().setNickname(nickname);
        return "Done!";
    }

    public static String changePassword(Matcher matcher) throws IOException {
        String passwords = matcher.group(1);
        String oldPassword = "";
        String newPassword = "";
        if (!(matcher = ProfileMenuCommands.getMatcher(passwords, ProfileMenuCommands.OLD_PASSWORD_FIELD)).find())
            return "you must enter your old password";
        oldPassword = matcher.group("oldpassword").replace("\"", "");
        if (!User.getCurrentUser().isPasswordCorrect(oldPassword))
            return "wrong password";
        if (!(matcher = ProfileMenuCommands.getMatcher(passwords, ProfileMenuCommands.NEW_PASSWORD_FIELD)).find())
            return "you must enter a new password";
        newPassword = matcher.group("newpassword").replace("\"", "");
        if (newPassword.equals(oldPassword)) return "enter a new password";
        if (!LoginMenuController.isPasswordFormatCorrect(newPassword))
            return "password format is not correct";
        if (!LoginMenuController.strengthOfPassword(newPassword).equals("ok"))
            return LoginMenuController.strengthOfPassword(newPassword);
        User.getCurrentUser().setPassword(newPassword);
        return "Done!";
    }

    public static String changeEmail(Matcher matcher) {
        String email = matcher.group("email");
        email = Controller.deleteWhiteSpacesOfEnd(email);
        if (email.matches("\\s+")) return "Email must doesn't have any space";
        else if (!LoginMenuController.isEmailFormatCorrect(email)) return "Email format isn't correct";
        else if (!LoginMenuController.isEmailExist(email)) return "This email already exists";
        User.getCurrentUser().setEmail(email);
        return "Done!";
    }

    public static String changeSlogan(Matcher matcher) {
        String slogan = matcher.group("slogan");
        slogan = Controller.deleteWhiteSpacesOfEnd(slogan);
        slogan = slogan.replace("\"", "");
        User.getCurrentUser().setSlogan(slogan);
        return "Done!";
    }

    public static Integer showHighScore() {
        return User.getCurrentUser().getUserHighScore();
    }

    public static Integer showRank() {
        return User.userRank();
    }

    public static String showSlogan() {
        if (User.getCurrentUser().getSlogan() != null) return User.getCurrentUser().getSlogan();
        return "Slogan is empty!";
    }

    public static String showInfo() {
        String profileInfo = "User Info:\n";
        profileInfo += "Username: " + User.getCurrentUser().getUsername() + "\n";
        profileInfo += "Nickname: " + User.getCurrentUser().getNickname() + "\n";
        profileInfo += "User ranking: " + User.userRank() + "\n";
        if (User.getCurrentUser().getSlogan() != null) profileInfo += "Slogan: " + User.getCurrentUser().getSlogan() + "\n";
        return profileInfo;
    }

    public static String removeSlogan() {
        User.getCurrentUser().setSlogan(null);
        return "Your slogan removed successfully";
    }
}

