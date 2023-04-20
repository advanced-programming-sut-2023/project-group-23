package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class User {
    private static User currentUser;
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

    private static ArrayList<User> copyOfUserList(ArrayList<User> userList) {
        ArrayList<User> copiedList = new ArrayList<>();
        for (User user : userList) {
            copiedList.add(user);
        }
        return copiedList;
    }

    private static boolean compareAccordingToHighscore(User firstUser, User secondUser) {
        if (firstUser.getUserHighScore() < secondUser.getUserHighScore()) return false;
        else if (firstUser.getUserHighScore() == secondUser.getUserHighScore())
            if (firstUser.getNickname().compareTo(secondUser.getNickname()) > 0) return false;
        return true;
    }

    private static void sortUsers(ArrayList<User> userList) {
        int listSize = userList.size();
        for (int i = 1; i < listSize; i ++) {
            for (int j = 0; j < i; j ++) {
                if (!compareAccordingToHighscore(userList.get(j), userList.get(i)))
                    Collections.swap(userList, i, j);
            }
        }
    }

    public static Integer userRank() {
        ArrayList<User> userList = copyOfUserList(users);
        sortUsers(userList);
        Integer userRanking = 1;
        for (User user : userList) {
            if (user.equals(currentUser)) return userRanking;
            userRanking++;
        }
        return 0;
    }
    public User getUserByUsername(String username) {
        return null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}
