package View.LoginMenu;

import Controller.LoginMenuController;
import Model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {

    public static void run(Scanner scanner) {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.CREATE_USER)).matches())
                System.out.println(LoginMenuController.register(matcher.group("content"), scanner));
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
