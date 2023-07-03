package Controller;

import Model.Game;
import Model.Government;
import Model.Maps;
import Model.User;
import View.MainMenu.MainMenuCommands;

import java.util.ArrayList;
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
            if(User.getCurrentUser().getUsername().equals(username))
                return "you can't enter your own username";
            tempGovernmentsList.add(new Government(user));
        }

        Game.setCurrentGame(new Game(tempGovernmentsList, new Maps()));
        return "set your game settings";
    }
}
