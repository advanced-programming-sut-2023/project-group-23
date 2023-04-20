package View.MainMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    ;

    private String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MainMenuCommands mainMenuCommands) {
        Matcher matcher = Pattern.compile(mainMenuCommands.regex).matcher(command);

        return matcher;
    }
}
