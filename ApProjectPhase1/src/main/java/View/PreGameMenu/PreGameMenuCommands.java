package View.PreGameMenu;

import View.LoginMenu.LoginMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PreGameMenuCommands {
    SET_TEXTURE_SINGLE ("\\s*set\\s+texture" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)" +
            "\\s+-t\\s+(?<type>[^\"\\s]+|(\"[^\"]*\"))\\s*"),
    SET_TEXTURE_ZONE ("\\s*set\\s+texture" +
            "\\s+-x1\\s+(?<x1Coordinate>-?\\d+)\\s+-y1\\s+(?<y1Coordinate>-?\\d+)" +
            "\\s+-x1\\s+(?<x1Coordinate>-?\\d+)\\s+-y1\\s+(?<y1Coordinate>-?\\d+)" +
            "\\s+-t\\s+(?<type>[^\"\\s]+|(\"[^\"]*\"))\\s*"),
    TEXT_INPUT ("\\s*(?<content>[^\"\\s]+|(\"[^\"]*\"))\\s*"),
    BACK ("\\s*back\\s*"),
    COORDINATE_INPUT ("\\s*-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)\\s*"),
    CLEAR ("\\s*clear\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)\\s*"),
    DROPROCK ("\\s*droprock" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)" +
            "\\s+-d\\s+(?<direction>[^\"\\s]+|(\"[^\"]*\"))\\s*"),
    DROPTREE ("\\s*droptree" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)" +
            "\\s+-t\\s+(?<type>[^\"\\s]+|(\"[^\"]*\"))\\s*"),
    DROPBUILDING ("\\s*dropbuilding" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)" +
            "\\s+-t\\s+(?<type>[^\"\\s]+|(\"[^\"]*\"))\\s*"),
    DROPUNIT ("\\s*dropunit" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)" +
            "\\s+-t\\s+(?<type>[^\"\\s]+|(\"[^\"]*\"))\\s*");

    private String regex;

    PreGameMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, PreGameMenuCommands preGameMenuCommands) {
        Matcher matcher = Pattern.compile(preGameMenuCommands.regex).matcher(command);
        return matcher;
    }
}
