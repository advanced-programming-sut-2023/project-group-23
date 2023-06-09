package View.UnitMenu;

import View.BuildlingMenu.BuildingMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuCommands {
    BACK ("\\s*back\\s*"),
    MOVE_UNIT("\\s*move\\s+unit\\s+to" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)"),
    SET_STATE ("\\s*set" +
            "\\s+-s\\s+(?<state>[^\"\\s]+|(\"[^\"]*\"))\\s*"),
    ATTACK ("\\s*attack\\s+-e\\s+(?<xCoordinate>-?\\d+)\\s+(?<yCoordinate>-?\\d+)\\s*"),
    AIR_ATTACK ("\\s*attack" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)"),
    DISBAND_UNIT ("\\s*disband\\s+unit\\s*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)");

    private String regex;

    UnitMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, UnitMenuCommands unitMenuCommands) {
        Matcher matcher = Pattern.compile(unitMenuCommands.regex).matcher(command);

        return matcher;
    }
}
