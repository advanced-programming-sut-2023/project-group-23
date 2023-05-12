package View.PreGameMenu;

import Controller.PreGameController;
import Model.Colors;
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

            if(pickGovernmentColor(scanner).equals("back")) {
                System.out.println("back to main menu");
                return;
            }

            if(placeKeep(scanner).equals("back")) {
                System.out.println("back to main menu");
                return;
            }

            while (true) {
                command = scanner.nextLine();

                if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.SET_TEXTURE_SINGLE)).matches())
                    System.out.println(PreGameController.setTextureSingle(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.SET_TEXTURE_ZONE)).matches())
                    System.out.println(PreGameController.setTextureZone(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.CLEAR)).matches())
                    System.out.println(PreGameController.clear(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.DROPROCK)).matches())
                    System.out.println(PreGameController.droprock(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.DROPTREE)).matches())
                    System.out.println(PreGameController.droptree(matcher));

                else if((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.DROPBUILDING)).matches())
                    System.out.println(PreGameController.dropbuilding(matcher));

                else if(PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.BACK).matches()) {
                    System.out.println("back to main menu");
                    return;
                }

                else
                    System.out.println("invalid command");
            }
        }
    }

    public static Game getCurrentGame() {
        return currentGame;
    }

    public static void setCurrentGame(Game currentGame) {
        PreGameMenu.currentGame = currentGame;
    }

    public static String pickGovernmentColor(Scanner scanner) {
        Matcher matcher;
        String command;
        while (true) {
            System.out.println("choose one of these colors as your government color:");
            for (Colors color : Colors.values()) {
                if (!currentGame.getUsedGovernmentColors().contains(color))
                    System.out.println(color.getName());
            }
            command = scanner.nextLine();
            if ((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.TEXT_INPUT)).matches()) {
                String inputColor = matcher.group("content").replace("\"", "");
                String result = PreGameController.pickGovernmentColor(inputColor);
                System.out.println(result);
                if(!result.equals("invalid color"))
                    return null;
            }
            else if(PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.BACK).matches())
                return "back";
            else
                System.out.println("invalid command");
        }
    }

    public static String placeKeep(Scanner scanner) {
        Matcher matcher;
        String command;
        while (true) {
            System.out.println("enter coordinates of your keep:");
            command = scanner.nextLine();
            if ((matcher = PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.COORDINATE_INPUT)).matches()) {
                int x = Integer.parseInt(matcher.group("xCoordinate"));
                int y = Integer.parseInt(matcher.group("yCoordinate"));
                String result = PreGameController.placeKeep(x, y);
                if (result.equals("placed building successfully"))
                    return null;
            } else if (PreGameMenuCommands.getMatcher(command, PreGameMenuCommands.BACK).matches())
                return "back";
            else
                System.out.println("invalid command");
        }
    }
}