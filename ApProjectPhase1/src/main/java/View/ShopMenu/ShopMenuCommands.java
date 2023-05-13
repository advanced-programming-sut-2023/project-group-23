package View.ShopMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    SHOW_PRICE_LIST("^\\s*show\\s+price\\s+list\\s*$"),
    BUY_ITEM("^\\s*buy\\s+(.+)$"),
    SELL_ITEM("^\\s*sell\\s+(.+)$"),
    ITEM_NAME_FIELD("-i\\s+(?<itemName>[^\"\\s]+|(\"[^\"]*\"))"),
    ITEM_AMOUNT("-a\\s+(?<itemAmount>[^\"\\s]+|(\"[^\"]*\"))"),
    ITEM_AMOUNT_VALIDITY("(?<amount>(-)?[0-9]+)"),
    BACK ("\\s*back\\s*"),
    ENTER_SHOP_MENU("^\\s*enter\\s+shop\\s+menu\\s*$"),
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
