package View.PreGameMenu;

import View.LoginMenu.LoginMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PreGameMenuCommands {
    SET_TEXTURE_SINGLE ("\\s*set\\s+texture\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)\\s+-t\\s+(?<type>[^\"\\s]+|(\"[^\"]*\"))\\s*");

    private String regex;

    PreGameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, PreGameMenuCommands preGameMenuCommands) {
        Matcher matcher = Pattern.compile(preGameMenuCommands.regex).matcher(command);
        return matcher;
    }
}
