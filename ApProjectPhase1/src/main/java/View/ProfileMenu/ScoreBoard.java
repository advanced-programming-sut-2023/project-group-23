package View.ProfileMenu;

import Controller.ProfileMenuController;
import Model.User;
import View.MainMenu.MainMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ScoreBoard extends Application {

    Integer group = 0;
    private ArrayList<Node> nodes;

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane anchorPane = FXMLLoader.load(new URL(ScoreBoard.class.getResource("/View/ScoreBoard.fxml").toExternalForm()));
        BackgroundSize backgroundSize = new BackgroundSize(600, 600, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/12.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);

        nodes = createNodes();
        anchorPane.getChildren().addAll(nodes);

        anchorPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().getName().equals("Up") && group > 0) {
                    anchorPane.getChildren().removeAll(nodes);
                    group--;
                    nodes = createNodes();
                    anchorPane.getChildren().addAll(nodes);
                } else if (keyEvent.getCode().getName().equals("Down") && group < (User.getUsers().size() - 1) / 10) {
                    anchorPane.getChildren().removeAll(nodes);
                    group++;
                    nodes = createNodes();
                    anchorPane.getChildren().addAll(nodes);
                }
            }
        });

        Button back = new Button("Back to profile menu");
        back.setLayoutX(10);
        back.setLayoutY(10);
        back.setFocusTraversable(false);

        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    stage.setTitle("Profile Menu");
                    new ProfileMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Text userRanking = new Text();
        userRanking.setText(ProfileMenuController.showRank().toString());
        userRanking.setX(230);
        userRanking.setY(55);

        Integer highScore = User.getCurrentUser().getUserHighScore();
        Text userHighScore = new Text();
        userHighScore.setText(highScore.toString());
        userHighScore.setX(450);
        userHighScore.setY(55);

        anchorPane.getChildren().addAll(back, userRanking, userHighScore);
        Scene scene = new Scene(anchorPane, 600, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("ScoreBoard");
        anchorPane.requestFocus();
        stage.show();
    }


    private ArrayList<Node> createNodes() {
        ArrayList<Node> nodes = new ArrayList<>();
        ArrayList<User> sortedUsers = User.copyOfUserList(User.getUsers());
        User.sortUsers(sortedUsers);
        int max = Math.min(sortedUsers.size(), 10 * (group + 1));
        for (int i = 10 * group; i < max; i++) {
            nodes.addAll(createUserDataNode(i, sortedUsers.get(i)));
        }
        return nodes;
    }

    private ArrayList<Node> createUserDataNode(int i, User user) {
        int y = (i % 10) * 20 + 110;
        Integer number = i + 1;
        Label rank = new Label(number.toString() + ".");
        rank.setLayoutX(50);
        rank.setLayoutY(y);

        ImageView imageView = new ImageView(new Image(user.getAvatarAddress()));
        imageView.setX(150);
        imageView.setY(y);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);

        AnchorPane anchorPane = new AnchorPane();
        BackgroundSize backgroundSize = new BackgroundSize(600, 600, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass()
                .getResource("/menuBackground/14.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        anchorPane.setBackground(background);
        Scene scene = new Scene(anchorPane, 300, 300);
        Stage profileStage = new Stage();
        profileStage.setResizable(false);
        profileStage.setScene(scene);
        profileStage.setTitle("Show Profile");

        Button set = new Button("Set as your profile");
        set.setLayoutX(75);
        set.setLayoutY(270);

        Button exit = new Button("Exit");
        exit.setLayoutX(190);
        exit.setLayoutY(270);

        ImageView profile = new ImageView(new Image(user.getAvatarAddress()));
        profile.setX(50);
        profile.setY(50);
        profile.setFitWidth(200);
        profile.setFitHeight(200);

        anchorPane.getChildren().addAll(set, exit, profile);

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                profileStage.show();
            }
        });

        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                profileStage.close();
            }
        });

        set.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                User.getCurrentUser().setAvatarAddress(user.getAvatarAddress());
                try {
                    User.updateDatabase();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                profileStage.close();
            }
        });

        Label nickname = new Label(user.getNickname());
        nickname.setLayoutX(256);
        nickname.setLayoutY(y);

        Integer highScoreNumber = user.getUserHighScore();
        Label highScore = new Label(highScoreNumber.toString());
        highScore.setLayoutX(427);
        highScore.setLayoutY(y);

        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(highScore);
        nodes.add(nickname);
        nodes.add(imageView);
        nodes.add(rank);
        return nodes;
    }

    public static void main(String[] args) throws IOException {
        User.initializeUsersFromDatabase();
        User.setCurrentUser(User.getUserByUsername("hassan"));
        launch();
    }
}
