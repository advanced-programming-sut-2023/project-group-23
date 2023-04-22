package Controller;

import Model.User;

import java.util.regex.Matcher;

import static Controller.LoginMenuController.isUsernameFormatCorrect;

public class ProfileMenuController {

    public static String change(String string) {
        return null;
    }

    public static String changeUsername(Matcher matcher) {
        String username = matcher.group("username");
        if(username.matches("\\s*"))
            return "please enter a username";
        if(!isUsernameFormatCorrect(username))
            return "username format is not correct";
        User.getCurrentUser().setUsername(username);
        return "Done!";
    }

    public static String changeNickname(Matcher matcher) {
        String nickname = matcher.group("nickname");
        if(nickname.matches("\\s*"))
            return "please enter a nickname";
        User.getCurrentUser().setNickname(nickname);
        return "Done!";
    }

    public static String changePassword(Matcher matcher) {
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

