package View.MapMenu;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {
    ;
    private String regex;

    MapMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, MapMenuCommands mapMenuCommands) {
        Matcher matcher = Pattern.compile(mapMenuCommands.regex).matcher(command);

        return matcher;
    }
}
