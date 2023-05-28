package View.LoginMenu;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class RegisterMenu extends Application {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirmation;
    @FXML
    private TextField nickname;
    @FXML
    private TextField email;
    @FXML
    private CheckBox showPassword;
    @FXML
    private CheckBox showPasswordConfirmation;
    @FXML
    private Label wrongUsername;
    @FXML
    private Label wrongPassword;
    @FXML
    private Label wrongPasswordConfirmation;
    @FXML
    private Label wrongNickname;
    @FXML
    private Label wrongEmail;
    @FXML
    private Slider slider;


    public void enterLogin(MouseEvent event) {
    }

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(new URL(RegisterMenu.class.getResource("/View/Register.fxml").toString()));

        RegisterMenu.showPasswordFields(showPassword);
        RegisterMenu.showPasswordFields(showPasswordConfirmation);


        MenuButton securityQuestion = new MenuButton("Security Question");
        MenuItem fatherName = new MenuItem("What is my father's name?");
        MenuItem petName = new MenuItem("What was my first pet's name?");
        MenuItem motherLastName = new MenuItem("What is my mother's last name?");
        securityQuestion.getItems().addAll(fatherName, petName, motherLastName);

        securityQuestion.setLayoutX(229);
        securityQuestion.setLayoutY(377);

        MenuButton slogan = new MenuButton("Slogan");
        MenuItem none = new MenuItem("None");
        MenuItem custom = new MenuItem("Custom");
        MenuItem first = new MenuItem("I shall have my revenge, in this life or the next");
        MenuItem second = new MenuItem("I may have my revenge, in this life or the next");
        MenuItem third = new MenuItem("I won't have my revenge, in this life or the next");
        slogan.getItems().addAll(none, custom, first, second, third);

        slogan.setLayoutX(229);
        slogan.setLayoutY(327);

        slogan.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

        anchorPane.getChildren().addAll(securityQuestion, slogan);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    protected static void showPasswordFields(CheckBox passwordField) {
        passwordField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (passwordField.isSelected()) {
                    passwordField.setSelected(false);
                    passwordField.setAccessibleRole(AccessibleRole.PASSWORD_FIELD);
                } else {
                    passwordField.setSelected(true);
                    passwordField.setAccessibleRole(AccessibleRole.TEXT_FIELD);
                }
            }
        });
    }
}
