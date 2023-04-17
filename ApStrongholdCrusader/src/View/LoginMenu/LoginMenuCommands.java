package View.LoginMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    CREATE_USER("\\s*user\\s+create\\s+-\\s*u\\s+<?(username).+>\\s+-\\s*p\\s+<?(password).+>\\s+<?(passwordConfirmation).+>\\s+-\\s*email\\s+<?(email).+>\\s+-\\s*n\\s+<?(nickname).+>(\\s+-\\s*s\\s+<?(slogan).+>)?\\s*"),
    VALID_USERNAME("[a-zA-Z0-9_]+");

    private String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, LoginMenuCommands loginMenuCommands) {
        Matcher matcher = Pattern.compile(loginMenuCommands.regex).matcher(command);
        if (matcher.matches())
            return matcher;
        return null;
    }
}
