package Model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.HashMap;

public class User {
    private static User currentUser;
    private static ArrayList<User> users = new ArrayList<>();
    private static HashMap<Integer, String> numberToSecurityQuestion = new HashMap<>() {{
        put(1, "What is my father’s name?");
        put(2, "What was my first pet’s name?");
        put(3, "What is my mother’s last name?");
    }};

    private static HashMap<Integer, String> randomSlogans = new HashMap<>() {{
        put(1, "I shall have my revenge, in this life or the next");
        put(2, "slogan2");
        put(3, "slogan3");
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
        this.password = DigestUtils.sha256Hex(password);
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

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    public String getUsername() {
        return username;
    }

    public boolean isPasswordCorrect(String inputPassword) {
        String encryptedInputPassword = DigestUtils.sha256Hex(inputPassword);

        return this.password.equals(encryptedInputPassword);
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
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

    public String getUserSecurityQuestion() {
        return numberToSecurityQuestion.get(userSecurityQuestionNumber);
    }

    public static String getQuestionByKey(int key) {
        return numberToSecurityQuestion.get(key);
    }

    public String getUserAnswerToSecurityQuestion() {
        return userAnswerToSecurityQuestion;
    }

    public int getUserHighScore() {
        return userHighScore;
    }

    public static void addUser(User user) {
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
    public static User getUserByUsername(String username) {
        for(User user : users)
            if(user.getUsername().equals(username))
                return user;
        return null;
    }

    public static User getUserByEmail(String email) {
        for(User user : users)
            if(user.getEmail().equals(email))
                return user;
        return null;
    }

    public static User getUserByNickname(String nickname) {
        for(User user : users)
            if(user.getNickname().equals(nickname))
                return user;
        return null;
    }

    public static ArrayList<String> getEmails() {
        return emails;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void initializeUsersFromDatabase() throws FileNotFoundException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        JsonReader reader = new JsonReader(new FileReader("usersDatabase.json"));
        User[] usersTempArray = gson.fromJson(reader, User[].class);
        if(usersTempArray != null) {
            ArrayList<User> usersTempList = new ArrayList<>(Arrays.asList(usersTempArray));
            User.setUsers(usersTempList);
        }
    }

    public static void updateDatabase() throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        FileWriter writer = new FileWriter("usersDatabase.json");
        gson.toJson(User.getUsers(), writer);
        writer.close();
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public static String getRandomSloganByKey(int key) {
        return randomSlogans.get(key);
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
