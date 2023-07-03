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
        put(1, "What is my father's name?");
        put(2, "What was my first pet's name?");
        put(3, "What is my mother's last name?");
    }};

    private static HashMap<Integer, String> randomSlogans = new HashMap<>() {{
        put(1, "I shall have my revenge, in this life or the next");
        put(2, "I may have my revenge, in this life or the next");
        put(3, "I won't have my revenge, in this life or the next");
    }};

    public static ArrayList<String> captchaList = new ArrayList<>() {{
        add("1181");add("1381");add("1491");add("1722");add("1959");add("2163");add("2177");add("2723");add("2785");
        add("3541");add("3847");add("3855");add("3876");add("3967");add("4185");add("4310");add("4487");add("4578");
        add("4602");add("4681");add("4924");add("5326");add("5463");add("5771");add("5849");add("6426");add("6553");
        add("6601");add("6733");add("6960");add("7415");add("7609");add("7755");add("7825");add("7905");add("8003");
        add("8070");add("8368");add("8455");add("8506");add("8555");add("8583");add("8692");add("8776");add("8972");
        add("8996");add("9061");add("9386");add("9582");add("9633");
    }};

    private String username;
    private String password;
    private String noEncryptedPassword;
    private String nickname;
    private String slogan;
    private String email;
    private int userSecurityQuestionNumber;
    private String userAnswerToSecurityQuestion;
    private int userHighScore;
    private String avatarAddress;

    public User(String username, String password, String nickname, String slogan, String email, int userSecurityQuestion
            , String userAnswerToSecurityQuestion, String avatarAddress) {
        this.username = username;
        this.password = DigestUtils.sha256Hex(password);
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.userSecurityQuestionNumber = userSecurityQuestion;
        this.userAnswerToSecurityQuestion = userAnswerToSecurityQuestion;
        this.userHighScore = 0;
        this.avatarAddress = avatarAddress;
        this.noEncryptedPassword = password;
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

    public void setPassword(String password) throws IOException {
        this.noEncryptedPassword = password;
        this.password = DigestUtils.sha256Hex(password);
        User.updateDatabase();
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

    public static ArrayList<User> copyOfUserList(ArrayList<User> userList) {
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

    public static void sortUsers(ArrayList<User> userList) {
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

    public static String famousSlogans(Integer rank) {
        HashMap<String, Integer> slogans = new HashMap<>();
        for (User user : users) {
            if (!user.getSlogan().equals("")) {
                if (slogans.containsKey(user.getSlogan()))
                    slogans.put(user.getSlogan(), slogans.get(user.getSlogan()) + 1);
                else slogans.put(user.getSlogan(), 1);
            }
        }
        if (rank > slogans.size()) return "";
        String first = "";
        String second = "";
        String third = "";
        int rank1 = 0;
        int rank2 = 0;
        int rank3 = 0;
        for (String s : slogans.keySet()) {
            if (slogans.get(s) > rank1) {
                rank3 = rank2;
                third = second;
                rank2 = rank1;
                second = first;
                rank1 = slogans.get(s);
                first = s;
            } else if (slogans.get(s) > rank2) {
                rank3 = rank2;
                third = second;
                rank2 = slogans.get(s);
                second = s;
            } else if (slogans.get(s) > rank3) {
                rank3 = slogans.get(s);
                third = s;
            }
        }
        if (rank.equals(1)) return first;
        if (rank.equals(2)) return second;
        if (rank.equals(3)) return third;
        return "";
    }

    public static boolean checkPasswordByUsername(String username, String password){
        return getUserByUsername(username).password.equals(password);
    }

    public static ArrayList<String> getEmails() {
        return null;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserHighScore(int userHighScore) throws IOException {
        this.userHighScore = userHighScore;
        User.updateDatabase();
    }

    public String getNoEncryptedPassword() {
        return noEncryptedPassword;
    }

    public String getAvatarAddress() {
        return avatarAddress;
    }

    public void setAvatarAddress(String avatarAddress) {
        this.avatarAddress = avatarAddress;
    }
}
