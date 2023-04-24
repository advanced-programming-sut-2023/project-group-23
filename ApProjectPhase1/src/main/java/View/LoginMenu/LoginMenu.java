package View.LoginMenu;

import Controller.LoginMenuController;
import Model.User;
import View.MainMenu.MainMenu;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {

    public static void run(Scanner scanner) throws IOException {
        String command;
        Matcher matcher;

        if(LoginMenuController.checkForStayLoggedIn()) {
            System.out.println("logged in as user: " + User.getCurrentUser().getUsername());
            MainMenu.run(scanner);
        }

        while (true) {
            command = scanner.nextLine();

            if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.CREATE_USER)).matches())
                System.out.println(LoginMenuController.register(matcher.group("content"), scanner));

            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.LOGIN_USER)).matches()) {
                String result = LoginMenuController.login(matcher.group("content"));
                System.out.println(result);
                if(result.equals("log in successful"))
                    MainMenu.run(scanner);
            }

            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGOT_PASSWORD)).matches()) {
                String username = matcher.group("username").replace("\"", "");
                String result = LoginMenuController.forgotPassword(username, scanner);
                System.out.println(result);
                if(result.equals("set your new password")) {
                    command = scanner.nextLine();
                    if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.SET_NEW_PASSWORD)).matches())
                        System.out.println(LoginMenuController.setNewPassword(matcher, username, scanner));
                    else
                        System.out.println("invalid command");
                }
            }

            else if(LoginMenuCommands.getMatcher(command, LoginMenuCommands.EXIT).matches())
                return;

            else
                System.out.println("invalid command");
        }
    }

    public static String setPasswordRandomly(Scanner scanner) {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQURESTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqurestuvwxyz";
        String digits = "0123456789";
        String specialCharacters = ".?@#$%!^&*_+<>(){}[]|";

        StringBuilder passwordBuilder = new StringBuilder();
        int index;
        for(int i = 0 ; i < 4 ; i++) {
            index = (int) (uppercaseLetters.length() * Math.random());
            passwordBuilder.append(uppercaseLetters.charAt(index));

            index = (int) (lowercaseLetters.length() * Math.random());
            passwordBuilder.append(lowercaseLetters.charAt(index));

            index = (int) (digits.length() * Math.random());
            passwordBuilder.append(digits.charAt(index));

            index = (int) (specialCharacters.length() * Math.random());
            passwordBuilder.append(specialCharacters.charAt(index));
        }
        String generatedPassword = passwordBuilder.toString();

        System.out.println("Your random password is: " + generatedPassword);
        System.out.println("Please re-enter your password here:");

        String passwordConfirmation = scanner.nextLine();
        if(LoginMenuController.passwordsMatch(generatedPassword, passwordConfirmation))
            return generatedPassword;
        else
            return null;
    }

    public static String pickSecurityQuestion(Scanner scanner) {
        System.out.println("Pick your security question:");
        for(int i = 0 ; i < 3 ; i++) {
            System.out.println(i + ". " + User.getQuestionByKey(i));
        }

        String command = scanner.nextLine();
        Matcher matcher;

        if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.QUESTION_PICK)).matches())
            return matcher.group("content");
        else
            return "invalid command";
    }

    private static void forgotMyPassword(Scanner scanner) {

    }
}
