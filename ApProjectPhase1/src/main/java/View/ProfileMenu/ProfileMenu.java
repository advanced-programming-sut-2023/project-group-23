package View.ProfileMenu;

import Controller.Controller;
import Controller.LoginMenuController;
import Controller.ProfileMenuController;
import Model.User;
import View.LoginMenu.LoginMenu;
import View.MainMenu.MainMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu extends Application {

    String captchaValue;
    private ImageView choose;
    public static void run(Scanner scanner) throws IOException {
        String command;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();

            if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)).matches())
                System.out.println(ProfileMenuController.changeUsername(matcher));
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_EMAIL)).matches())
                System.out.println(ProfileMenuController.changeEmail(matcher));
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_NICKNAME)).matches())
                System.out.println(ProfileMenuController.changeNickname(matcher));
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD)).matches())
                System.out.println(ProfileMenuController.changePassword(matcher, scanner));
            else if ((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_SLOGAN)).matches())
                System.out.println(ProfileMenuController.changeSlogan(matcher));
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE).matches())
                System.out.print(ProfileMenuController.showInfo());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGHSCORE).matches())
                System.out.println(ProfileMenuController.showHighScore());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK).matches())
                System.out.println(ProfileMenuController.showRank());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN).matches())
                System.out.println(ProfileMenuController.showSlogan());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN).matches())
                System.out.println(ProfileMenuController.removeSlogan());
            else if (ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.BACK).matches()) {
                System.out.println("you entered to main menu");
                return;
            }
            else System.out.println("invalid command");
        }
    }


    private Label username;
    private Label password;
    private Label nickname;
    private Label email;
    private Label slogan;
    private String avatarAddress;
    private ImageView avatar;


    public String getAvatarAddress() {
        return avatarAddress;
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(new URL(ProfileMenu.class.getResource("/View/Profile.fxml").toExternalForm()));


        Font font = new Font(20);

        if (User.getCurrentUser().getAvatarAddress() == null)
            User.getCurrentUser().setAvatarAddress(getClass().getResource("/profile/1.png").toExternalForm());
        avatar = new ImageView(new Image(User.getCurrentUser().getAvatarAddress()));
        avatar.setFitWidth(100);
        avatar.setFitHeight(100);
        avatar.setX(45);
        avatar.setY(20);

        username = (Label) anchorPane.getChildren().get(1);
        username.setText(User.getCurrentUser().getUsername());
        username.setFont(font);

        password = (Label) anchorPane.getChildren().get(3);
        password.setText(User.getCurrentUser().getPassword());
        password.setFont(font);

        nickname = (Label) anchorPane.getChildren().get(5);
        nickname.setText(User.getCurrentUser().getNickname());
        nickname.setFont(font);

        email = (Label) anchorPane.getChildren().get(7);
        email.setText(User.getCurrentUser().getEmail());
        email.setFont(font);

        slogan = (Label) anchorPane.getChildren().get(9);
        if (User.getCurrentUser().getSlogan().equals("")) slogan.setText("slogan is empty!");
        else slogan.setText(User.getCurrentUser().getSlogan());
        slogan.setFont(new Font(12));

        Button changeAvatar = new Button("Change Avatar");
        changeAvatar.setLayoutX(44);
        changeAvatar.setLayoutY(134);

        Button changeUsername = new Button("Change Username");
        changeUsername.setLayoutX(34);
        changeUsername.setLayoutY(163);

        Button changePassword = new Button("Change Password");
        changePassword.setLayoutX(36);
        changePassword.setLayoutY(192);

        Button changeSlogan = new Button("Change Slogan");
        changeSlogan.setLayoutX(43);
        changeSlogan.setLayoutY(278);

        Button changeNickname = new Button("Change Nickname");
        changeNickname.setLayoutX(35);
        changeNickname.setLayoutY(221);

        Button changeEmail = new Button("Change Email");
        changeEmail.setLayoutX(47);
        changeEmail.setLayoutY(250);

        anchorPane.getChildren().addAll(changeAvatar, changeEmail, changeNickname, changePassword, changeUsername, changeSlogan);

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        anchorPane.getChildren().get(10).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeAvatar();
            }
        });

        anchorPane.getChildren().get(14).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeUsername();
            }
        });

        anchorPane.getChildren().get(13).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changePassword();
            }
        });

        anchorPane.getChildren().get(12).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeNickname();
            }
        });

        anchorPane.getChildren().get(11).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeEmail();
            }
        });

        anchorPane.getChildren().get(15).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeSlogan();
            }
        });

        Button back = new Button("Back to main menu");
        back.setLayoutX(450);
        back.setLayoutY(30);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    stage.setTitle("Main Menu");
                    new MainMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Button scoreBoard = new Button("Scoreboard");
        scoreBoard.setLayoutX(350);
        scoreBoard.setLayoutY(30);

        scoreBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    stage.setTitle("ScoreBoard");
                    new ScoreBoard().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        BackgroundSize backgroundSize = new BackgroundSize(600, 600, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/6.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);

        anchorPane.getChildren().addAll(avatar, back, scoreBoard);
        stage.setTitle("Profile Menu");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        User.initializeUsersFromDatabase();
        User.setCurrentUser(User.getUsers().get(1));
        launch();
    }


    private void changeUsername() {
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane anchorPane = new AnchorPane();
        BackgroundSize backgroundSize = new BackgroundSize(250, 250, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/1.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);
        Scene scene = new Scene(anchorPane, 250, 250);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Change Username");

        TextField username = new TextField();
        username.setPromptText("New Username");
        username.setLayoutX(50);
        username.setLayoutY(20);
        username.setFocusTraversable(false);

        Text wrongUsername = new Text("username field is empty!");
        wrongUsername.setX(50);
        wrongUsername.setY(70);
        wrongUsername.setFill(Color.RED);

        Text suggest = new Text("");
        suggest.setX(50);
        suggest.setY(85);

        Button apply = new Button("Apply");
        apply.setLayoutX(60);
        apply.setLayoutY(100);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(110);
        cancel.setLayoutY(100);

        username.textProperty().addListener((observableValue, oldText, newText) -> {
            if (newText == null || newText.equals(""))
                wrongUsername.setText("username field is empty!");
            else wrongUsername.setText(ProfileMenuController.checkNewUsername(newText));
            if (ProfileMenuController.suggestUsername(newText).equals("you can choose ")) suggest.setText("");
            else suggest.setText(ProfileMenuController.suggestUsername(newText));
        });

        Alert changeUsername = new Alert(Alert.AlertType.INFORMATION);
        changeUsername.setTitle("Change Username");
        changeUsername.setHeaderText("username changed successfully!");

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wrongUsername.getText().equals("it's ok!")) {
                    User.getCurrentUser().setUsername(username.getText());
                    try {
                        changeUsername.show();
                        User.updateDatabase();
                        getUsername().setText(username.getText());
                        stage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });


        anchorPane.getChildren().addAll(username, wrongUsername, apply, cancel, suggest);
        stage.show();
    }

    private void changePassword() {
        Stage stage = new Stage();
        stage.setResizable(false);
        AnchorPane anchorPane = new AnchorPane();
        BackgroundSize backgroundSize = new BackgroundSize(400, 400, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/2.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Change Password");

        Button apply = new Button("Apply");
        apply.setLayoutX(150);
        apply.setLayoutY(350);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(210);
        cancel.setLayoutY(350);

        PasswordField oldPassword = new PasswordField();
        oldPassword.setPromptText("Old password");
        oldPassword.setLayoutX(10);
        oldPassword.setLayoutY(30);

        PasswordField newPassword = new PasswordField();
        newPassword.setPromptText("New password");
        newPassword.setLayoutX(10);
        newPassword.setLayoutY(70);

        Text wrongOldPassword = new Text("");
        wrongOldPassword.setX(170);
        wrongOldPassword.setY(45);
        wrongOldPassword.setFill(Color.RED);

        Text wrongNewPassword = new Text("");
        wrongNewPassword.setX(170);
        wrongNewPassword.setY(85);
        wrongNewPassword.setFill(Color.RED);


        newPassword.textProperty().addListener(((observableValue, oldText, newText) -> {
            wrongNewPassword.setText(LoginMenuController.checkPasswordForRegister(newText));
        }));

        String address = Controller.captchaAddress();
        Matcher matcher = Pattern.compile("(\\S+).png").matcher(address);
        if (matcher.find()) captchaValue = matcher.group(1);
        ImageView captcha = new ImageView(new Image(getClass().getResource("/captcha/" + address).toExternalForm()));
        captcha.setX(140);
        captcha.setY(150);

        ImageView refresh = new ImageView(new Image(getClass().getResource("/button/refresh.png").toExternalForm()));
        refresh.setY(160);
        refresh.setX(300);
        refresh.setFitWidth(40);
        refresh.setFitHeight(40);

        TextArea captchaField = new TextArea();
        captchaField.setLayoutY(160);
        captchaField.setLayoutX(10);
        captchaField.setPrefWidth(100);
        captchaField.setPrefHeight(20);

        Label wrongCaptcha = new Label("");
        wrongCaptcha.setLayoutX(100);
        wrongCaptcha.setLayoutY(220);
        wrongCaptcha.setTextFill(Color.RED);

        refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                wrongCaptcha.setText("");
                wrongOldPassword.setText("");
                if (wrongNewPassword.getText().equals("new password field is empty!")) wrongNewPassword.setText("");
                refresh(captcha, captchaField);
            }
        });

        Alert changePassword = new Alert(Alert.AlertType.INFORMATION);
        changePassword.setTitle("Change Password");
        changePassword.setHeaderText("password changed successfully!");

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (newPassword.getText().equals("") || newPassword.getText() == null)
                    wrongNewPassword.setText("new password field is empty!");
                if (!captchaField.getText().equals(captchaValue)) {
                    wrongCaptcha.setText("please write captcha carefully!");
                    refresh(captcha, captchaField);
                }
                if (oldPassword.getText().equals("") || oldPassword.getText() == null)
                    wrongOldPassword.setText("old password field is empty!");
                if (!oldPassword.getText().equals(User.getCurrentUser().getPassword()))
                    wrongOldPassword.setText("you must write your old password here!");
                if (wrongCaptcha.getText().equals("") && wrongOldPassword.getText().equals("") &&
                        wrongNewPassword.getText().equals("")) {
                    try {
                        changePassword.show();
                        User.getCurrentUser().setPassword(newPassword.getText());
                        User.updateDatabase();
                        stage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        anchorPane.getChildren().addAll(cancel, apply, oldPassword, newPassword, wrongOldPassword, wrongNewPassword,
                captcha, captchaField, wrongCaptcha, refresh);
        stage.show();
    }

    private void changeNickname() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        BackgroundSize backgroundSize = new BackgroundSize(400, 400, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/3.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Change Nickname");

        TextField nickname = new TextField();
        nickname.setPromptText("New Nickname");
        nickname.setLayoutX(50);
        nickname.setLayoutY(20);
        nickname.setFocusTraversable(false);

        Text wrongNickname = new Text("nickname field is empty!");
        wrongNickname.setX(50);
        wrongNickname.setY(70);
        wrongNickname.setFill(Color.RED);

        Button apply = new Button("Apply");
        apply.setLayoutX(60);
        apply.setLayoutY(100);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(110);
        cancel.setLayoutY(100);

        nickname.textProperty().addListener((observableValue, oldText, newText) -> {
            if (newText == null || newText.equals(""))
                wrongNickname.setText("nickname field is empty!");
            wrongNickname.setText(ProfileMenuController.checkNewNickname(newText));
        });

        Alert changeNickname = new Alert(Alert.AlertType.INFORMATION);
        changeNickname.setTitle("Change Nickname");
        changeNickname.setHeaderText("nickname changed successfully!");

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wrongNickname.getText().equals("it's ok!")) {
                    changeNickname.show();
                    User.getCurrentUser().setNickname(nickname.getText());
                    try {
                        User.updateDatabase();
                        getNickname().setText(nickname.getText());
                        stage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });


        anchorPane.getChildren().addAll(nickname, wrongNickname, apply, cancel);

        stage.show();
    }

    private void changeEmail() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        BackgroundSize backgroundSize = new BackgroundSize(400, 400, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/4.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Change Email");

        TextField email = new TextField();
        email.setPromptText("New email");
        email.setLayoutX(50);
        email.setLayoutY(20);
        email.setFocusTraversable(false);

        Text wrongEmail = new Text("email field is empty!");
        wrongEmail.setX(50);
        wrongEmail.setY(70);
        wrongEmail.setFill(Color.RED);

        Button apply = new Button("Apply");
        apply.setLayoutX(60);
        apply.setLayoutY(100);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(110);
        cancel.setLayoutY(100);

        email.textProperty().addListener((observableValue, oldText, newText) -> {
            if (newText == null || newText.equals(""))
                wrongEmail.setText("email field is empty!");
            wrongEmail.setText(ProfileMenuController.checkNewEmail(newText));
        });

        Alert changeEmail = new Alert(Alert.AlertType.INFORMATION);
        changeEmail.setTitle("Change Email");
        changeEmail.setHeaderText("email changed successfully!");

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wrongEmail.getText().equals("it's ok!")) {
                    changeEmail.show();
                    User.getCurrentUser().setEmail(email.getText());
                    try {
                        User.updateDatabase();
                        getEmail().setText(email.getText());
                        stage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });


        anchorPane.getChildren().addAll(email, wrongEmail, apply, cancel);

        stage.show();
    }

    private void changeSlogan() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        BackgroundSize backgroundSize = new BackgroundSize(400, 400, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/5.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Change Slogan");


        TextField slogan = new TextField();
        slogan.setPromptText("New Slogan");
        slogan.setLayoutX(50);
        slogan.setLayoutY(20);
        slogan.setFocusTraversable(false);

        Text wrongSlogan = new Text("slogan field is empty!");
        wrongSlogan.setX(50);
        wrongSlogan.setY(70);
        wrongSlogan.setFill(Color.RED);

        Button apply = new Button("Apply");
        apply.setLayoutX(60);
        apply.setLayoutY(100);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(110);
        cancel.setLayoutY(100);

        Button clear = new Button("Clear");
        clear.setLayoutX(14);
        clear.setLayoutY(100);

        slogan.textProperty().addListener((observableValue, oldText, newText) -> {
            if (newText == null || newText.equals(""))
                wrongSlogan.setText("slogan field is empty!");
            else wrongSlogan.setText("");
        });

        Alert changeSlogan = new Alert(Alert.AlertType.INFORMATION);
        changeSlogan.setTitle("Change Slogan");
        changeSlogan.setHeaderText("slogan changed successfully!");

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wrongSlogan.getText().equals("")) {
                    changeSlogan.show();
                    User.getCurrentUser().setSlogan(slogan.getText());
                    try {
                        User.updateDatabase();
                        getSlogan().setText(slogan.getText());
                        stage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        Alert deleteSlogan = new Alert(Alert.AlertType.INFORMATION);
        deleteSlogan.setTitle("Delete Slogan");
        deleteSlogan.setHeaderText("slogan deleted successfully!");

        clear.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteSlogan.show();
                User.getCurrentUser().setSlogan("");
                try {
                    User.updateDatabase();
                    getSlogan().setText("slogan is empty!");
                    stage.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        anchorPane.getChildren().addAll(slogan, wrongSlogan, apply, cancel, clear);

        stage.show();
    }

    private void changeAvatar() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
        BackgroundSize backgroundSize = new BackgroundSize(400, 400, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/7.PNG").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Change Avatar");

        Text wrongAvatar = new Text("");
        wrongAvatar.setX(210);
        wrongAvatar.setY(270);
        wrongAvatar.setFill(Color.RED);


        Label header = new Label("Choose an avatar");
        header.setFont(new Font(16));
        header.setLayoutX(10);
        header.setLayoutY(10);

        Button apply = new Button("Apply");
        apply.setLayoutX(150);
        apply.setLayoutY(350);

        Button cancel = new Button("Cancel");
        cancel.setLayoutX(210);
        cancel.setLayoutY(350);

        ImageView rectangle = new ImageView();
        rectangle.setX(20);
        rectangle.setY(200);
        rectangle.setFitWidth(180);
        rectangle.setFitHeight(140);
        rectangle.setImage(new Image(getClass().getResource("/profile/initialize.png").toExternalForm()));


        for (int i = 1; i <= 10; i++) {
            createImage(i, anchorPane, rectangle);
        }
        ImageView upload = new ImageView(new Image(getClass().getResource("/profile/upload.jpg").toExternalForm()));
        upload.setX(10);
        upload.setY(140);
        upload.setFitWidth(50);
        upload.setFitHeight(50);
        upload.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(stage);
                rectangle.setImage(new Image(file.getAbsolutePath()));
            }
        });
        cancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        Alert changeAvatar = new Alert(Alert.AlertType.INFORMATION);
        changeAvatar.setTitle("Change Avatar");
        changeAvatar.setHeaderText("avatar changed successfully!");

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (rectangle.getImage().getUrl().equals("file:/C:/Users/Notebook/approject/project-group-23" +
                        "/ApProjectPhase1/target/classes/profile/initialize.png")) {
                    wrongAvatar.setText("you must select or upload a photo!");
                } else {
                    changeAvatar.show();
                    User.getCurrentUser().setAvatarAddress(rectangle.getImage().getUrl());
                    try {
                        User.updateDatabase();
                        avatarAddress = rectangle.getImage().getUrl();
                        avatar.setImage(new Image(avatarAddress));
                        stage.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        anchorPane.getChildren().addAll(header, apply, cancel, rectangle, upload, wrongAvatar);
        stage.show();
    }

    private ImageView createImage(int number, AnchorPane anchorPane, ImageView rectangle) {
        ImageView imageView = new ImageView(new Image(getClass().getResource("/profile/" + number + ".png").toExternalForm()));
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        int x = ((number - 1) % 5) * 50 + 10;
        int y = ((number - 1) % 2) * 50 + 40;
        imageView.setX(x);
        imageView.setY(y);
        selectImage(anchorPane, imageView, rectangle);
        anchorPane.getChildren().add(imageView);
        return imageView;
    }

    private void selectImage(AnchorPane anchorPane, ImageView imageView, ImageView rectangle) {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                rectangle.setImage(imageView.getImage());
                if (choose != null) anchorPane.getChildren().remove(choose);
                ImageView tick = new ImageView(new Image(getClass().getResource("/profile/tick.png").toExternalForm()));
                tick.setFitWidth(10);
                tick.setFitHeight(10);
                tick.setX(imageView.getX() + 40);
                tick.setY(imageView.getY() + 40);
                anchorPane.getChildren().add(tick);
                choose = tick;
            }
        });
    }

    public Label getUsername() {
        return username;
    }

    public Label getPassword() {
        return password;
    }

    public Label getNickname() {
        return nickname;
    }

    public Label getEmail() {
        return email;
    }

    public Label getSlogan() {
        return slogan;
    }

    public void refresh(ImageView imageView, TextArea textArea) {
        String newAddress = Controller.captchaAddress();
        Matcher matcher = Pattern.compile("(\\S+).png").matcher(newAddress);
        if (matcher.find()) captchaValue = matcher.group(1);
        imageView.setImage(new Image(getClass().getResource("/captcha/" + newAddress).toExternalForm()));
        textArea.clear();
    }
}
