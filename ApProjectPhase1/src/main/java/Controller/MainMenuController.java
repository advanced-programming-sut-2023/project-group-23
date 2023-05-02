package Controller;

import Model.Game;
import Model.Government;
import Model.Map;
import Model.User;
import View.LoginMenu.LoginMenuCommands;
import View.MainMenu.MainMenuCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

public class MainMenuController {
    public static String startNewGame(String content) {
        Matcher matcher = MainMenuCommands.getMatcher(content, MainMenuCommands.USERS_FIELD);
        if(!matcher.matches())
            return "invalid users field";

        String usernames = matcher.group("users");
        String[] usernamesArray = usernames.split(",");

        if(usernamesArray.length > 8)
            return "can't have more than 8 users";

        ArrayList<Government> tempGovernmentsList = new ArrayList<>();
        tempGovernmentsList.add(new Government(User.getCurrentUser()));

        User user;
        for(String username : usernamesArray) {
            if((user = User.getUserByUsername(username)) == null)
                return "no user with username \"" + username + "\" exists";
            tempGovernmentsList.add(new Government(user));
        }

        Game.setCurrentGame(new Game(tempGovernmentsList, new Map()));
        return "set your game settings";
    }
}
