package View.LoginMenu;

import Controller.Controller;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Application {

    private Label wrongUsername;
    private Label wrongPassword;
    private Label wrongCaptcha;
    private String captchaValue;
    private String captchaEntered;

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

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(new URL(LoginMenu.class.getResource("/View/Login.fxml").toExternalForm()));



        TextField username = new TextField();
        username.setLayoutX(225);
        username.setLayoutY(122);
        username.setPromptText("Username");

        wrongUsername = new Label();
        wrongUsername.setLayoutX(388);
        wrongUsername.setLayoutY(126);

        PasswordField hiddenPassword = new PasswordField();
        hiddenPassword.setLayoutX(225);
        hiddenPassword.setLayoutY(165);
        hiddenPassword.setPromptText("Password");

        wrongPassword = new Label();
        wrongPassword.setLayoutX(388);
        wrongPassword.setLayoutY(169);

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




        AnchorPane forgotPasswordPane = new AnchorPane();
        Stage dialog = new Stage();
        Scene sceneDialog = new Scene(forgotPasswordPane, 400, 200);
        dialog.setScene(sceneDialog);
        dialog.setTitle("forgot password");
        dialog.setResizable(false);

        signUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO: enter register menu
            }
        });

        String address = Controller.captchaAddress();
        Matcher matcher = Pattern.compile("(\\S+).png").matcher(address);
        if (matcher.find()) captchaValue = matcher.group(1);
        ImageView captcha = new ImageView(new Image(getClass().getResource("/captcha/" + address).toExternalForm()));
        captcha.setX(330);
        captcha.setY(400);

        ImageView refresh = new ImageView(new Image(getClass().getResource("/button/refresh.png").toExternalForm()));
        refresh.setY(410);
        refresh.setX(490);
        refresh.setFitWidth(40);
        refresh.setFitHeight(40);

        TextArea captchaField = new TextArea();
        captchaField.setLayoutY(410);
        captchaField.setLayoutX(200);
        captchaField.setPrefWidth(100);
        captchaField.setPrefHeight(20);

        wrongCaptcha = new Label("");
        wrongCaptcha.setLayoutX(250);
        wrongCaptcha.setLayoutY(470);

        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                refresh(captcha, captchaField);
            }
        });

        logIn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                captchaEntered = captchaField.getText();
                if (hiddenPassword.isVisible()) login(username.getText(), hiddenPassword.getText());
                else login(username.getText(), visiblePassword.getText());
                refresh(captcha, captchaField);
            }
        });

        forgotPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongUsername.setText("");
                wrongPassword.setText("");
                wrongCaptcha.setText("");
                captchaEntered = captchaField.getText();
                if (captchaEntered.equals(captchaValue) && LoginMenuController.isUserExist(username.getText())) {
                    if (!dialog.isShowing()) {
                        captchaField.clear();
                        User user = User.getUserByUsername(username.getText());
                        initializeDialog(dialog, forgotPasswordPane, user);
                        dialog.show();
                    }
                } else {
                    wrongUsername.setText(LoginMenuController.loginCheckUsername(username.getText()));
                    if (!captchaEntered.equals(captchaValue)) {
                        wrongCaptcha.setText("please write captcha carefully!");
                    }
                }
                refresh(captcha, captchaField);
            }
        });


        anchorPane.getChildren().addAll(username, hiddenPassword, visiblePassword, showPassword, logIn, forgotPassword,
                signUp, wrongPassword, wrongUsername, captcha, refresh, captchaField, wrongCaptcha);
        Scene scene = new Scene(anchorPane);
        stage.setTitle("Login Menu");
        stage.setScene(scene);
        stage.setResizable(false);
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

    private boolean checkUserPass(String username, String password) {
        wrongPassword.setText(LoginMenuController.loginCheckPassword(password, username));
        wrongUsername.setText(LoginMenuController.loginCheckUsername(username));
        return wrongUsername.getText().equals("") && wrongPassword.getText().equals("");
    }

    private void login(String username, String password) {
        checkUserPass(username, password);
        if (captchaValue.equals(captchaEntered) && checkUserPass(username, password)) {
            User.setCurrentUser(User.getUserByUsername(username));
            return;
        } else {
            if (!captchaEntered.equals(captchaValue))
                wrongCaptcha.setText("please write captcha carefully!");
            else wrongCaptcha.setText("");
        }

        //TODO:enter main menu
    }

    private void initializeDialog(Stage stage, AnchorPane anchorPane, User user) {
        Text text = new Text();
        text.setX(20);
        text.setY(50);
        text.setText(user.getUserSecurityQuestion());

        TextField answer = new TextField();
        answer.setPromptText("Answer");
        answer.setLayoutY(70);
        answer.setLayoutX(20);

        Button enter = new Button("Enter");
        enter.setLayoutY(120);
        enter.setLayoutX(180);

        Text wrongAnswer = new Text("");
        wrongAnswer.setX(180);
        wrongAnswer.setY(87);

        enter.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!answer.getText().equals(user.getUserAnswerToSecurityQuestion())) wrongAnswer.setText("Your answer" +
                        " didn't correct, try again!");
                else {
                    //TODO: enter main menu
                    stage.close();
                }
            }
        });


        anchorPane.getChildren().addAll(text, enter, answer, wrongAnswer);
    }

    private void refresh(ImageView imageView, TextArea textArea) {
        String newAddress = Controller.captchaAddress();
        Matcher matcher = Pattern.compile("(\\S+).png").matcher(newAddress);
        if (matcher.find()) captchaValue = matcher.group(1);
        imageView.setImage(new Image(getClass().getResource("/captcha/" + newAddress).toExternalForm()));
        textArea.clear();
    }

    public static void main(String[] args) throws FileNotFoundException {
        User.initializeUsersFromDatabase();
        launch();
    }
}
