package View.MapMenu;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {
    SHOW_MAP("\\s*show\\s+map(?<content>(\\s+-[xy]\\s+-?\\d+){2})\\s*"),
    COORDINATE_X("-x\\s+(?<xCoordinate>(-)?\\d+)"),
    COORDINATE_Y("^-y\\s+(?<yCoordinate>(-)?\\d+)$"),
    SHOW_DETAILS ("\\s*show\\s+details(?<content>(\\s+-[xy]\\s+-?\\d+){2})\\s*"),
    BACK ("\\s*back\\s*"),
    MOVE_MAP ("\\s*map(?<content>(\\s+((up)|(down)|(left)|(right))(\\s+-?\\d+)?)+)\\s*"),
    UP_DIRECTION ("(?<field>up(\\s+-?\\d+)?)"),
    DOWN_DIRECTION ("(?<field>down(\\s+-?\\d+)?)"),
    LEFT_DIRECTION ("(?<field>left(\\s+-?\\d+)?)"),
    RIGHT_DIRECTION ("(?<field>right(\\s+-?\\d+)?)"),
    AMOUNT ("(?<amount>-?\\d+)"),
    SETTEXTTURE("^\\s*settextture\\s+(.+)$"),
    SETTEXTTURE_MORE_THAN_ONE("^\\s*settextture\\s+(.+)$"),
    CLEAR("^\\s*clear\\s+(.+)$"),
    DROP_ROCK("^\\s*droprock\\s+(.+)$"),
    DROP_TREE("\\s*droptree\\s+(.+)$"),
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
