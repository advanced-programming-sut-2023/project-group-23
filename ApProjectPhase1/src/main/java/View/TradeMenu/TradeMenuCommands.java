package View.TradeMenu;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    BACK("^\\s*back\\s*$"),
    ENTER_PLAYER("^\\s*(?<playerNumber>[0-9]+)\\s*$"),
    TRADE_LIST("\\s*trade\\s+list\\s*"),
    TRADE_HISTORY("\\s*trade\\s+history\\s*"),
    TRADE("\\s*trade(?<content>(\\s+-[tapm]\\s+([^\"\\s]+|(\"[^\"]*\")))*)\\s*"),
    TRADE_ACCEPT("\\s*trade\\s+accept(?<content>(\\s+-[im]\\s+([^\"\\s]+|(\"[^\"]*\")))*)\\s*"),
    RESOURCE_TYPE_FIELD("-t\\s+(?<resourceType>[^\"\\s]+|(\"[^\"]*\"))"),
    RESOURCE_AMOUNT_FIELD("-a\\s+(?<resourceAmount>[^\"\\s]+|(\"[^\"]*\"))"),
    RESOURCE_PRICE_FIELD("-a\\s+(?<price>[^\"\\s]+|(\"[^\"]*\"))"),
    MESSAGE_FIELD("-a\\s+(?<message>[^\"\\s]+|(\"[^\"]*\"))"),
    ID_FIELD("-i\\s+(?<id>[^\"\\s]+|(\"[^\"]*\"))"),
    VALID_NUMBER_INPUT("-?\\d+");
    private String regex;

    TradeMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, TradeMenuCommands tradeMenuCommands) {
        Matcher matcher = Pattern.compile(tradeMenuCommands.regex).matcher(command);

        return matcher;
    }
}
