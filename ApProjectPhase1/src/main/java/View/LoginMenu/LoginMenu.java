package View.LoginMenu;

import Controller.LoginMenuController;
import Model.User;
import View.MainMenu.MainMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Application {

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

            else if(LoginMenuCommands.getMatcher(command, LoginMenuCommands.EXIT).matches()) {
                LoginMenuController.exit();
                return;
            }

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
        for(int i = 1 ; i <=  3 ; i++) {
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
    @FXML
    private Label wrongUsername;
    @FXML
    private Label wrongPassword;

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(new URL(LoginMenu.class.getResource("/View/Login.fxml").toExternalForm()));



        TextField username = new TextField();
        username.setLayoutX(225);
        username.setLayoutY(122);
        username.setPromptText("Username");

        PasswordField hiddenPassword = new PasswordField();
        hiddenPassword.setLayoutX(225);
        hiddenPassword.setLayoutY(165);
        hiddenPassword.setPromptText("Password");

        TextField visiblePassword = new TextField();
        visiblePassword.setLayoutX(225);
        visiblePassword.setLayoutY(165);
        visiblePassword.setVisible(false);
        visiblePassword.setPromptText("Password");

        CheckBox showPassword = new CheckBox();
        showPassword.setLayoutX(247);
        showPassword.setLayoutY(200);
        showPassword.setText("show password");

        Button logIn = new Button();
        logIn.setLayoutX(275);
        logIn.setLayoutY(234);
        logIn.setText("Log In");

        Button forgotPassword = new Button();
        forgotPassword.setLayoutX(235);
        forgotPassword.setLayoutY(275);
        forgotPassword.setText("forgot my password");

        Button signUp = new Button();
        signUp.setLayoutX(270);
        signUp.setLayoutY(334);
        signUp.setText("Sign Up");


        showPassword(showPassword, hiddenPassword, visiblePassword);

        Label securityQuestion = new Label("");
        TextField question = new TextField();
        question.setPromptText("Answer");
        question.setVisible(false);
        Popup popup = new Popup();
        popup.getContent().addAll(securityQuestion, question);


        anchorPane.getChildren().addAll(username, hiddenPassword, visiblePassword, showPassword, logIn, forgotPassword, signUp);

//        anchorPane.getChildren().addAll((Collection<? extends Node>) popup);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }


    private void showPassword(CheckBox checkBox, PasswordField passwordField, TextField textField) {
        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (checkBox.isSelected()) {
                    textField.setText(passwordField.getText());
                    passwordField.setVisible(false);
                    textField.setVisible(true);
                    return;
                }
                passwordField.setText(textField.getText());
                passwordField.setVisible(true);
                textField.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
