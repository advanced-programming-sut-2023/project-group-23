package View.BuildlingMenu;

import View.GameMenu.GameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {
    BACK ("\\s*back\\s*"),
    REPAIR ("\\s*repair\\s*"),
    CREATEUNIT ("\\s*createunit" +
            "\\s+-t\\s+(?<type>[^\"\\s]+|(\"[^\"]*\"))" +
            "\\s+-c\\s+(?<count>-?\\d+)\\s*");

    private String regex;

    BuildingMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, BuildingMenuCommands buildingMenuCommands) {
        Matcher matcher = Pattern.compile(buildingMenuCommands.regex).matcher(command);

        return matcher;
    }
}
