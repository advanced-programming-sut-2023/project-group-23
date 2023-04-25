package View.MapMenu;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {
    //SHOW_MAP("^\\s*show\\s+map\\s*(.+)$"),
    SHOW_MAP("\\s*show\\s+map(?<content>(\\s+-[xy]\\s+-?\\d+){2})\\s*"),
    COORDINATE_X("-x\\s+(?<xCoordinate>(-)?\\d+)"),
    COORDINATE_Y("^-y\\s+(?<yCoordinate>(-)?\\d+)$"),
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
