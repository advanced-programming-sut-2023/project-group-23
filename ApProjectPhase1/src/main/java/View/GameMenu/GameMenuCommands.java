package View.GameMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    ENTER_SHOP_MENU("^\\s*enter\\s+shop\\s+menu\\s*$"),
    SHOW_POPULARITY_FACTORS("^\\s*show\\s+popularity\\s+factors\\s*$"),
    SHOW_POPULARITY("^\\s*show\\s+popularity\\s*$"),
    SHOW_FOOD_LIST("^\\s*show\\s+food\\s+list\\s*$"),
    FOOD_RATE("^\\s*food\\s+rate\\s+-r\\s+(?<rateNumber>(-)?\\d+)\\s*$"),
    FOOD_RATE_SHOW("^\\s*food\\s+rate\\s+show\\s*$"),
    TAX_RATE("^\\s*tax\\s+rate\\s+-r\\s+(?<rateNumber>(-)?\\d+)\\s*$"),
    TAX_RATE_SHOW("^\\s*tax\\s+rate\\s+show\\s*$"),
    FEAR_RATE("^\\s*fear\\s+rate\\s+-r\\s+(?<rateNumber>(-)?\\d+)\\s*$"),
    DROP_BUILDING("\\s*dropbuilding" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)" +
            "\\s+-t\\s+(?<type>[^\"\\s]+|(\"[^\"]*\"))\\s*"),
    SELECT_BUILDING("\\s*select\\s+building" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)"),
    CREATE_UNIT("^\\s*createunit\\s+(.+)$"),
    REPAIR("^\\s*repair\\s*$"),
    SELECT_UNIT("\\s*select\\s+unit" +
            "\\s+-x\\s+(?<xCoordinate>-?\\d+)\\s+-y\\s+(?<yCoordinate>-?\\d+)"),
    PATROL_UNIT("^\\s*patrol\\s+unit\\s+(.+)$"),
    SET("^\\s*set\\s+(.+)$"),
    ATTACK_ENEMY("^\\s*attack\\s+-e\\s+(?<xCoordinate>(-)?\\d+)\\s+(?<yCoordinate>(-)?\\d+)\\s*$"),
    AIR_ATTACK("^\\s*attack\\s+(.+)$"),
    POUR_OIL("^\\s*pour\\s+oil\\s+-d\\s+(?<direction>\\S+)"),
    DIG_TUNNEL("^\\s*dig\\s+tunnel\\s+(.+)$"),
    BUILD_EQUIPMENT("^\\s*build\\s+-q\\s+(?<equipmentName>[^\"\\s]+|(\"[^\"]*\"))$"),
    DISBAND_UNIT("^\\s*disband\\s+unit\\s*$"),
    ENTER_TRADE_MENU("^\\s*enter\\s+trade\\s+menu\\s*$"),
    END_GAME ("\\s*end\\s+game\\s*"),
    DONE ("\\s*done\\s*")
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
