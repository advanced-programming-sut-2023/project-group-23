package View.ProfileMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("^\\s*change\\s+username\\s+-\\s*u\\s+(?<username>.+)$"),
    CHANGE_NICKNAME("^\\s*change\\s+nickname\\s+-\\s*n\\s+(?<nickname>.+)$"),
    CHANGE_PASSWORD("^\\s*change\\s+password\\s+(.+)$"),
    OLD_PASSWORD_FIELD("-\\s*o\\s+(?<oldpassword>[^\"\\s]+|(\"[^\"]*\"))"),
    NEW_PASSWORD_FIELD("-\\s*n\\s+(?<newpassword>[^\"\\s]+|(\"[^\"]*\"))"),
    CHANGE_EMAIL("^\\s*change\\s+email\\s+-\\s*e\\s+(?<email>.+)$"),
    CHANGE_SLOGAN("^\\s*change\\s+slogan\\s+-\\s*s\\s+slogan"),
    REMOVE_SLOGAN("^\\s*Profile\\s+remove\\s+slogan\\s*$"),
    DISPLAY_HIGHSCORE("^\\s*profile\\s+display\\s+highscore\\s*$"),
    DISPLAY_RANK("^\\s*profile\\s+display\\s+rank$\\s*"),
    DISPLAY_SLOGAN("^\\s*profile\\s+display\\s+slogan\\s*$"),
    DISPLAY_PROFILE("^\\s*profile\\s+display\\s*$")
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
