package View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    ;

    private String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, LoginMenuCommands loginMenuCommands) {
        Matcher matcher = Pattern.compile(loginMenuCommands.regex).matcher(command);

        return matcher;
    }
}
