package View.ProfileMenu;

import Controller.ProfileMenuController;
import Model.User;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Application {
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

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(new URL(ProfileMenu.class.getResource("/View/Profile.fxml").toExternalForm()));

        username = (Label) anchorPane.getChildren().get(2);
        username.setText(User.getCurrentUser().getUsername());

        password = (Label) anchorPane.getChildren().get(4);
        password.setText(User.getCurrentUser().getPassword());

        nickname = (Label) anchorPane.getChildren().get(6);
        nickname.setText(User.getCurrentUser().getNickname());

        email = (Label) anchorPane.getChildren().get(8);
        email.setText(User.getCurrentUser().getEmail());

        slogan = (Label) anchorPane.getChildren().get(10);
        if (User.getCurrentUser().getSlogan().equals("")) slogan.setText("slogan is empty!");
        else slogan.setText(User.getCurrentUser().getSlogan());

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

        anchorPane.getChildren().get(11).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeAvatar();
            }
        });

        anchorPane.getChildren().get(15).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeUsername();
            }
        });

        anchorPane.getChildren().get(14).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changePassword();
            }
        });

        anchorPane.getChildren().get(13).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeNickname();
            }
        });

        anchorPane.getChildren().get(12).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeEmail();
            }
        });

        anchorPane.getChildren().get(16).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                changeSlogan();
            }
        });


        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        User.initializeUsersFromDatabase();
        User.setCurrentUser(User.getUsers().get(1));
        launch();
    }


    private void changeUsername() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
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

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wrongUsername.getText().equals("it's ok!")) {
                    User.getCurrentUser().setUsername(username.getText());
                    try {
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
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Change Password");
        stage.show();
    }

    private void changeNickname() {
        Stage stage = new Stage();
        AnchorPane anchorPane = new AnchorPane();
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

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wrongNickname.getText().equals("it's ok!")) {
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

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wrongEmail.getText().equals("it's ok!")) {
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

        apply.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (wrongSlogan.getText().equals("")) {
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

        clear.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
        Scene scene = new Scene(anchorPane, 400, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Change Avatar");
        stage.show();
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
}
