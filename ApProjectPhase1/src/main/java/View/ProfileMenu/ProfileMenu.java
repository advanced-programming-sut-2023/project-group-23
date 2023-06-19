package View.ProfileMenu;

import Controller.ProfileMenuController;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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


    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(new URL(ProfileMenu.class.getResource("/View/Profile.fxml").toExternalForm()));

        Label username = (Label) anchorPane.getChildren().get(3);
        username.setText(User.getCurrentUser().getUsername());

        Label password = (Label) anchorPane.getChildren().get(5);
        password.setText(User.getCurrentUser().getPassword());

        Label nickname = (Label) anchorPane.getChildren().get(7);
        nickname.setText(User.getCurrentUser().getNickname());

        Label email = (Label) anchorPane.getChildren().get(9);
        email.setText(User.getCurrentUser().getEmail());

        Label slogan = (Label) anchorPane.getChildren().get(11);
        if (slogan.equals("")) slogan.setText("slogan is empty!");
        else slogan.setText(User.getCurrentUser().getSlogan());

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        User.initializeUsersFromDatabase();
        User.setCurrentUser(User.getUsers().get(1));
        launch();
    }
}
