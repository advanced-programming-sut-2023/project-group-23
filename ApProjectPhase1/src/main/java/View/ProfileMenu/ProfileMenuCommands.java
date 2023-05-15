package View.ProfileMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("^\\s*profile\\s+change\\s+-\\s*u\\s+(?<username>.+)$"),
    CHANGE_NICKNAME("^\\s*profile\\s+change\\s+-\\s*n\\s+(?<nickname>.+)$"),
    CHANGE_PASSWORD("^\\s*profile\\s+change\\s+password\\s+(.+)$"),
    OLD_PASSWORD_FIELD("-\\s*o\\s+(?<oldPassword>[^\"\\s]+|(\"[^\"]*\"))"),
    NEW_PASSWORD_FIELD("-\\s*n\\s+(?<newPassword>[^\"\\s]+|(\"[^\"]*\"))"),
    RE_ENTERED_PASSWORD("^\\s*(?<reEnteredPassword>[^\"\\s]+|(\"[^\"]*\"))\\s*$"),
    CHANGE_EMAIL("^\\s*profile\\s+change\\s+-\\s*e\\s+(?<email>.+)$"),
    CHANGE_SLOGAN("^\\s*profile\\s+change\\s+slogan\\s+-\\s*s\\s+(?<slogan>[^\"\\s]+|(\"[^\"]*\"))\\s*$"),
    REMOVE_SLOGAN("^\\s*profile\\s+remove\\s+slogan\\s*$"),
    DISPLAY_HIGHSCORE("^\\s*profile\\s+display\\s+highscore\\s*$"),
    DISPLAY_RANK("^\\s*profile\\s+display\\s+rank$\\s*"),
    DISPLAY_SLOGAN("^\\s*profile\\s+display\\s+slogan\\s*$"),
    DISPLAY_PROFILE("^\\s*profile\\s+display\\s*$"),
    BACK("^\\s*(B|b)(A|a)(C|c)(K|k)\\s*$")
    ;

    private String regex;

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String command, ProfileMenuCommands profileMenuCommands) {
        Matcher matcher = Pattern.compile(profileMenuCommands.regex).matcher(command);

        return matcher;
    }
}
