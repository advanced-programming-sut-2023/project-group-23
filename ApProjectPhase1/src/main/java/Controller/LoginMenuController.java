package Controller;

import Model.User;
import View.LoginMenu.LoginMenu;
import View.LoginMenu.LoginMenuCommands;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.sun.tools.javac.Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public static boolean isQuestionNumberFormatCorrect(String questionNUmber) {
        return LoginMenuCommands.getMatcher(questionNUmber, LoginMenuCommands.VALID_QUESTION_NUMBER).matches();
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
        int randomKey = random.nextInt() % 3;
        String slogan = User.getRandomSloganByKey(randomKey);
        System.out.println("Your slogan is \"" + slogan + "\"");
        return slogan;
    }

    public static String checkForPickQuestionError(String content) {
        Matcher matcher;

        if(!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.QUESTION_NUMBER_FIELD)).find())
            return "question number field is empty";
        else if(matcher.results().count() > 1)
            return "invalid command";
        else if(!isQuestionNumberFormatCorrect(matcher.group("questionNumber")))
            return "question number format is invalid";
        int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
        if(questionNumber > 3 || questionNumber < 1)
            return "question number is out of bounds";

        if(!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.ANSWER_FIELD)).find())
            return "answer field is empty";
        else if(matcher.results().count() > 1)
            return "invalid command";
        String answer = matcher.group("answer").replace("\"", "");

        if(!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.ANSWER_CONFIRMATION_FIELD)).find())
            return "answer confirmation field is empty";
        else if(matcher.results().count() > 1)
            return "invalid command";
        String answerConfirmation = matcher.group("answer").replace("\"", "");

        if(!answerConfirmation.equals(answer))
            return "answer confirmation doesn't match answer";

        return null;
    }

    /*public static String createUser(Matcher matcher, String command){
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
    }*/

    public static String register(String content, Scanner scanner) throws IOException {
        Matcher matcher;

        String username = "";
        if (!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.USERNAME_FIELD)).find())
            return "username field is empty";
        else if (Controller.findCounter(matcher) > 1) return "invalid command";
        else {
            if (matcher.find(0)) username = matcher.group("username").replace("\"", "");

            if (!isUsernameFormatCorrect(username))
                return "username format is not correct";
            if (isUserExist(username)) {
                Integer number = 0;
                String copyOfUsername = username;
                while (isUserExist(copyOfUsername)) {
                    number++;
                    copyOfUsername = username + number;
                }
                String usernameSuggestion = username + number;
                return "this username already exists\nyou can choose \"" + usernameSuggestion + "\" instead of " + username;
            }
        }

        String password = "";
        boolean isPasswordRandom = false;
        if (!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.PASSWORD_FIELD)).find())
            return "password field is empty";
        else if (Controller.findCounter(matcher) > 1) return "invalid command";
        else {
            if (matcher.find(0)) password = matcher.group("password").replace("\"", "");

            if (!isPasswordFormatCorrect(password))
                return "password format is not correct";

            if (password.equals("random"))
                isPasswordRandom = true;
            else if (!strengthOfPassword(password).equals("ok"))
                return strengthOfPassword(password);
        }

        Matcher matcher1;
        matcher1 = LoginMenuCommands.getMatcher(content, LoginMenuCommands.PASSWORD_CONFIRM_FIELD);
        if (!isPasswordRandom && !(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.PASSWORD_CONFIRM_FIELD)).find())
            return "password confirmation field is empty";
        else if (Controller.findCounter(matcher1) > 1) return "invalid command";
        else if (!isPasswordRandom && !passwordsMatch(password, matcher.group("password").replace("\"", "")))
            return "password and password confirmation are not the same";

        String email = "";
        if (!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.EMAIL_FIELD)).find())
            return "email field is empty";
        else if (Controller.findCounter(matcher) > 1) return "invalid command";
        else {
            if (matcher.find(0)) email = matcher.group("email").replace("\"", "");

            if (!isEmailFormatCorrect(email))
                return "email format is not correct";
            if (isEmailExist(email))
                return "this email has been used";
        }

        String nickname = "";
        if (!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.NICKNAME_FIELD)).find())
            return "nickname field is empty";
        else if (Controller.findCounter(matcher) > 1) return "invalid command";
        else {
            if (matcher.find(0)) nickname = matcher.group("nickname").replace("\"", "");

            if (!isNicknameCorrect(nickname))
                return "nickname format is not correct";
            if (isNicknameExist(nickname))
                return "this nickname has been used";
        }

        String slogan = "";
        boolean isSloganRandom = false;
        if (Pattern.compile("-s((\\s*$)|(\\s*-))").matcher(content).find())
            return "slogan field is empty";
        matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.SLOGAN_FIELD);
        if (Controller.findCounter(matcher) > 1) return "invalid command";
        if (matcher.find(0)) {
            slogan = matcher.group("slogan").replace("\"", "");

            isSloganRandom = slogan.equals("random");
        }

        if (isSloganRandom)
            slogan = setSloganRandomly();

        if (isPasswordRandom)
            if ((password = LoginMenu.setPasswordRandomly(scanner)) == null)
                return "password and password confirmation are not the same";

        String securityQuestionContent = LoginMenu.pickSecurityQuestion(scanner);

        if (securityQuestionContent.equals("invalid command"))
            return "invalid command";

        String message = checkForPickQuestionError(content);

        if (message != null)
            return message;

        matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.QUESTION_NUMBER_FIELD);
        int questionNumber = Integer.parseInt(matcher.group("questionNumber"));

        matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.ANSWER_FIELD);
        String answer = matcher.group("answer");

        User.addUser(new User(username, password, nickname, slogan, email, questionNumber, answer));

        User.updateDatabase();

        return "register successfully";
    }

    public static String login(String content) throws IOException {
        Matcher matcher;
        if (!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.USERNAME_FIELD)).find())
            return "username field is empty";
        if (Controller.findCounter(matcher) > 1) return "invalid command";
        String username = "";
        if (matcher.find(0)) username = matcher.group("username").replace("\"", "");
        if (!(matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.PASSWORD_FIELD)).find())
            return "password field is empty";
        if (Controller.findCounter(matcher) > 1) return "invalid command";
        String password = "";
        if (matcher.find(0)) password = matcher.group("password").replace("\"", "");
        if (!isUserExist(username))
            return "this username does not exists";
        if(!User.getUserByUsername(username).isPasswordCorrect(password))
            return "username and password didn't match!";

        if((matcher = LoginMenuCommands.getMatcher(content, LoginMenuCommands.STAY_LOGGED_IN_FLAG)).find()) {
            if (Controller.findCounter(matcher) > 1) return "invalid command";

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter("stayLoggedInData.json");
            gson.toJson(User.getUserByUsername(username), writer);
            writer.close();
        }

        User user = User.getUserByUsername(username);
        User.setCurrentUser(user);
        return "log in successful";
    }

    public static boolean checkForStayLoggedIn() throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader reader = new JsonReader(new FileReader("stayLoggedInData.json"));
        User user = gson.fromJson(reader, User.class);
        if(user != null) {
            User.setCurrentUser(user);
            return true;
        }
        return false;
    }

    public static String forgotPassword(String username, Scanner scanner) {
        if(!isUserExist(username))
            return "no user with this username exists";

        User user = User.getUserByUsername(username);
        System.out.println(user.getUserSecurityQuestion());
        String answer = scanner.nextLine();
        if(!user.getUserAnswerToSecurityQuestion().equals(answer))
            return "wrong answer";

        return "set your new password";
    }

    public static String setNewPassword(Matcher matcher, String username, Scanner scanner) {
        User user = User.getUserByUsername(username);
        String password = matcher.group("password").replace("\"", "");
        boolean isPasswordRandom = false;
        if (!isPasswordFormatCorrect(password))
            return "password format is not correct";
        if (password.equals("random"))
            isPasswordRandom = true;
        else if (!strengthOfPassword(password).equals("ok"))
            return strengthOfPassword(password);

        if (isPasswordRandom) {
            if ((password = LoginMenu.setPasswordRandomly(scanner)) == null)
                return "password and password confirmation are not the same";
        }
        else {
            String passwordConfirmation = matcher.group("passwordConfirmation").replace("\"", "");
            if(!password.equals(passwordConfirmation))
                return "password and password confirmation are not the same";
        }

        user.setPassword(password);
        return "password changed successfully";
    }

    public static String getUserSecurityQuestion(String string) {
        return null;
    }

    public static String isSecurityAnswerCorrect(String string) {
        return null;
    }
}