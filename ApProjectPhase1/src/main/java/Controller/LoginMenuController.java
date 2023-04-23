package Controller;

import Model.User;
import View.LoginMenu.LoginMenu;
import View.LoginMenu.LoginMenuCommands;
import com.sun.tools.javac.Main;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuController {
    public static boolean isUsernameFormatCorrect(String username){
        return LoginMenuCommands.getMatcher(username, LoginMenuCommands.VALID_USERNAME).matches();
    }

    public static boolean isPasswordFormatCorrect(String password) {
        return LoginMenuCommands.getMatcher(password, LoginMenuCommands.VALID_PASSWORD).matches();
    }

    public static boolean isEmailFormatCorrect(String email){
        return LoginMenuCommands.getMatcher(email, LoginMenuCommands.VALID_EMAIL).matches();
    }

    public static boolean isNicknameCorrect(String nickname) {
        return LoginMenuCommands.getMatcher(nickname, LoginMenuCommands.VALID_NICKNAME).matches();
    }

    public static boolean isUserExist(String username){
        return User.getUserByUsername(username) != null;
    }

    public static boolean isEmailExist(String emailForCheck){
        return User.getUserByEmail(emailForCheck) != null;
    }

    public static boolean isNicknameExist(String nickname) {
        return User.getUserByNickname(nickname) != null;
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

    public static String setSloganRandomly() {
        Random random = new Random();
        int randomKey = random.nextInt();
        String slogan = User.getRandomSloganByKey(randomKey);
        System.out.println("Your slogan is \"" + slogan + "\"");
        return slogan;
    }

    public static String checkForPickQuestionError(String content) {

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
        return null;
    }

    public static String register(String content, Scanner scanner) {
        Matcher matcher;

        if (!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.USERNAME_FIELD)).find())
            return "username field is empty";
        else {
            String username = matcher.group("username").replace("\"", "");

            if (!isUsernameFormatCorrect(username))
                return "username format is not correct";
            if (isUserExist(username))
                return "this username already exists";
        }

        String password;
        boolean isPasswordRandom = false;
        if (!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.PASSWORD_FIELD)).find())
            return "password field is empty";
        else {
            password = matcher.group("password").replace("\"", "");

            if (!isPasswordFormatCorrect(password))
                return "password format is not correct";

            if(password.equals("random"))
                isPasswordRandom = true;
            else if (strengthOfPassword(password).equals("ok"))
                return strengthOfPassword(password);
        }

        if (!isPasswordRandom && !(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.PASSWORD_CONFIRM_FIELD)).find())
            return "password confirmation field is empty";
        else if (!isPasswordRandom && !passwordsMatch(password, matcher.group("password").replace("\"", "")))
            return "password and password confirmation are not the same";

        if(!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.EMAIL_FIELD)).find())
            return "email field is empty";
        else {
            String email = matcher.group("email").replace("\"", "");

            if(!isEmailFormatCorrect(email))
                return "email format is not correct";
            if(isEmailExist(email))
                return "this email has been used";
        }

        if(!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.NICKNAME_FIELD)).find())
            return "nickname field is empty";
        else {
            String nickname = matcher.group("nickname").replace("\"", "");

            if(!isNicknameCorrect(nickname))
                return "nickname format is not correct";
            if(isNicknameExist(nickname))
                return "this nickname has been used";
        }

        String slogan = "";
        boolean isSloganRandom = false;
        if (Pattern.compile("-s\\s*$").matcher(content).find())
            return "slogan field is empty";

        if((matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.SLOGAN_FIELD)).find()) {
            slogan = matcher.group("slogan").replace("\"", "");

            isSloganRandom = slogan.equals("random");
        }

        if(isSloganRandom)
            slogan = setSloganRandomly();

        if(isPasswordRandom)
            if((password = LoginMenu.setPasswordRandomly(scanner)) == null)
                return "password and password confirmation are not the same";

        String securityQuestionContent = LoginMenu.pickSecurityQuestion(scanner);

        if(securityQuestionContent.equals("invalid command"))
            return "invalid command";

        String message = checkForPickQuestionError(content);

        if(message != null)
            return message;

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