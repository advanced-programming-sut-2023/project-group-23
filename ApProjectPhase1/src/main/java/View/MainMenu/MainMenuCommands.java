package View.MainMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    LOGOUT("^\\s*log\\s*out\\s*$"),
    ENTER_PROFILE_MENU("^\\s*enter\\s+profile\\s+menu\\s*$"),
    START_NEW_GAME("\\s*start\\s+a\\s+new\\s+game\\s+(?<content>-u\\.*)\\s*"),
    USERS_FIELD ("-u\\s+(?<users>\\S+)"),
    ENTER_TRADE_MENU("^\\s*enter\\s+trade\\s+menu\\s*$");

    private String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MainMenuCommands mainMenuCommands) {
        Matcher matcher = Pattern.compile(mainMenuCommands.regex).matcher(command);

        return matcher;
    }
}
