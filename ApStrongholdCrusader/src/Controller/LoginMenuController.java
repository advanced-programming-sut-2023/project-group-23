package Controller;

import Model.User;
import View.LoginMenu.LoginMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {
    public static boolean isUsernameFormatCorrect(String username){
        return LoginMenuCommands.getMatcher(username, LoginMenuCommands.VALID_USERNAME) != null;
    }

    public static boolean isUserExist(String username){
        return User.getUserByUsername(username) != null;
    }

    public static boolean isEmailExist(String emailForCheck){
        for (String email : User.getEmails()) {
            if(email.equals(emailForCheck))
                return true;
        }
        return false;
    }

    public static String strengthOfPassword(String password){
        if(!(Pattern.compile("[a-z]").matcher(password).find()))
            return "need lower case";
        if(!(Pattern.compile("[A-Z]").matcher(password).find()))
            return "need upper case";
        if(!(Pattern.compile("[0-9]").matcher(password).find()))
            return "need number";
        if(password.length() < 6)
            return "short password";
        if(!(Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()))
            return "need special char";
        return "ok";
    }

    public static String register(Matcher matcher) {
        return null;
    }

    public static String login(Matcher matcher) {
        return null;
    }

    public static String getUserSecurityQuestion(String string) {
        return null;
    }

    public static String isSecurityAnswerCorrect(String string) {
        return null;
    }
}
