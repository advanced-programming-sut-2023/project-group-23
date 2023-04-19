package View.ProfileMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
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
