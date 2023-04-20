package Controller;

import Model.User;

import java.util.regex.Matcher;

public class ProfileMenuController {

    public static String change(String string) {
        return null;
    }

    public static String changeUsername(Matcher matcher) {

        return null;
    }

    public static String changePassword(Matcher matcher) {
        return null;
    }

    public static String changeNickname(Matcher matcher) {
        return null;
    }

    public static String changeEmail(Matcher matcher) {
        return null;
    }

    public static String changeSlogan(Matcher matcher) {
        return null;
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

