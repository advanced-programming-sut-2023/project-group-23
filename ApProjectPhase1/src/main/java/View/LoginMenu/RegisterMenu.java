package View.LoginMenu;

import Controller.Controller;
import Controller.LoginMenuController;
import Controller.ProfileMenuController;
import Model.User;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterMenu extends Application {

    private String captchaValue;
    private Integer questionNumber;
    public static void main(String[] args) throws FileNotFoundException {
        User.initializeUsersFromDatabase();
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(new URL(RegisterMenu.class.getResource("/View/Register.fxml").toString()));

        BackgroundSize backgroundSize = new BackgroundSize(600, 800, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/10.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setLayoutX(228);
        username.setLayoutY(48);

        TextField password = new TextField();
        password.setPromptText("Password");
        password.setLayoutX(228);
        password.setLayoutY(93);
        password.setVisible(false);

        PasswordField hiddenPassword = new PasswordField();
        hiddenPassword.setPromptText("Password");
        hiddenPassword.setLayoutX(228);
        hiddenPassword.setLayoutY(93);

        TextField passwordConfirmation = new TextField();
        passwordConfirmation.setPromptText("Password Confirmation");
        passwordConfirmation.setLayoutX(228);
        passwordConfirmation.setLayoutY(159);
        passwordConfirmation.setVisible(false);

        PasswordField hiddenPasswordConfirmation = new PasswordField();
        hiddenPasswordConfirmation.setPromptText("Password Confirmation");
        hiddenPasswordConfirmation.setLayoutX(228);
        hiddenPasswordConfirmation.setLayoutY(159);

        TextField nickname = new TextField();
        nickname.setPromptText("Nickname");
        nickname.setLayoutX(228);
        nickname.setLayoutY(228);

        TextField email = new TextField();
        email.setPromptText("Email");
        email.setLayoutX(228);
        email.setLayoutY(277);

        CheckBox showPassword = new CheckBox("Show");
        showPassword.setLayoutX(275);
        showPassword.setLayoutY(125);

        CheckBox showPasswordConfirmation = new CheckBox("Show");
        showPasswordConfirmation.setLayoutX(274);
        showPasswordConfirmation.setLayoutY(191);

        Button signUp = new Button("Sign Up");
        signUp.setLayoutX(273);
        signUp.setLayoutY(313);

        Button logIn = new Button("Log In");
        logIn.setLayoutX(275);
        logIn.setLayoutY(378);

        Text wrongUsername = new Text("");
        wrongUsername.setFill(Color.RED);
        wrongUsername.setX(395);
        wrongUsername.setY(65);

        Text suggest = new Text("");
        suggest.setX(395);
        suggest.setY(85);
        suggest.setFill(Color.GREEN);

        Text wrongPassword = new Text("");
        wrongPassword.setFill(Color.RED);
        wrongPassword.setX(395);
        wrongPassword.setY(110);

        Text wrongPasswordConfirmation = new Text("");
        wrongPasswordConfirmation.setFill(Color.RED);
        wrongPasswordConfirmation.setX(395);
        wrongPasswordConfirmation.setY(176);

        Text wrongEmail = new Text("");
        wrongEmail.setFill(Color.RED);
        wrongEmail.setX(395);
        wrongEmail.setY(294);

        Text wrongNickname = new Text("");
        wrongNickname.setFill(Color.RED);
        wrongNickname.setX(395);
        wrongNickname.setY(245);

        CheckBox slogan = new CheckBox("Slogan");
        slogan.setLayoutX(275);
        slogan.setLayoutY(410);

        Button randomPassword = new Button("Random password");
        randomPassword.setLayoutX(110);
        randomPassword.setLayoutY(159);

        CheckBox randomSlogan = new CheckBox("Random");
        randomSlogan.setLayoutX(275);
        randomSlogan.setLayoutY(430);
        randomSlogan.setVisible(false);

        TextField sloganField = new TextField();
        sloganField.setPromptText("Slogan");
        sloganField.setLayoutX(228);
        sloganField.setLayoutY(450);
        sloganField.setVisible(false);

        LoginMenu.showPassword(showPassword, hiddenPassword, password);
        LoginMenu.showPassword(showPasswordConfirmation, hiddenPasswordConfirmation, passwordConfirmation);

        randomSlogan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (randomSlogan.isSelected()) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(3) + 1;
                    sloganField.setText(User.getRandomSloganByKey(randomNumber));
                }
            }
        });



        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Random Password");
        alert.setResizable(false);

        randomPassword.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String randomPass = LoginMenu.setPasswordRandomly();
                alert.setHeaderText("Do you want to set this password? \"" + randomPass + "\"");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    if (showPassword.isSelected()) password.setText(randomPass);
                    else hiddenPassword.setText(randomPass);
                }
            }
        });

        MenuButton famousSlogan = new MenuButton("Famous Slogans");
        MenuItem first = new MenuItem();
        if (!User.famousSlogans(1).equals("")) {
            first.setText(User.famousSlogans(1));
            famousSlogan.getItems().add(first);
            selectMenuItem(first, sloganField);
        }
        MenuItem second = new MenuItem();
        if (!User.famousSlogans(2).equals("")) {
            second.setText(User.famousSlogans(2));
            famousSlogan.getItems().add(second);
            selectMenuItem(second, sloganField);
        }
        MenuItem third = new MenuItem();
        if (!User.famousSlogans(3).equals("")) {
            third.setText(User.famousSlogans(3));
            famousSlogan.getItems().add(third);
            selectMenuItem(third, sloganField);
        }
        famousSlogan.setLayoutX(240);
        famousSlogan.setLayoutY(480);
        famousSlogan.setVisible(false);

        Text wrongSlogan = new Text("");
        wrongSlogan.setX(395);
        wrongSlogan.setY(465);
        wrongSlogan.setFill(Color.RED);

        slogan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (slogan.isSelected()) {
                    famousSlogan.setVisible(true);
                    randomSlogan.setVisible(true);
                    sloganField.setVisible(true);
                    return;
                }
                famousSlogan.setVisible(false);
                randomSlogan.setVisible(false);
                sloganField.setVisible(false);
            }
        });

        username.textProperty().addListener((observableValue, oldText, newText) -> {
            wrongUsername.setText(LoginMenuController.checkUsernameForRegister(username.getText()));
            if (ProfileMenuController.suggestUsername(newText).equals("you can choose ")) suggest.setText("");
            else suggest.setText(ProfileMenuController.suggestUsername(newText));
        });

        hiddenPassword.textProperty().addListener((observable, oldText, newText) -> {
            wrongPassword.setText(LoginMenuController.checkPasswordForRegister(hiddenPassword.getText()));
        });

        Stage securityStage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene securityScene = new Scene(pane, 400, 250);
        securityStage.setTitle("Security Question");
        securityStage.setScene(securityScene);
        MenuButton securityQuestion = new MenuButton("Pick security question");
        securityQuestion.setLayoutX(10);
        securityQuestion.setLayoutY(10);
        MenuItem firstQuestion = new MenuItem();
        firstQuestion.setText(User.getQuestionByKey(1));
        MenuItem secondQuestion = new MenuItem();
        secondQuestion.setText(User.getQuestionByKey(2));
        MenuItem thirdQuestion = new MenuItem();
        thirdQuestion.setText(User.getQuestionByKey(3));
        securityQuestion.getItems().addAll(firstQuestion, secondQuestion, thirdQuestion);


        TextField securityQuestionAnswer = new TextField();
        securityQuestionAnswer.setPromptText("Answer");
        securityQuestionAnswer.setLayoutX(10);
        securityQuestionAnswer.setLayoutY(45);

        Button ok = new Button("OK");
        ok.setLayoutX(180);
        ok.setLayoutY(200);

        String address = Controller.captchaAddress();
        Matcher matcher = Pattern.compile("(\\S+).png").matcher(address);
        if (matcher.find()) captchaValue = matcher.group(1);
        ImageView captcha = new ImageView(new Image(getClass().getResource("/captcha/" + address).toExternalForm()));
        captcha.setX(140);
        captcha.setY(100);

        ImageView refresh = new ImageView(new Image(getClass().getResource("/button/refresh.png").toExternalForm()));
        refresh.setY(110);
        refresh.setX(300);
        refresh.setFitWidth(40);
        refresh.setFitHeight(40);

        TextArea captchaField = new TextArea();
        captchaField.setLayoutY(110);
        captchaField.setLayoutX(10);
        captchaField.setPrefWidth(100);
        captchaField.setPrefHeight(20);

        Label wrongCaptcha = new Label("");
        wrongCaptcha.setLayoutX(100);
        wrongCaptcha.setLayoutY(170);
        wrongCaptcha.setTextFill(Color.RED);

        Text wrongAnswer = new Text("");
        wrongAnswer.setX(165);
        wrongAnswer.setY(61);
        wrongAnswer.setFill(Color.RED);

        Text wrongChooseQuestion = new Text("");
        wrongChooseQuestion.setX(165);
        wrongChooseQuestion.setY(25);
        wrongChooseQuestion.setFill(Color.RED);

        selectSecurityQuestion(firstQuestion, securityQuestion, wrongChooseQuestion);
        selectSecurityQuestion(secondQuestion, securityQuestion, wrongChooseQuestion);
        selectSecurityQuestion(thirdQuestion, securityQuestion, wrongChooseQuestion);

        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCaptcha.setText("");
                wrongAnswer.setText("");
                wrongChooseQuestion.setText("");
                refresh(captcha, captchaField);
            }
        });
        String finalCaptchaValue = captchaValue;

        Alert successfullyRegister = new Alert(Alert.AlertType.INFORMATION);
        successfullyRegister.setTitle("Congratulations!");
        successfullyRegister.setHeaderText("your account created successfully!");

        ok.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String answer = securityQuestionAnswer.getText();
                String finalSlogan = "";
                String finalPassword;
                if (!sloganField.getText().equals("") && sloganField.getText() != null) finalSlogan = sloganField.getText();
                if (showPassword.isSelected()) finalPassword = password.getText();
                else finalPassword = hiddenPassword.getText();
                if (!captchaValue.equals(captchaField.getText())) {
                    wrongCaptcha.setText("please write captcha carefully!");
                    refresh(captcha, captchaField);
                } else
                    wrongCaptcha.setText("");
                if (answer == null || answer.equals(""))
                    wrongAnswer.setText("answer field is empty!");
                if (securityQuestion.getText().equals("Pick security question"))
                    wrongChooseQuestion.setText("choose a question!");
                else wrongChooseQuestion.setText("");
                if (wrongAnswer.getText().equals("") && wrongCaptcha.getText().equals("") && wrongChooseQuestion.getText().equals("")) {
                    User.addUser(new User(username.getText(), finalPassword, nickname.getText(), finalSlogan,
                            email.getText(), questionNumber, answer, avatarAddressRandom()));
                    try {
                        User.updateDatabase();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        securityStage.close();
                        successfullyRegister.show();
                        stage.setTitle("Login Menu");
                        new LoginMenu().start(stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        pane.getChildren().addAll(securityQuestion, securityQuestionAnswer, captchaField,
                captcha, wrongCaptcha, refresh, ok, wrongAnswer, wrongChooseQuestion);

        Alert enterSecuritySection = new Alert(Alert.AlertType.INFORMATION);
        enterSecuritySection.setTitle("Congratulations!");
        enterSecuritySection.setHeaderText("you filled fields properly! now you must enter to security section...");

        signUp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String finalPassword = null;
                String finalPasswordConfirmation = null;
                if (showPassword.isSelected()) finalPassword = password.getText();
                else finalPassword = hiddenPassword.getText();
                if (showPasswordConfirmation.isSelected()) finalPasswordConfirmation = passwordConfirmation.getText();
                else finalPasswordConfirmation = hiddenPasswordConfirmation.getText();
                if (LoginMenuController.signUp(username.getText(), finalPassword, finalPasswordConfirmation,
                        email.getText(), nickname.getText(), sloganField.getText()) && !securityStage.isShowing()
                        && (!slogan.isSelected() || (!sloganField.getText().equals("") && sloganField.getText() != null))) {
                    enterSecuritySection.show();
                    securityStage.show();
                    wrongUsername.setText("");
                    wrongPassword.setText("");
                    wrongPasswordConfirmation.setText("");
                    wrongEmail.setText("");
                    wrongNickname.setText("");
                    wrongSlogan.setText("");
                }
                else {
                    if (username.getText().equals("") || username.getText() == null)
                        wrongUsername.setText("username field is empty!");
                    if ((sloganField.getText().equals("") || sloganField.getText() == null) && slogan.isSelected())
                        wrongSlogan.setText("slogan field is empty!");
                    if (finalPassword.equals("") || finalPassword == null)
                        wrongPassword.setText("password field is empty!");
                    if (finalPasswordConfirmation.equals("") || finalPasswordConfirmation == null)
                        wrongPasswordConfirmation.setText("password confirmation field is empty!");
                    else if (!LoginMenuController.checkPasswordConfirmationForRegister(finalPassword
                            , finalPasswordConfirmation).equals("it's ok!"))
                        wrongPasswordConfirmation.setText(LoginMenuController.checkPasswordConfirmationForRegister(
                                finalPassword, finalPasswordConfirmation));
                    if (email.getText().equals("") || email.getText() == null)
                        wrongEmail.setText("email field is empty!");
                    else if (!LoginMenuController.checkEmailForRegister(email.getText()).equals("it's ok!"))
                        wrongEmail.setText(LoginMenuController.checkEmailForRegister(email.getText()));
                    if (nickname.getText().equals("") || nickname.getText() == null)
                        wrongNickname.setText("nickname field is empty!");
                    else if (!LoginMenuController.checkNicknameForRegister(nickname.getText()).equals("it's ok!"))
                        wrongNickname.setText(LoginMenuController.checkNicknameForRegister(nickname.getText()));
                }


            }
        });

        logIn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    stage.setTitle("Login Menu");
                    new LoginMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        anchorPane.getChildren().addAll(username, password, passwordConfirmation, nickname, email,
                showPassword, showPasswordConfirmation, signUp, logIn, wrongUsername, wrongPassword, wrongEmail,
                wrongPasswordConfirmation, wrongNickname, hiddenPasswordConfirmation, hiddenPassword,
                slogan, randomSlogan, sloganField, famousSlogan, wrongSlogan, suggest, randomPassword);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    private void selectMenuItem(MenuItem menuItem, TextField textField) {
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textField.setText(menuItem.getText());
            }
        });
    }

    private String avatarAddressRandom() {
        Random random = new Random();
        int randomInt = random.nextInt(10) + 1;
        Image image = new Image(getClass().getResource("/profile/" + randomInt + ".png").toExternalForm());
        return image.getUrl();
    }

    private void selectSecurityQuestion(MenuItem menuItem, MenuButton menuButton, Text text) {
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (menuItem.getText().equals("What is my father's name?")) questionNumber = 1;
                else if (menuItem.getText().equals("What was my first pet's name?")) questionNumber = 2;
                else questionNumber = 3;
                text.setText("");
                menuButton.setText(menuItem.getText());
            }
        });
    }

    public void refresh(ImageView imageView, TextArea textArea) {
        String newAddress = Controller.captchaAddress();
        Matcher matcher = Pattern.compile("(\\S+).png").matcher(newAddress);
        if (matcher.find()) captchaValue = matcher.group(1);
        imageView.setImage(new Image(getClass().getResource("/captcha/" + newAddress).toExternalForm()));
        textArea.clear();
    }

}
