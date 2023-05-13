import Model.User;
import View.LoginMenu.LoginMenu;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        User.initializeUsersFromDatabase();

        LoginMenu.run(scanner);

        User.updateDatabase();
    }
}
