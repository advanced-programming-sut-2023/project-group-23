package View.LoginMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    CREATE_USER("\\s*create\\s+user(?<content>(\\s+-[upcnse]\\s+([^\"\\s]+|(\"[^\"]*\")))*)\\s*"),
    USERNAME_FIELD ("-u\\s+(?<username>[^\"\\s]+|(\"[^\"]*\"))"),
    PASSWORD_FIELD ("-p\\s+(?<password>[^\"\\s]+|(\"[^\"]*\"))"),
    PASSWORD_CONFIRM_FIELD ("-c\\s+(?<password>[^\"\\s]+|(\"[^\"]*\"))"),
    EMAIL_FIELD ("-e\\s+(?<email>[^\"\\s]+|(\"[^\"]*\"))"),
    NICKNAME_FIELD ("-n\\s+(?<nickname>[^\"\\s]+|(\"[^\"]*\"))"),
    SLOGAN_FIELD ("-s\\s+(?<slogan>[^\"\\s]+|(\"[^\"]*\"))"),
    VALID_USERNAME("[a-zA-Z0-9_]+"),
    VALID_PASSWORD ("\\S+"),
    VALID_NICKNAME ("\\S+"),
    VALID_EMAIL("(?<name>[\\w+_.]+)@(?<mailServer>[\\w+_.]+)\\.(?<domain>[\\w+_.]+)"),
    QUESTION_PICK ("\\s*question\\s+pick(?<content>(\\s+-[qac]\\s+([^\"\\s]+|(\"[^\"]*\")))*)\\s*"),
    QUESTION_NUMBER_FIELD ("-q\\s+(?<questionNumber>[^\"\\s]+|(\"[^\"]*\"))"),
    VALID_QUESTION_NUMBER ("-?\\d+"),
    ANSWER_FIELD ("-a\\s+(?<answer>[^\"\\s]+|(\"[^\"]*\"))"),
    ANSWER_CONFIRMATION_FIELD ("-c\\s+(?<answer>[^\"\\s]+|(\"[^\"]*\"))");

    private String regex;

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, LoginMenuCommands loginMenuCommands) {
        Matcher matcher = Pattern.compile(loginMenuCommands.regex).matcher(command);
        return matcher;
    }
}