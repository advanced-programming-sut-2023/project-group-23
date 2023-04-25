package View.GameMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    ENTER_SHOP_MENU("^\\s*enter\\s+shop\\s+menu\\s*$"),
    ;

    private String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, GameMenuCommands gameMenuCommands) {
        Matcher matcher = Pattern.compile(gameMenuCommands.regex).matcher(command);

        return matcher;
    }
}
