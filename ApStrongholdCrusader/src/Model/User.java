package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private static ArrayList<User> users = new ArrayList<>();
    private static HashMap<Integer, String> numberToSecurityQuestion = new HashMap<>() {{
        put(1, "What is my father’s name?");
        put(2, "What was my first pet’s name?");
        put(3, "What is my mother’s last name?");
    }};

    private String username;
    private String password;
    private String nickname;
    private String slogan;
    private String email;
    private int userSecurityQuestionNumber;
    private String userAnswerToSecurityQuestion;
    private int userHighScore;

    public User(String username, String password, String nickname, String slogan, String email, int userSecurityQuestion, String userAnswerToSecurityQuestion) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.userSecurityQuestionNumber = userSecurityQuestion;
        this.userAnswerToSecurityQuestion = userAnswerToSecurityQuestion;
        this.userHighScore = 0;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public String getUsername() {
        return username;
    }

    public boolean isPasswordCorrect(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public String getNickname() {
        return nickname;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getEmail() {
        return email;
    }

    public String  getUserSecurityQuestion() {
        return numberToSecurityQuestion.get(userSecurityQuestionNumber);
    }

    public String getUserAnswerToSecurityQuestion() {
        return userAnswerToSecurityQuestion;
    }

    public int getUserHighScore() {
        return userHighScore;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void sortUserList(User user) {

    }
}
