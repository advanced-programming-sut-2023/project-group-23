package View.MainMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    LOGOUT("^\\s*(l|L)(o|O)(g|G)\\s*(o|O)(u|U)(t|T)\\s*$"),
    ENTER_PROFILE_MENU("^\\s*enter\\s+profile\\s+menu\\s*$"),
    START_NEW_GAME("^\\s*start\\s+a\\s+new\\s+game\\s*$")
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
