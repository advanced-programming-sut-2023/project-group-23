package View.TradeMenu;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    ;
    private String regex;

    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, TradeMenuCommands tradeMenuCommands) {
        Matcher matcher = Pattern.compile(tradeMenuCommands.regex).matcher(command);

        return matcher;
    }
}
