package Controller;

import Model.User;
import View.LoginMenu.LoginMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {
    public static boolean isUsernameFormatCorrect(String username){
        return LoginMenuCommands.getMatcher(username, LoginMenuCommands.VALID_USERNAME) != null;
    }

    public static boolean isEmailFormatCorrect(String email){
        return LoginMenuCommands.getMatcher(email, LoginMenuCommands.VALID_EMAIL) != null;
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
            return "password is weak : need lower case";
        if(!(Pattern.compile("[A-Z]").matcher(password).find()))
            return "password is weak : need upper case";
        if(!(Pattern.compile("[0-9]").matcher(password).find()))
            return "password is weak : need number";
        if(password.length() < 6)
            return "password is weak : short password";
        if(!(Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()))
            return "password is weak : need special character";
        return "ok";
    }

    public static boolean passwordsMatch(String password, String passwordConfirmation){
        return password.equals(passwordConfirmation);
    }

    public static String createUser(Matcher matcher, String command){
        String username = matcher.group("username");
        String password = matcher.group("password");
        String passwordConfirmation = matcher.group("passwordConfirmation");
        String email = matcher.group("email");
        String nickname = matcher.group("nickname");
        String slogan = "";
        if(Pattern.compile("(\\s+-s\\s+(?<slogan>.+))").matcher(command).find())
            slogan = matcher.group("slogan");
        if(username.matches("\\s*"))
            return "please enter a username";
        if(password.matches("\\s*"))
            return "please enter a password";
        if(nickname.matches("\\s*"))
            return "please enter a nickname";
        if(email.matches("\\s*"))
            return "please enter an email";
        if(!isUsernameFormatCorrect(username))
            return "username format is not correct";
        if(isUserExist(username))
            return "this username already exists";
        if(!strengthOfPassword(password).equals("ok"))
            return strengthOfPassword(password);
        if(!passwordsMatch(password, passwordConfirmation))
            return "password and password confirmation are not the same";
        if(isEmailExist(email))
            return "this email has been used";
        if(!isEmailFormatCorrect(email))
            return "email format is not correct";

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
