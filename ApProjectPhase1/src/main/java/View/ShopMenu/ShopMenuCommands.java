package View.ShopMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    ;
    private String regex;

    ShopMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ShopMenuCommands shopMenuCommands) {
        Matcher matcher = Pattern.compile(shopMenuCommands.regex).matcher(command);

        return matcher;
    }
}
