package View.PreGameMenu;

import Controller.PreGameController;
import Model.Game;
import Model.Government;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PreGameMenu {
    private static Game currentGame;

    public static void run(Scanner scanner) {
        PreGameMenu.setCurrentGame(Game.getCurrentGame());
        PreGameController.setCurrentGame(Game.getCurrentGame());

        String command;
        Matcher matcher;

        for(Government government : currentGame.getGovernments()) {
            PreGameController.setCurrentGovernment(government);

            while (true) {
                command = scanner.nextLine();

                if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.SET_TEXTURE_SINGLE)).matches())
                    System.out.println(PreGameController.setTextureSingle(matcher));
            }
        }
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        PreGameMenu.currentGame = currentGame;
    }
}
